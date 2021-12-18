package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.permutations

private val dinnerTablePattern = Regex("""(.+) would (lose|gain) (\d+) happiness units by sitting next to (.+).""")

private fun dinnerTableScore(permutation: List<String>, rulesByPerson: Map<String, List<DinnerTableRule>>): Int {
    var score = 0
    for ((i, person) in permutation.withIndex()) {
        val left = permutation[(i + permutation.size - 1) % permutation.size]
        val right = permutation[(i + 1) % permutation.size]

        score += rulesByPerson[person]!!.filter { it.other == left || it.other == right }.sumOf { it.change }
    }
    return score
}

private fun bestDinnerTableScore(rules: List<DinnerTableRule>): Int {
    val rulesByPerson = rules.groupBy { it.person }
    var bestScore = Int.MIN_VALUE

    for (permutation in rulesByPerson.keys.toList().permutations())
        bestScore = maxOf(bestScore, dinnerTableScore(permutation, rulesByPerson))

    return bestScore
}

fun knightsOfTheDinnerTable1(input: String) =
    bestDinnerTableScore(input.nonEmptyLines().map { DinnerTableRule.parse(it) })

fun knightsOfTheDinnerTable2(input: String): Int {
    val rules = input.nonEmptyLines().map { DinnerTableRule.parse(it) }.toMutableList()

    for (person in rules.map { it.person }.distinct()) {
        rules.add(DinnerTableRule("me", 0, person))
        rules.add(DinnerTableRule(person, 0, "me"))
    }

    return bestDinnerTableScore(rules)
}

private data class DinnerTableRule(val person: String, val change: Int, val other: String) {
    companion object {

        fun parse(s: String): DinnerTableRule {
            val (_, person, op, num, other) = dinnerTablePattern.matchEntire(s)?.groupValues ?: error("no match '$s'")
            val change = if (op == "gain") num.toInt() else -num.toInt()
            return DinnerTableRule(person, change, other)
        }
    }
}
