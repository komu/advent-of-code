package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class AirDuctSpelunkingTest {

    @Test
    fun part1() {
        assertEquals(464, airDuctSpelunking(readTestInput("/2016/AirDuctSpelunking.txt"), returnToStart = false))
    }

    @Test
    fun part2() {
        assertEquals(652, airDuctSpelunking(readTestInput("/2016/AirDuctSpelunking.txt"), returnToStart = true))
    }
}