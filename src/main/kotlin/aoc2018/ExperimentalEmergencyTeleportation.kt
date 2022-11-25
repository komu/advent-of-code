package komu.adventofcode.aoc2018

import komu.adventofcode.utils.nonEmptyLines
import kotlin.math.abs

fun experimentalEmergencyTeleportation1(input: String): Int {
    val bots = input.nonEmptyLines().map { Nanobot.parse(it) }
    val strongest = bots.maxBy { it.range }
    return bots.count { strongest.pos.manhattanDistance(it.pos) <= strongest.range }
}

fun experimentalEmergencyTeleportation2(input: String): Int {
    val bots = input.nonEmptyLines().map { Nanobot.parse(it) }

    var bestConfirmedScore = Int.MIN_VALUE
    var bestPoint = Point3.ORIGIN

    fun recurse(cube: Cube) {
        val divisions = cube.divide()
        if (divisions.isEmpty()) {
            // If cube can't be divided, it's just a single point
            val point = cube.toPoint()
            val score = bots.count { point.manhattanDistance(it.pos) <= it.range }
            if (score > bestConfirmedScore) {
                bestConfirmedScore = score
                bestPoint = point
            }

        } else {
            recurse(divisions.maxBy { it.upperBoundForScore(bots) })
        }
    }

    recurse(Cube(bots.map { it.pos }))
    return bestPoint.manhattanDistance(Point3.ORIGIN)
}

private data class Cube(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {

    constructor(bots: Iterable<Point3>) : this(
        xRange = bots.minOf { it.x }..bots.maxOf { it.x },
        yRange = bots.minOf { it.y }..bots.maxOf { it.y },
        zRange = bots.minOf { it.z }..bots.maxOf { it.z },
    )

    fun toPoint() = Point3(xRange.first, yRange.first, zRange.first)

    fun divide(): List<Cube> = buildList {
        if (xRange.splittable || yRange.splittable || zRange.splittable)
            for (x in xRange.split())
                for (y in yRange.split())
                    for (z in zRange.split())
                        add(Cube(x, y, z))
    }

    fun closestPoint(p: Point3) = Point3(xRange.closest(p.x), yRange.closest(p.y), zRange.closest(p.z))

    fun upperBoundForScore(bots: List<Nanobot>): Int =
        bots.count { closestPoint(it.pos).manhattanDistance(it.pos) <= it.range }
}

private fun IntRange.closest(x: Int): Int = when {
    x < first -> first
    x > last -> last
    else -> x
}

private val IntRange.splittable: Boolean
    get() = first != last

private fun IntRange.split(): List<IntRange> {
    if (first >= last)
        return listOf(this)

    val mid = (last + first) / 2
    return listOf(first..mid, mid + 1..last)
}

private data class Nanobot(val pos: Point3, val range: Int) {

    companion object {
        private val regex = Regex("""pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""")

        fun parse(s: String): Nanobot {
            val (x, y, z, r) = regex.matchEntire(s)?.destructured ?: error("no match '$s'")

            return Nanobot(Point3(x.toInt(), y.toInt(), z.toInt()), r.toInt())
        }
    }
}

private data class Point3(val x: Int, val y: Int, val z: Int) {
    override fun toString() = "<$x,$y,$z>"

    fun manhattanDistance(p: Point3): Int =
        abs(x - p.x) + abs(y - p.y) + abs(z - p.z)

    companion object {
        val ORIGIN = Point3(0, 0, 0)
    }
}
