package komu.adventofcode.aoc2015

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import java.util.*

fun likeAGifForYouYard1(input: String, buggy: Boolean = false): Int {
    var grid = GifGrid.parse(input, buggy)

    repeat(100) {
        grid = grid.step()
    }

    return grid.countLightsOn()
}

private class GifGrid(val buggy: Boolean) {

    private val lightsOn = BitSet(width * height)

    operator fun set(x: Int, y: Int, value: Boolean) {
        lightsOn[y * width + x] = value
    }

    operator fun get(x: Int, y: Int): Boolean =
        (buggy && isCorner(x, y)) || ((x in indices && y in indices) && lightsOn[y * width + x])

    private fun isCorner(x: Int, y: Int) =
        (x == 0 || x == width - 1) && (y == 0 || y == height - 1)

    fun countLightsOn() = indices.sumOf { y -> indices.count { x -> this[x, y] } }

    fun step(): GifGrid {
        val grid = GifGrid(buggy)

        for (y in indices)
            for (x in indices)
                grid[x, y] = nextFor(x, y)

        return grid
    }

    private fun nextFor(x: Int, y: Int): Boolean {
        val count = Point(x, y).allNeighbors.count { this[it.x, it.y] }

        return if (this[x, y]) {
            count == 2 || count == 3
        } else {
            count == 3
        }
    }

    companion object {

        private val indices = 0 until 100
        private const val width = 100
        private const val height = 100

        fun parse(input: String, buggy: Boolean): GifGrid {
            val grid = GifGrid(buggy)
            for ((y, line) in input.nonEmptyLines().withIndex()) {
                for ((x, c) in line.withIndex())
                    if (c == '#')
                        grid[x, y] = true
            }
            return grid
        }
    }
}