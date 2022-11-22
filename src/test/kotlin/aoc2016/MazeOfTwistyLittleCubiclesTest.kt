package komu.adventofcode.aoc2016

import kotlin.test.Test
import kotlin.test.assertEquals

class MazeOfTwistyLittleCubiclesTest {

    @Test
    fun part1() {
        assertEquals(86, mazeOfTwistyLittleCubicles1())
    }

    @Test
    fun part2() {
        assertEquals(127, mazeOfTwistyLittleCubicles2())
    }
}