package komu.adventofcode.aoc2016

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.permutations
import utils.shortestPathBetween

fun airDuctSpelunking(input: String, returnToStart: Boolean): Int {
    val map = AirDuctMap.parse(input)

    val start = map.pointsOfInterest['0'] ?: error("no starting point")
    val otherPoints = map.pointsOfInterest.values - start

    return otherPoints.permutations().minOf { permutation ->
        val path = listOf(start) + permutation + if (returnToStart) listOf(start) else emptyList()
        path.zipWithNext { a, b -> map.distanceBetween(a, b) }.sum()
    }
}

private class AirDuctMap(private val rows: List<String>) {

    private val distanceCache = mutableMapOf<Pair<Point, Point>, Int>()

    val pointsOfInterest = buildMap {
        for ((y, row) in rows.withIndex())
            for ((x, c) in row.withIndex())
                if (c.isDigit())
                    this[c] = Point(x, y)
    }

    fun distanceBetween(p1: Point, p2: Point): Int {
        val key = minOf(p1, p2, Point.readingOrderComparator) to maxOf(p1, p2, Point.readingOrderComparator)
        return distanceCache.getOrPut(key) {
            shortestPathBetween(p1, p2) { p ->
                p.neighbors.filter { isPassable(it) }
            }?.size ?: error("no path")
        }
    }

    private fun isPassable(p: Point) =
        rows[p.y][p.x] != '#'

    companion object {

        fun parse(input: String): AirDuctMap =
            AirDuctMap(input.nonEmptyLines())
    }
}