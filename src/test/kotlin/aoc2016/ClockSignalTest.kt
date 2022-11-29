package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class ClockSignalTest {

    @Test
    fun testCompatibility() {
        verifyClockSignalCompatibility(readTestInput("/2016/ClockSignal.txt"))
    }

    @Test
    fun part1() {
        assertEquals(196, clockSignal())
    }
}