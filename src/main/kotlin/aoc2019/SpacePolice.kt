package aoc2019

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point
import kotlin.concurrent.thread

fun spacePolice1(input: String): Int =
    buildColorMap(input, 0).size

fun spacePolice2(input: String): String {
    val colorMap = buildColorMap(input, 1)
    val maxY = colorMap.map { it.key.y }.max()!!
    val maxX = colorMap.map { it.key.x }.max()!!

    return buildString {
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val color = colorMap[Point(x, y)] ?: 0
                append(if (color == 0) ' ' else '#')
            }
            appendln()
        }
    }
}

private fun buildColorMap(input: String, startColor: Int): MutableMap<Point, Int> {
    val machine = IntCodeMachine(input)

    val tileColors = mutableMapOf(Point.ORIGIN to startColor)
    val t = thread {
        var pos = Point.ORIGIN
        var direction = Direction.UP

        try {
            while (!Thread.interrupted()) {
                machine.sendInput(tileColors[pos] ?: 0)
                val color = machine.readNext().toInt()
                val turn = machine.readNext().toInt()

                direction = when (turn) {
                    0 -> direction.left
                    1 -> direction.right
                    else -> error("invalid turn $turn")
                }
                tileColors[pos] = color
                pos += direction
            }
        } catch (e: InterruptedException) {
        }
    }

    machine.run()
    t.interrupt()
    return tileColors
}
