package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun firewallRules1(input: String): UInt =
    FirewallRule.parseRules(input).mergeSuccessive().first().high + 1u

fun firewallRules2(input: String): UInt =
    FirewallRule.parseRules(input).mergeSuccessive().zipWithNext().sumOf { (r1, r2) -> r1.gap(r2) }

private fun List<FirewallRule>.mergeSuccessive(): List<FirewallRule> {
    val result = mutableListOf<FirewallRule>()

    val rules = this.sortedBy { it.low }
    val it = rules.iterator()
    var last = it.next()

    while (it.hasNext()) {
        val current = it.next()
        if (current.low - 1u <= last.high)
            last = last.merge(current)
        else {
            result.add(last)
            last = current
        }
    }

    result.add(last)

    return result
}

private data class FirewallRule(val low: UInt, val high: UInt) {

    operator fun contains(number: UInt) = number in low..high

    fun merge(r: FirewallRule) =
        FirewallRule(minOf(low, r.low), maxOf(high, r.high))

    fun gap(r: FirewallRule) =
        r.low - high - 1u

    companion object {
        fun parseRules(s: String) =
            s.nonEmptyLines().map { parse(it) }

        private fun parse(s: String): FirewallRule {
            val (l, h) = s.split('-')

            return FirewallRule(l.toUInt(), h.toUInt())
        }
    }
}