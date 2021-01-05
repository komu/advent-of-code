package aoc2020

import komu.adventofcode.utils.countOccurrences
import komu.adventofcode.utils.nonEmptyLines
import kotlin.math.sqrt

fun jurassicJigsaw1(input: String) =
    Jigsaw.parse(input).checksum()

fun jurassicJigsaw2(input: String): Int {
    val monster = JigsawPattern(
        """
                          #
        #    ##    ##    ###
         #  #  #  #  #  #
        """.trimIndent()
    )

    val picture = Jigsaw.parse(input).picture()
    val matches = picture.variations().maxOf { it.countMatches(monster) }

    return picture.count('#') - (matches * monster.elements)
}

private class Jigsaw(private val tiles: Collection<JigsawTile>) {

    private val size = sqrt(tiles.size.toDouble()).toInt()
    private val pieces = mutableListOf<MutableList<JigsawGrid>>()

    init {
        val queue = tiles.toMutableList()
        for (y in 0 until size) {
            val row = mutableListOf<JigsawGrid>()
            pieces += row
            for (x in 0 until size)
                row += placePart(x, y, queue)
        }
    }

    private fun placePart(x: Int, y: Int, unusedTiles: MutableCollection<JigsawTile>): JigsawGrid {
        val it = unusedTiles.iterator()

        while (it.hasNext()) {
            val tile = it.next()
            for (c in tile.configurations) {
                if (canPlace(x, y, c)) {
                    it.remove()
                    return c
                }
            }
        }

        error("can't place part")
    }

    // We are filling the pieces from top-down, left to right so we only never need to check the right side
    private fun canPlace(x: Int, y: Int, c: JigsawGrid) =
        (if (x == 0) !hasMatches(c.left) else c.left == pieces[y][x - 1].right)
                && (if (y == 0) !hasMatches(c.top) else c.top == pieces[y - 1][x].bottom)
                && ((x != size - 1) || !hasMatches(c.right))
                && ((y != size - 1) || !hasMatches(c.bottom))

    private fun hasMatches(border: String) =
        tiles.count { it.matches(border) } > 1

    fun checksum() =
        pieces.first().first().id.toLong() * pieces.first().last().id *
                pieces.last().first().id * pieces.last().last().id

    fun picture(): JigsawGrid {
        val cellSize = pieces[0][0].size - 2
        return JigsawGrid(0, size * cellSize) { x, y ->
            val cell = pieces[y / cellSize][x / cellSize].withoutBorders()
            cell[x % cellSize, y % cellSize]
        }
    }

    companion object {
        fun parse(input: String) =
            Jigsaw(input.split("\n\n").map { JigsawTile.parse(it) }.toMutableList())
    }
}

private class JigsawGrid(val id: Int, val size: Int, init: (Int, Int) -> Char) {

    private val data = Array(size) { y -> CharArray(size) { x -> init(x, y) } }

    val top = String(data.first())
    val bottom = String(data.last())
    val left = data.map { it.first() }.joinToString("")
    val right = data.map { it.last() }.joinToString("")

    operator fun get(x: Int, y: Int) = data[y][x]

    fun matches(b: String) =
        b == top || b == bottom || b == left || b == right

    fun withoutBorders() = JigsawGrid(id, size - 2) { x, y -> data[y + 1][x + 1] }

    fun rotate() = JigsawGrid(id, size) { x, y -> data[size - x - 1][y] }
    fun flip() = JigsawGrid(id, size) { x, y -> data[y][size - x - 1] }

    fun variations(): List<JigsawGrid> {
        val rots = generateSequence(this) { it.rotate() }.take(4).toList()
        return rots + rots.map { it.flip() }
    }

    override fun toString(): String =
        data.joinToString("\n")

    fun countMatches(pattern: JigsawPattern): Int {
        var matches = 0
        for (y in 0 until size - pattern.height)
            for (x in 0 until size - pattern.length)
                if (pattern.matchesAt(this, x, y))
                    matches++

        return matches
    }

    fun count(c: Char) =
        data.sumBy { r -> r.count { it == c } }
}

private class JigsawPattern(input: String) {
    val lines = input.nonEmptyLines()
    val length = lines.maxOf { it.length }
    val height = lines.size
    val elements = input.countOccurrences('#')

    fun matchesAt(grid: JigsawGrid, x: Int, y: Int): Boolean {
        for ((yy, line) in lines.withIndex())
            for ((xx, c) in line.withIndex())
                if (c == '#' && grid[x + xx, y + yy] != '#')
                    return false
        return true
    }
}

private class JigsawTile(val id: Int, val configurations: List<JigsawGrid>) {

    fun matches(border: String) =
        configurations.any { it.matches(border) }

    companion object {

        private val headerRegex = Regex("""Tile (\d+):""")

        fun parse(str: String): JigsawTile {
            val lines = str.nonEmptyLines()
            val (_, idStr) = headerRegex.matchEntire(lines[0])?.groupValues ?: error("invalid header")
            val id = idStr.toInt()

            val data = lines.drop(1)
            return JigsawTile(id, JigsawGrid(id, data.size) { x, y -> data[y][x] }.variations())
        }
    }
}
