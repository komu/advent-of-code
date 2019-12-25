package aoc2019

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import utils.shortestPathBetween

fun donutMaze1(input: String): Int {
    val maze = DonutMaze.parse(input)

    val path = shortestPathBetween(maze.start, maze.end) { it.neighbors } ?: error("no path")

    return path.size
}

private class DonutMaze(
    val start: Node,
    val end: Node
) {

    companion object {
        fun parse(input: String): DonutMaze {
            val lines = input.nonEmptyLines()
            val nodesByPoint = mutableMapOf<Point, Node>()
            var start: Point? = null
            var end: Point? = null
            val portalsInProgress = mutableMapOf<String, Point>()
            val portals = mutableMapOf<Point, Point>()

            fun get(x: Int, y: Int) = lines.getOrNull(y)?.getOrNull(x) ?: ' '

            fun addPortal(label: String, point: Point) {
                when (label) {
                    "AA" -> start = point
                    "ZZ" -> end = point
                    else -> {
                        val otherEnd = portalsInProgress.remove(label)
                        if (otherEnd != null) {
                            portals[point] = otherEnd
                            portals[otherEnd] = point
                        } else
                            portalsInProgress[label] = point
                    }
                }
            }

            for ((y, line) in lines.withIndex()) {
                for ((x, c) in line.withIndex()) {
                    when {
                        c == '.' ->
                            nodesByPoint[Point(x, y)] = Node()

                        c.isLetter() -> {
                            when {
                                get(x + 1, y).isLetter() -> {
                                    val label = "${c}${get(x + 1, y)}"
                                    val point = if (get(x - 1, y) == '.') Point(x - 1, y) else Point(x + 2, y)
                                    addPortal(label, point)
                                }
                                get(x, y + 1).isLetter() -> {
                                    val label = "${c}${get(x, y + 1)}"
                                    val point = if (get(x, y - 1) == '.') Point(x, y - 1) else Point(x, y + 2)
                                    addPortal(label, point)
                                }
                            }
                        }
                        c in " #" -> {
                            // ignore
                        }
                        else -> error("unexpected '$c'")
                    }
                }
            }

            check(portalsInProgress.isEmpty() && start != null && end != null)

            for ((point, node) in nodesByPoint) {
                val neighbors = mutableListOf<Node>()
                val portal = portals[point]
                if (portal != null)
                    neighbors += nodesByPoint[portal]!!

                neighbors += point.neighbors.mapNotNull { nodesByPoint[it] }

                node.neighbors = neighbors
            }

            return DonutMaze(
                start = nodesByPoint[start!!] ?: error("no start"),
                end = nodesByPoint[end!!] ?: error("no end")
            )
        }
    }

    class Node {
        var neighbors = emptyList<Node>()
    }
}
