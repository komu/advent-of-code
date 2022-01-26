package komu.adventofcode.aoc2018

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Direction.*
import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun reservoirResearch(input: String): Int {
    val reservoir = Reservoir(input)
    flowFrom(reservoir, Point(x = 500, y = 0))
    return reservoir.totalWater
}

fun reservoirResearch2(input: String): Int {
    val reservoir = Reservoir(input)
    flowFrom(reservoir, Point(x = 500, y = 0))
    return reservoir.filledWater.size
}

private fun flowFrom(reservoir: Reservoir, start: Point, visited: MutableSet<Point> = mutableSetOf()) {
    if (!visited.add(start))
        return

    var point = start
    while (true) {
        if (point.y > reservoir.yRange.last)
            return

        val down = point.towards(DOWN)
        if (down in reservoir.clay)
            break

        reservoir.addFlow(point)
        point = down
    }

    while (true) {
        val left = reservoir.fillTowards(point, LEFT)
        val right = reservoir.fillTowards(point, RIGHT)

        val bothWalls = left is FillResult.Wall && right is FillResult.Wall
        reservoir.fillBetween(left.position, right.position, flowing = !bothWalls)

        if (left is FillResult.Wall && right is FillResult.Wall) {
            point += UP

        } else {

            if (left is FillResult.Drop)
                flowFrom(reservoir, left.position, visited)
            if (right is FillResult.Drop)
                flowFrom(reservoir, right.position, visited)

            return
        }
    }
}

private class Reservoir(input: String) {

    val clay = input.nonEmptyLines()
        .map { ReservoirScanLine.parse(it) }
        .flatMap { it.points }
        .toMutableSet()

    val filledWater = mutableSetOf<Point>()
    val flowingWater = mutableSetOf<Point>()
    val yRange = clay.minOf { it.y }..clay.maxOf { it.y }

    val totalWater: Int
        get() = filledWater.union(flowingWater).size

    fun fillTowards(position: Point, direction: Direction): FillResult {
        var p = position
        while (true) {
            if (p.towards(direction) in clay)
                return FillResult.Wall(p)
            else if (p.towards(DOWN) !in clay && p.towards(DOWN) !in filledWater)
                return FillResult.Drop(p)
            p += direction
        }
    }

    fun fillBetween(left: Point, right: Point, flowing: Boolean) {
        for (x in left.x..right.x) {
            val point = Point(x, left.y)

            if (flowing)
                flowingWater += point
            else
                filledWater += point
        }
    }

    fun addFlow(point: Point) {
        if (point !in filledWater && point.y in yRange)
            flowingWater += point
    }

    @Suppress("unused")
    fun dumpImage(s: String) {
        val allPoints = clay + filledWater + flowingWater
        val minX = allPoints.minOf { it.x }
        val maxX = allPoints.maxOf { it.x }
        val minY = allPoints.minOf { it.y }
        val maxY = allPoints.maxOf { it.y }
        val image = BufferedImage(maxX - minX + 1, maxY - minY + 1, BufferedImage.TYPE_INT_RGB)

        for (y in 0 until image.height)
            for (x in 0 until image.width)
                image.setRGB(x, y, when (Point(minX + x, minY + y)) {
                    in clay -> 0x000000ff
                    in filledWater -> 0x0000ff00
                    in flowingWater -> 0x00ff0000
                    else -> 0x00000000
                })

        ImageIO.write(image, "png", File(s))
    }
}

private sealed class FillResult {
    abstract val position: Point

    data class Wall(override val position: Point) : FillResult()
    data class Drop(override val position: Point) : FillResult()
}

private sealed class ReservoirScanLine {

    abstract val points: Collection<Point>

    data class HorizontalLine(val x: Int, val yRange: IntRange) : ReservoirScanLine() {
        override val points: Collection<Point>
            get() = yRange.map { Point(x, it) }
    }

    data class VerticalLine(val xRange: IntRange, val y: Int) : ReservoirScanLine() {
        override val points: Collection<Point>
            get() = xRange.map { Point(it, y) }
    }

    companion object {
        private val regex1 = Regex("""([xy])=(\d+), ([xy])=(\d+)\.\.(\d+)""")

        fun parse(line: String): ReservoirScanLine {
            val (first, firstValue, second, secondMin, secondMax) = regex1.matchEntire(line)?.destructured
                ?: error("invalid line '$line'")

            val firstNum = firstValue.toInt()
            val secondRange = secondMin.toInt()..secondMax.toInt()
            return when {
                first == "x" && second == "y" -> HorizontalLine(firstNum, secondRange)
                first == "y" && second == "x" -> VerticalLine(secondRange, firstNum)
                else -> error("invalid coords $first/$second")
            }
        }
    }
}