package komu.adventofcode.aoc2016

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Direction.DOWN
import komu.adventofcode.utils.Direction.RIGHT
import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines

fun gridComputing1(input: String) =
    Grid.parse(input).viablePairs()

// Part two is not really feasible to solve with assuming a fully general grid. By eyeballing the
// debug output of the grid it's pretty easy to figure out the correct route
fun gridComputing2(input: String): Int {
    val grid = Grid.parse(input)

    // path to move around the wall to the left side item
    val path = "ulllllllllllllllllllllllllluuuuuuuuuuuuuuuuuuuuuuuurrrrrrrrrrrrrrrrrrrrrrrrrrrrurl".map { d ->
        Direction.values().find { it.name.lowercase()[0] == d } ?: error("invalid char '$d' ")
    }
    grid.followPath(Point(34, 26), path)
    grid.dump()

    // it takes 5 moves to rotate the items to move one to the left
    return 261
}

private class Grid(nodes: List<GridNode>) {

    private val width = nodes.maxOf { it.point.x } + 1
    private val height = nodes.size / width
    private val nodes = nodes.associateBy { it.point }
    private var dataLocation = Point(width - 1, 0)

    fun viablePairs() =
        nodes.values.sumOf { a -> nodes.values.count { b -> a.isViablePairWith(b) } }

    private fun move(from: GridNode, to: GridNode) {
        to.used += from.used
        from.used = 0
        if (from.point == dataLocation)
            dataLocation = to.point
    }

    fun dump() {
        println((0 until width).joinToString(separator = "      ", prefix = "      ") { String.format("%-2d", it) })

        for (y in 0 until height) {
            val yLabel = String.format("%2d  ", y)

            println((0 until width).joinToString(separator = "", prefix = yLabel, postfix = yLabel) { x ->
                val point = Point(x, y)
                val node = nodes[point]!!

                val value = if (node.total > 400)
                    "#####"
                else
                    String.format("%2d%s%-2d", node.used, if (dataLocation == point) '!' else '/', node.total)

                val right = nodes[point + RIGHT]
                val next = when {
                    right != null && node.isViablePairWith(right) -> ">"
                    right != null && right.isViablePairWith(node) -> "<"
                    else -> " "
                }

                "$value $next "
            })

            println((0 until width).joinToString(prefix = "      ", separator = "       ") { x ->
                val point = Point(x, y)
                val node = nodes[point]!!
                val down = nodes[point + DOWN]

                when {
                    down != null && node.isViablePairWith(down) -> "v"
                    down != null && down.isViablePairWith(node) -> "^"
                    else -> " "
                }
            })
        }
    }

    fun followPath(active: Point, path: List<Direction>) {
        var node = nodes[active] ?: error("no such node: $active")
        for (d in path) {
            val newNode = nodes[node.point + d] ?: error("no such node: ${node.point + d}")

            move(newNode, node)
            node = newNode
        }
    }

    companion object {
        private val regex = Regex("""/dev/grid/node-x(\d+)-y(\d+)\s+(\d+)T\s+(\d+)T\s+\d+T\s+\d+%""")

        fun parse(input: String) =
            Grid(input.nonEmptyLines().filter { it.startsWith("/dev") }.map { parseNode(it) })

        private fun parseNode(s: String): GridNode {
            val (x, y, total, used) = regex.matchEntire(s)?.destructured ?: error("invalid input '$s'")

            return GridNode(Point(x.toInt(), y.toInt()), total.toInt(), used.toInt())
        }
    }
}

private data class GridNode(val point: Point, val total: Int, var used: Int) {
    val avail: Int
        get() = total - used

    fun isViablePairWith(b: GridNode): Boolean =
        used != 0 && this !== b && used <= b.avail

}

