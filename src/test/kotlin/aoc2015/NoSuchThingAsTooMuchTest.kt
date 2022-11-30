package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoSuchThingAsTooMuchTest {

    private val data = listOf(33, 14, 18, 20, 45, 35, 16, 35, 1, 13, 18, 13, 50, 44, 48, 6, 24, 41, 30, 42)

    @Test
    fun example1() {
        assertEquals(4, noSuchThingAsTooMuch1(25, listOf(20, 15, 10, 5, 5)))
    }

    @Test
    fun example2() {
        assertEquals(3, noSuchThingAsTooMuch2(25, listOf(20, 15, 10, 5, 5)))
    }

    @Test
    fun part1() {
        assertEquals(1304, noSuchThingAsTooMuch1(150, data))
    }

    @Test
    fun part2() {
        assertEquals(18, noSuchThingAsTooMuch2(150, data))
    }
}
