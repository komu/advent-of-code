package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun signalsAndNoise1(input: String): String =
    process(input) { maxByOrNull { it.value } }

fun signalsAndNoise2(input: String): String =
    process(input) { minByOrNull { it.value }!! }

private fun process(input: String, selectEntry: Collection<Map.Entry<Char, Int>>.() -> Map.Entry<Char, Int>?): String {
    val distributions = List<MutableMap<Char, Int>>(8) { mutableMapOf() }

    for (line in input.nonEmptyLines())
        for ((i, c) in line.withIndex())
            distributions[i][c] = (distributions[i][c] ?: 0) + 1

    return distributions.joinToString("") { it.entries.selectEntry()?.key.toString() }
}
