package komu.adventofcode.aoc2017

import komu.adventofcode.utils.nonEmptyLines
import java.util.Comparator.comparing

fun electromagneticMoat(input: String, longest: Boolean = false): Int {
    val components = input.nonEmptyLines().map { Component.parse(it) }.toSet()

    val comparator = if (longest) comparing(Result::length).thenComparing(Result::strength) else comparing(Result::strength)

    return moat(0, components, comparator).strength
}

private fun moat(pin: Int, available: Set<Component>, comparator: Comparator<Result>): Result =
    available.filter { it.fits(pin) }.map { c ->
        moat(c.other(pin), available - c, comparator).add(c.strength)
    }.maxWithOrNull(comparator) ?: Result(0, 0)

data class Result(val length: Int, val strength: Int) {

    fun add(strength: Int) = Result(length + 1, this.strength + strength)
}

private class Component(val pins1: Int, val pins2: Int) {
    val strength: Int
        get() = pins1 + pins2

    fun fits(pin: Int) = pin == pins1 || pin == pins2

    fun other(pin: Int) = strength - pin

    override fun toString() = "$pins1/$pins2"

    companion object {

        private val regex = Regex("""(\d+)/(\d+)""")

        fun parse(s: String): Component {
            val match = regex.matchEntire(s) ?: error("invalid input '$s'")

            return Component(match.groupValues[1].toInt(), match.groupValues[2].toInt())
        }
    }
}
