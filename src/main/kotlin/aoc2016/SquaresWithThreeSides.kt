package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun squaresWithThreeSides1(input: String): Int {
    val triangles = input.nonEmptyLines().map { Triangle.parse(it) }

    return triangles.count { it.isPossible() }
}

fun squaresWithThreeSides2(input: String): Int {
    val triangles = input.nonEmptyLines().map { Triangle.parse(it) }.chunked(3).flatMap { (a,b,c) ->
        listOf(Triangle(a.x, b.x, c.x), Triangle(a.y, b.y, c.y), Triangle(a.z, b.z, c.z))
    }

    return triangles.count { it.isPossible() }
}

private data class Triangle(val x: Int, val y: Int, val z: Int) {

    fun isPossible(): Boolean {
        val hypothenuse = maxOf(x, y, z)
        val sides = x + y + z - hypothenuse
        return sides > hypothenuse
    }

    companion object {

        private val pattern = Regex("""\s*(\d+)\s+(\d+)\s+(\d+)""")

        fun parse(s: String): Triangle {
            val (_, x, y, z) = pattern.matchEntire(s)?.groupValues ?: error("invalid input '$s'")

            return Triangle(x.toInt(), y.toInt(), z.toInt())
        }
    }
}
