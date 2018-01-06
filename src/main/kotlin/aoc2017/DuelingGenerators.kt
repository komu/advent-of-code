package komu.adventofcode.aoc2017

import kotlin.coroutines.experimental.buildSequence

fun duelingGenerators(startA: Int, startB: Int, rounds: Int, multiplesOfA: Int = 1, multiplesOfB: Int = 1) =
    generator(factor = 16807, start = startA, multiplier = multiplesOfA)
            .zip(generator(factor = 48271, start = startB, multiplier = multiplesOfB))
            .take(rounds)
            .count { (a, b) -> a.lowBits == b.lowBits }

private fun generator(factor: Int, start: Int, multiplier: Int = 1) = buildSequence {
    var state = start.toLong()

    while (true) {
        state = (state * factor) % 2147483647

        if (state.toInt() % multiplier == 0)
            yield(state.toInt())
    }
}

private val Int.lowBits get() = this and 65535
