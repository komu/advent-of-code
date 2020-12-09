package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun allInASingleNight(input: String, longest: Boolean = false): Int {
    val legs = Leg.parseAll(input)
    val cities = legs.flatMap { it.cities }.toSet()

    val best = if (longest) Collection<Int>::maxOrNull else Collection<Int>::minOrNull

    fun recurse(currentCity: String, distance: Int, visited: List<String>): Int? {
        if (visited.size == cities.size)
            return distance

        val candidateLegs = legs.filter { it.from == currentCity && it.to !in visited }

        return best(candidateLegs.mapNotNull { leg ->
            recurse(leg.to, distance + leg.distance, visited + leg.to)
        })
    }

    return best(cities.mapNotNull { city ->
        recurse(city, 0, listOf(city))
    }) ?: error("no route")
}

private data class Leg(val from: String, val to: String, val distance: Int) {

    val cities: List<String>
        get() = listOf(from, to)

    fun reverse() = Leg(to, from, distance)

    companion object {
        private val regex = Regex("""(.+) to (.+) = (\d+)""")

        fun parseAll(s: String): List<Leg> {
            val legs = s.nonEmptyLines().map { parse(it) }
            return legs + legs.map { it.reverse() }
        }

        fun parse(s: String): Leg {
            val m = regex.matchEntire(s) ?: error("invalid input '$s'")

            return Leg(m.groupValues[1], m.groupValues[2], m.groupValues[3].toInt())
        }
    }
}
