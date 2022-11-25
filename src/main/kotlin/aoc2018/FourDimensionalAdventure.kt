package komu.adventofcode.aoc2018

import komu.adventofcode.utils.nonEmptyLines
import kotlin.math.abs

fun fourDimensionalAdventure1(input: String): Int {
    val stars = input.nonEmptyLines().map { Point4.parse(it) }

    val constellations = stars.map { Constellation(listOf(it)) }.toMutableList()
    do {
        val oldSize = constellations.size
        mergeConstellations(constellations)

    } while (oldSize != constellations.size)

    return constellations.size
}

private fun mergeConstellations(constellations: MutableList<Constellation>) {
    for ((i, c1) in constellations.withIndex()) {
        for (j in i + 1..constellations.lastIndex) {
            val c2 = constellations[j]
            if (c1.isSameAs(c2)) {
                constellations.removeAt(j)
                constellations.removeAt(i)
                constellations.add(c1.merge(c2))
                return
            }
        }
    }
}

private class Constellation(val stars: List<Point4>) {

    fun isSameAs(c: Constellation): Boolean =
        stars.any { s1 -> c.stars.any { s2 -> s1.manhattanDistance(s2) <= 3 } }

    fun merge(c: Constellation): Constellation {
        return Constellation(stars + c.stars)
    }
}

private data class Point4(val x: Int, val y: Int, val z: Int, val w: Int) {
    override fun toString() = "<$x,$y,$z,$w>"

    fun manhattanDistance(p: Point4): Int =
        abs(x - p.x) + abs(y - p.y) + abs(z - p.z) + abs(w - p.w)

    companion object {
        private val regex = Regex("""(-?\d+),(-?\d+),(-?\d+),(-?\d+)""")

        fun parse(s: String): Point4 {
            val (x, y, z, w) = regex.matchEntire(s)?.destructured ?: error("no match '$s'")

            return Point4(x.toInt(), y.toInt(), z.toInt(), w.toInt())
        }
    }
}
