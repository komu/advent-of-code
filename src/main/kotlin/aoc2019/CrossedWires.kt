package aoc2019

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Direction.*
import komu.adventofcode.utils.Point

private typealias Wire = List<Movement>

fun crossedWires(input: String): Int {
    val (wire1, wire2) = input.lines().take(2).map { parseWire(it) }

    val visited = wire1.points().toSet()
    return wire2.points().filter { it in visited }.map { it.manhattanDistanceFromOrigin }.min() ?: Integer.MAX_VALUE
}

fun crossedWires2(input: String): Int {
    val (wire1, wire2) = input.lines().take(2).map { parseWire(it) }

    val stepsToPoint = mutableMapOf<Point, Int>()

    for ((steps, point) in wire1.points().withIndex())
        stepsToPoint.putIfAbsent(point, steps)

    var minCount = Integer.MAX_VALUE
    for ((steps, point) in wire2.points().withIndex()) {
        val count2 = stepsToPoint[point]
        if (count2 != null)
            minCount = minOf(minCount, steps + count2)
    }

    return minCount + 2 // convert 0-based indices to 1-based steps
}

private fun Wire.points(): Sequence<Point> = sequence {
    var point = Point.ORIGIN
    for (movement in this@points) {
        repeat(movement.steps) {
            point += movement.direction
            yield(point)
        }
    }
}

private fun parseWire(wire: String): Wire =
    wire.split(',').map(Movement.Companion::parse)

private class Movement(val direction: Direction, val steps: Int) {

    companion object {
        fun parse(step: String) = Movement(
            direction = when (step[0]) {
                'U' -> UP
                'D' -> DOWN
                'L' -> LEFT
                'R' -> RIGHT
                else -> error("invalid direction ${step[0]}")
            }, steps = step.substring(1).toInt()
        )
    }
}
