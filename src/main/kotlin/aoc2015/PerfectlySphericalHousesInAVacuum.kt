package komu.adventofcode.aoc2015

import komu.adventofcode.utils.Direction.*
import komu.adventofcode.utils.Point
import komu.adventofcode.utils.skipChars

fun visitedHouses(s: String): Int {
    val visited = mutableSetOf<Point>()

    visitHouses(s.asIterable(), visited)

    return visited.size
}

fun visitedHouses2(s: String): Int {
    val visited = mutableSetOf<Point>()

    visitHouses(s.skipChars(offset = 0, step = 2), visited)
    visitHouses(s.skipChars(offset = 1, step = 2), visited)

    return visited.size
}

private fun visitHouses(s: Iterable<Char>, visited: MutableSet<Point>) {
    var point = Point.ORIGIN
    visited += point

    for (c in s) {
        when (c) {
            '<' -> point += LEFT
            '>' -> point += RIGHT
            '^' -> point += UP
            'v' -> point += DOWN
        }

        visited += point
    }
}
