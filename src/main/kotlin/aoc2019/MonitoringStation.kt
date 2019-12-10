package aoc2019

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines
import kotlin.math.atan2

private typealias Angle = Double

fun monitoringStation1(input: String): Int =
    parseAsteroidMap(input).buildAngleMap().values.map { it.size }.max()!!

fun monitoringStation2(input: String): Int {
    val asteroids = parseAsteroidMap(input)
    val (station, slopes) = parseAsteroidMap(input).buildAngleMap().entries.maxBy { it.value.size }!!

    var count = 0

    val otherAsteroids = (asteroids - station)
    for (angle in slopes) {
        val vaporized = otherAsteroids.filter { angleBetween(station, it) == angle }.minBy { station.manhattanDistance(it) }
        if (vaporized != null) {
            count++

            if (count == 200)
                return vaporized.x * 100 + vaporized.y
        }
    }

    error("no result")
}

private fun Set<Point>.buildAngleMap(): Map<Point, Set<Angle>> =
    associateWith { start -> (this - start).map { angleBetween(start, it) }.toSortedSet() }

private fun parseAsteroidMap(input: String): Set<Point> {
    val asteroids = mutableSetOf<Point>()
    for ((y, line) in input.nonEmptyLines().withIndex())
        for ((x, c) in line.withIndex())
            if (c == '#')
                asteroids += Point(x, y)
    return asteroids
}

private fun angleBetween(from: Point, to: Point): Angle {
    val dx = from.x - to.x
    val dy = from.y - to.y
    val radians = atan2(dx.toDouble(), dy.toDouble())
    return if (radians <= 0) -radians else Math.PI * 2 - radians
}
