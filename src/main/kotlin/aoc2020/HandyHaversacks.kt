package komu.adventofcode.aoc2020

import komu.adventofcode.utils.nonEmptyLines

private val rulePattern = Regex("""(.+) bags contain (.+)\.""")
private val countPattern = Regex("""(\d+) (.+) bags?""")

fun handyHaversacks1(data: String): Int {
    val rules = parseHaversackRules(data)

    val work = mutableListOf("shiny gold")
    val seen = mutableSetOf<String>()
    var count = 0

    while (work.isNotEmpty()) {
        val bag = work.removeLast()

        for ((otherBag, contains) in rules) {
            if (contains.any { it.second == bag } && seen.add(otherBag)) {
                count++
                work += otherBag
            }
        }
    }

    return count
}

fun handyHaversacks2(data: String): Int {
    val rules = parseHaversackRules(data)

    fun countNested(bag: String): Int =
        rules[bag]!!.sumBy { (count, b) ->
            count * (1 + countNested(b))
        }

    return countNested("shiny gold")
}

private fun parseHaversackRules(data: String): Map<String, Collection<Pair<Int, String>>> {
    val rules = mutableMapOf<String, Collection<Pair<Int, String>>>()

    for (line in data.nonEmptyLines()) {
        val (_, color, contains) = rulePattern.matchEntire(line)?.groupValues ?: error("no match '$line'")

        rules[color] = if (contains == "no other bags")
            emptyList()
        else
            contains.split(", ").map {
                val (_ , count, type) = countPattern.matchEntire(it)?.groupValues ?: error("no match '$it'")
                Pair(count.toInt(), type)
            }
    }

    return rules
}
