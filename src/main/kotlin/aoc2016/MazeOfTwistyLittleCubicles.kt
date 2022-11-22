package komu.adventofcode.aoc2016

import komu.adventofcode.utils.Point
import utils.shortestPathBetween

fun mazeOfTwistyLittleCubicles1() =
    shortestPathBetween(Point(1, 1), Point(31, 39)) { it.openNeighbors }?.size ?: error("no path")

fun mazeOfTwistyLittleCubicles2(): Int {
    val seen = mutableSetOf<Point>()
    val queue = ArrayDeque<SearchState>()
    queue.add(SearchState(Point(1, 1), depth = 0))

    while (queue.isNotEmpty()) {
        val state = queue.removeFirst()

        if (state.depth <= 50 && seen.add(state.point))
            queue += state.next
    }

    return seen.size
}

private class SearchState(val point: Point, val depth: Int) {
    val next: List<SearchState>
        get() = point.openNeighbors.map { SearchState(it, depth = depth + 1) }
}

private val Point.openNeighbors: List<Point>
    get() = neighbors.filter { isOpenSpace(it.x, it.y) }

private fun isOpenSpace(x: Int, y: Int): Boolean {
    if (x < 0 || y < 0) return false

    val num = (x * x) + (3 * x) + (2 * x * y) + y + (y * y)
    return Integer.bitCount(num + 1364) % 2 == 0
}
