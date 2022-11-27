package komu.adventofcode.aoc2016

import kotlin.test.Test
import kotlin.test.assertEquals

class AnElephantNamedJosephTest {

    private val input = 3_001_330

    @Test
    fun example1() {
        assertEquals(3, anElephantNamedJoseph1(5))
    }

    @Test
    fun example2() {
        assertEquals(2, anElephantNamedJoseph2(5))
    }

    @Test
    fun part1() {
        assertEquals(1808357, anElephantNamedJoseph1(input))
    }

    @Test
    fun part2() {
        assertEquals(1407007, anElephantNamedJoseph2(input))
    }
}