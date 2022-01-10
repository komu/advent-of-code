package komu.adventofcode.aoc2020

import komu.adventofcode.utils.Dir
import komu.adventofcode.utils.Point

fun seatingSystem1(data: String): Int =
    Grid.forData(data).evolve { step1() }.countOccupied()

fun seatingSystem2(data: String): Int =
    Grid.forData(data).evolve { step2() }.countOccupied()

private class Grid(val width: Int, val height: Int, init: (Point) -> Char) {

    private val data = CharArray(width * height) { i -> init(Point(i % width, i / width)) }

    operator fun get(p: Point): Char =
        data[p.y * width + p.x]

    operator fun set(p: Point, char: Char) {
        data[p.y * width + p.x] = char
    }

    operator fun contains(p: Point) =
        p.x in 0 until width && p.y in 0 until height

    fun evolve(step: Grid.() -> Grid): Grid {
        var grid = this
        while (true) {
            val next = grid.step()
            if (grid == next)
                break
            grid = next
        }
        return grid
    }

    fun step1() = Grid(width, height) { p ->
        when (this[p]) {
            'L' -> if (countOccupiedNeighbors(p) == 0) '#' else 'L'
            '#' -> if (countOccupiedNeighbors(p) >= 4) 'L' else '#'
            else -> '.'
        }
    }

    fun step2() = Grid(width, height) { p ->
        when (this[p]) {
            'L' -> if (countSeenOccupied(p) == 0) '#' else 'L'
            '#' -> if (countSeenOccupied(p) >= 5) 'L' else '#'
            else -> '.'
        }
    }

    private fun countOccupiedNeighbors(p: Point): Int =
        Dir.all.count { d ->
            val pt = p + d
            pt in this && this[pt] == '#'
        }

    private fun countSeenOccupied(p: Point): Int =
        Dir.all.count { seenOccupied(p, it) }

    private fun seenOccupied(p: Point, d: Dir): Boolean {
        var pt = p + d
        while (pt in this) {
            when (this[pt]) {
                '#' -> return true
                'L' -> return false
            }

            pt += d
        }
        return false
    }

    override fun equals(other: Any?) =
        other is Grid && data.contentEquals(other.data)

    override fun hashCode(): Int = data.hashCode()

    override fun toString() =
        data.asList().windowed(width, width).joinToString("\n") { it.joinToString("") }

    fun countOccupied(): Int =
        data.count { it == '#' }

    companion object {
        fun forData(data: String): Grid {
            val lines = data.lines()
            return Grid(lines.first().length, lines.size) { p ->
                lines[p.y][p.x]
            }
        }
    }
}
