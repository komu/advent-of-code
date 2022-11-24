package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines

fun timingIsEverything1(input: String): Int =
    timingIsEverything(Disc.parseLines(input))

fun timingIsEverything2(input: String): Int =
    timingIsEverything(Disc.parseLines(input) + Disc(id = 7, positionCount = 11, initialPosition = 0))

private fun timingIsEverything(discs: List<Disc>): Int {
    var time = 0
    while (true) {
        if (discs.all { it.positionAt(it.id + time) == 0 })
            return time
        time++
    }
}

private data class Disc(val id: Int, val positionCount: Int, val initialPosition: Int) {

    fun positionAt(time: Int) =
        (initialPosition + time) % positionCount

    companion object {
        private val regex = Regex("""Disc #(\d+) has (\d+) positions; at time=0, it is at position (\d+).""")

        fun parseLines(input: String) = input.nonEmptyLines().map { parse(it) }

        private fun parse(s: String): Disc {
            val (id, positionCount, initialPosition) = regex.matchEntire(s)?.destructured ?: error("no match '$s'")

            return Disc(id.toInt(), positionCount.toInt(), initialPosition.toInt())
        }
    }
}