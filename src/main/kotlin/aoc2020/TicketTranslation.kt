package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.product

fun ticketTranslation1(input: String): Int {
    val (rules, _, nearbyTickets) = parseTicketData(input)

    return nearbyTickets.sumBy { ticket ->
        ticket.filter { v -> !rules.any { it.isSatisfiedBy(v) } }.sum()
    }
}

fun ticketTranslation2(input: String): Long {
    val (rules, myTicket, nearbyTickets) = parseTicketData(input)

    val validNearbyTickets = nearbyTickets.filter { t -> t.none { v -> !rules.any { it.isSatisfiedBy(v) } } }

    val assignment = resolveAssignments(rules, validNearbyTickets)

    val indices = assignment.filterKeys { it.name.startsWith("departure") }.values
    return indices.map { i -> myTicket[i].toLong() }.product()
}

private typealias Ticket = List<Int>

private fun resolveAssignments(rules: List<TicketRule>, tickets: Collection<Ticket>): Map<TicketRule, Int> {
    val candidatesByIndex = List(rules.size) { index ->
        rules.filter { rule -> tickets.all { rule.isSatisfiedBy(it[index]) } }.toMutableSet()
    }

    while (candidatesByIndex.any { it.size > 1 }) {
        val uniqueRules = candidatesByIndex.filter { it.size == 1 }.map { it.first() }
        for (ruleSet in candidatesByIndex)
            if (ruleSet.size > 1)
                ruleSet -= uniqueRules
    }

    return candidatesByIndex.map { it.single() }.mapIndexed { index, rule -> rule to index }.toMap()
}

private class TicketRule(
    val name: String,
    private val range1: ClosedRange<Int>,
    private val range2: ClosedRange<Int>
) {
    fun isSatisfiedBy(value: Int) =
        value in range1 || value in range2

    companion object {

        private val ruleRegex = Regex("""([\w\s]+): (\d+)-(\d+) or (\d+)-(\d+)""")

        fun parse(rule: String): TicketRule {
            val (name, min1, max1, min2, max2) = ruleRegex.matchEntire(rule)?.groupValues?.drop(1)
                ?: error("invalid rule '$rule'")

            return TicketRule(name, min1.toInt()..max1.toInt(), min2.toInt()..max2.toInt())
        }
    }
}

private fun parseTicketData(input: String): Triple<List<TicketRule>, Ticket, List<Ticket>> {
    val (rulesStr, myTicketStr, nearbyTicketsStr) = input.split("\n\n")

    val rules = rulesStr.nonEmptyLines().map { TicketRule.parse(it) }
    val myTicket = parseTicket(myTicketStr.removePrefix("your ticket:\n"))
    val nearbyTickets = nearbyTicketsStr.removePrefix("nearby tickets:\n").nonEmptyLines().map { parseTicket(it) }

    return Triple(rules, myTicket, nearbyTickets)
}

private fun parseTicket(line: String) =
    line.split(",").map { it.toInt() }
