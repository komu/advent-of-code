package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ItHangsInTheBalanceTest {

    @Test
    fun part1() {
        assertEquals(11266889531, itHangsInTheBalance1(readTestInput("/2015/ItHangsInTheBalance.txt")))
    }

    @Test
    fun part2() {
        assertEquals(77387711, itHangsInTheBalance2(readTestInput("/2015/ItHangsInTheBalance.txt")))
    }
}
