package komu.adventofcode.aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun monsterMessages(input: String, alterRule: (String) -> String = { it }): Int {
    val (rulesPart, messagesPart) = input.split("\n\n")

    val rule = buildRules(rulesPart.nonEmptyLines().map(alterRule))
    val messages = messagesPart.nonEmptyLines()

    return messages.count { rule.matches(it) }
}

fun monsterMessages2(input: String): Int = monsterMessages(input) {
    when {
        it.startsWith("8:") -> "8: 42 | 42 8"
        it.startsWith("11:") -> "11: 42 31 | 42 11 31"
        else -> it
    }
}

private fun buildRules(rules: List<String>): Rule {
    val rulePattern = Regex("""(\d+): (.+)""")
    val ruleMap = mutableMapOf<Int, Lazy<Rule>>()

    ruleMap += rules.associate { s ->
        val (_, id, text) = rulePattern.matchEntire(s)?.groupValues ?: error("invalid rule '$s'")

        id.toInt() to lazy {
            Rule.parse(text, ruleMap)
        }
    }

    return ruleMap[0]!!.value
}

private sealed class Rule {

    fun matches(s: String) =
        accept(s).any { it == "" }

    protected abstract fun accept(input: String): List<String>

    class Literal(private val text: String) : Rule() {
        override fun accept(input: String) =
            if (input.startsWith(text)) listOf(input.removePrefix(text)) else emptyList()
    }

    class Sequence(private val rules: List<Lazy<Rule>>) : Rule() {
        override fun accept(input: String) =
            rules.fold(listOf(input)) { remaining, rule -> remaining.flatMap { rule.value.accept(it) }}
    }

    class OneOf(private val rules: List<Rule>) : Rule() {
        override fun accept(input: String) = rules.flatMap { it .accept(input) }
    }

    companion object {

        fun parse(rule: String, ruleMap: Map<Int, Lazy<Rule>>): Rule = when {
            rule[0] == '"' ->
                Literal(rule.substring(1, rule.length - 1))
            '|' in rule ->
                OneOf(rule.split(" | ").map { parse(it, ruleMap) })
            else ->
                Sequence(rule.split(' ').map { ruleMap[it.toInt()]!! })
        }
    }
}
