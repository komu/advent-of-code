package aoc2019

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point

fun spacePolice1(input: String): Int =
    buildColorMap(input, 0).size

fun spacePolice2(input: String): String {
    val colorMap = buildColorMap(input, 1)
    val maxY = colorMap.map { it.key.y }.maxOrNull()!!
    val maxX = colorMap.map { it.key.x }.maxOrNull()!!

    return buildString {
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val color = colorMap[Point(x, y)] ?: 0
                append(if (color == 0) ' ' else '#')
            }
            appendLine()
        }
    }
}

private fun buildColorMap(input: String, startColor: Int): MutableMap<Point, Int> {
    val machine = IntCodeMachine(input)

    val tileColors = mutableMapOf(Point.ORIGIN to startColor)

    var pos = Point.ORIGIN
    var direction = Direction.UP
    val outputBuffer = mutableListOf<Int>()

    var nextInput = startColor
    machine.readInput = { nextInput.toLong() }
    machine.writeOutput = { v ->
        outputBuffer += v.toInt()

        if (outputBuffer.size == 2) {
            val (color, turn) = outputBuffer
            outputBuffer.clear()

            direction = when (turn) {
                0 -> direction.left
                1 -> direction.right
                else -> error("invalid turn $turn")
            }
            tileColors[pos] = color
            pos += direction
            nextInput = tileColors[pos] ?: 0
        }
    }

    machine.run()
    return tileColors
}
