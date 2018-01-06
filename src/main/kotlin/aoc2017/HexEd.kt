package komu.adventofcode.aoc2017

import kotlin.math.absoluteValue

fun hexEdDistance(input: String): Int =
    hexEdDistance(input.split(",").map { HexMove.valueOf(it.toUpperCase().trim()) })

fun hexEdMaxDistance(input: String): Int =
    hexEdMaxDistance(input.split(",").map { HexMove.valueOf(it.toUpperCase().trim()) })

fun hexEdDistance(steps: List<HexMove>): Int {
    val (x, y) = takeSteps(steps)
    return distanceTo(x, y)
}

fun hexEdMaxDistance(steps: List<HexMove>): Int {
    var max = 0
    var x = 0
    var y = 0

    for (step in steps) {
        x += step.dx
        y += step.dy

        val distance = distanceTo(x, y)
        if (distance > max)
            max = distance
    }

    return max
}

fun distanceTo(x: Int, y: Int): Int {
    val xx = x.absoluteValue
    val yy = y.absoluteValue

    return if (yy < xx) xx else yy
}

fun takeSteps(steps: List<HexMove>): Pair<Int, Int> {
    var x = 0
    var y = 0

    for (step in steps) {
        x += step.dx
        y += step.dy
    }

    return Pair(x, y)
}

enum class HexMove(val dx: Int, val dy: Int) {
    N(0, -1),
    NE(1, -1),
    SE(1, 0),
    S(0, 1),
    SW(-1, 1),
    NW(-1, 0);
}
