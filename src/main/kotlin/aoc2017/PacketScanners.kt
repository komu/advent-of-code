package komu.adventofcode.aoc2017

import komu.adventofcode.utils.nonEmptyLines

fun packetScannersSeverity(input: String): Int =
    parseFirewallConfiguration(input)
            .filterNot { it.isSafe(0) }
            .sumBy { it.severity }

fun packetScannersMinimumDelay(input: String): Int {
    val config = parseFirewallConfiguration(input)
    return (0..Int.MAX_VALUE).first { delay ->
        config.all { it.isSafe(delay) }
    }
}

private class Scanner(val depth: Int, range: Int) {
    val period = (range - 1) * 2
    val severity = depth * range

    fun isSafe(delay: Int) = (depth + delay) % period != 0
}

private val configRegex = Regex("""(\d+): (\d+)""")

private fun parseFirewallConfiguration(input: String) =
    input.nonEmptyLines().map {
        val m = configRegex.matchEntire(it) ?: error("no match for '$it'")
        Scanner(m.groupValues[1].toInt(), m.groupValues[2].toInt())
    }
