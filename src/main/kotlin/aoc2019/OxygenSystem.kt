package komu.adventofcode.aoc2019

import komu.adventofcode.aoc2019.MoveResult.FOUND
import komu.adventofcode.aoc2019.MoveResult.WALL
import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Direction.*
import komu.adventofcode.utils.Point
import utils.shortestPathBetween

fun oxygenSystem1(input: String): Int {
    val (floor, tank) = createMap(input)
    return floor.shortestPathOnFloor(Point.ORIGIN, tank).size
}

fun oxygenSystem2(input: String): Int {
    val (floor, tank) = createMap(input)

    val filled = mutableSetOf(tank)
    var frontier = listOf(tank)
    var minutes = 0

    while (true) {
        val nextFrontier = mutableListOf<Point>()
        for (node in frontier)
            for (neighbor in node.neighbors)
                if (neighbor in floor && filled.add(neighbor))
                    nextFrontier += neighbor

        if (nextFrontier.isEmpty())
            return minutes

        frontier = nextFrontier
        minutes++
    }
}

private fun createMap(input: String): Pair<Set<Point>, Point> {
    val machine = IntCodeMachine(input)

    var position = Point.ORIGIN
    val floor = mutableSetOf(position)
    val walls = mutableSetOf<Point>()
    var tank: Point? = null

    val queue = position.neighbors.toMutableSet()
    while (queue.isNotEmpty()) {
        val target = queue.removeClosest(position)

        val path = floor.shortestPathOnFloor(position, target)
        for (node in path) {
            val direction = position.directionOfNeighbor(node)
            val result = machine.executeMove(direction)
            if (result == WALL) {
                walls += node
            } else {
                floor += node
                position = node
                if (result == FOUND)
                    tank = node

                for (neighbor in node.neighbors)
                    if (neighbor !in floor && neighbor !in walls)
                        queue += neighbor
            }
        }
    }

    return Pair(floor, tank!!)
}

private fun IntCodeMachine.executeMove(dir: Direction) =
    MoveResult.values()[sendInputAndWaitForOutput(dir.toMove()).toInt()]

private enum class MoveResult {
    WALL, MOVE, FOUND
}

private fun Set<Point>.shortestPathOnFloor(from: Point, to: Point): List<Point> =
    shortestPathBetween(from, to) { p ->
        p.neighbors.filter { it in this || it == to }
    }!!

private fun MutableSet<Point>.removeClosest(p: Point): Point {
    val closest = minByOrNull { it.manhattanDistance(p) } ?: error("no points")
    remove(closest)
    return closest
}

private fun Direction.toMove(): Long = when (this) {
    UP -> 1
    DOWN -> 2
    LEFT -> 3
    RIGHT -> 4
}
