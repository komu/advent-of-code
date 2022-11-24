package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class TimingIsEverythingTest {

    @Test
    fun example1() {
        assertEquals(
            5, timingIsEverything1(
                """
                   Disc #1 has 5 positions; at time=0, it is at position 4.
                   Disc #2 has 2 positions; at time=0, it is at position 1.
                """.trimIndent()
            )
        )
    }

    @Test
    fun part1() {
        assertEquals(121834, timingIsEverything1(readTestInput("/2016/TimingIsEverything.txt")))
    }

    @Test
    fun part2() {
        assertEquals(3208099, timingIsEverything2(readTestInput("/2016/TimingIsEverything.txt")))
    }
}