package komu.adventofcode.aoc2016

import kotlin.test.Test
import kotlin.test.assertEquals

class DragonChecksumTest {

    private val input = "10111011111001111"

    @Test
    fun example1() {
        assertEquals("01100", dragonChecksum1("10000", 20))
    }

    @Test
    fun part1() {
        assertEquals("11101010111100010", dragonChecksum1(input, 272))
    }

    @Test
    fun part2() {
        assertEquals("01001101001000101", dragonChecksum1(input, 35651584))
    }
}