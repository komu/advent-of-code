package komu.adventofcode.aoc2016

import kotlin.test.Test
import kotlin.test.assertEquals

class OneTimePadTest {

    private val salt = "ahsbgdzn"

    @Test
    fun example1() {
        assertEquals(22728, oneTimePad1("abc"))
    }

    @Test
    fun part1() {
        assertEquals(23890, oneTimePad1(salt))
    }

    @Test
    fun part2() {
        assertEquals(22696, oneTimePad2(salt))
    }
}