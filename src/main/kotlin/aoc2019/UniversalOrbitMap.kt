package aoc201

import komu.adventofcode.utils.nonEmptyLines

private typealias OrbitalObject = String
private typealias OrbitMap = Map<OrbitalObject, OrbitalObject>

fun universalOrbitMap1(input: String): Int {
    val map = parseOrbitMap(input)
    return map.keys.sumBy { map.stepsToCenterOfMass(it).size }
}

fun universalOrbitMap2(input: String): Int {
    val map = parseOrbitMap(input)

    val myRoute = map.stepsToCenterOfMass("YOU")
    val santaRoute = map.stepsToCenterOfMass("SAN")

    for ((i, node) in myRoute.withIndex()) {
        val santaSteps = santaRoute.indexOf(node)
        if (santaSteps != -1)
            return i + santaSteps - 2
    }

    error("no common ancestor")
}

private fun OrbitMap.stepsToCenterOfMass(start: OrbitalObject): List<String> {
    val steps = mutableListOf<OrbitalObject>()
    var node = start

    while (node != "COM") {
        steps += node
        node = this[node] ?: error("no parent for '$node'")
    }

    return steps
}

private val orbitRegex = Regex("""(.+)\)(.+)""")

private fun parseOrbitMap(input: String): Map<String, String> =
    input.nonEmptyLines()
        .associate { (orbitRegex.matchEntire(it) ?: error("no match for '$it'")).groupValues.let { g -> g[2] to g[1] } }
