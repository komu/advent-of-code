package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class GridComputingTest {

    @Test
    fun part1() {
        assertEquals(1034, gridComputing1(readTestInput("/2016/GridComputing.txt")))
    }

    @Test
    fun part2() {
        assertEquals(261, gridComputing2(readTestInput("/2016/GridComputing.txt")))
    }
}
