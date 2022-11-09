package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class BalanceBotsTest {

    @Test
    fun part1() {
        assertEquals(73, balanceBots1(readTestInput("/2016/BalanceBots.txt")))
    }

    @Test
    fun part2() {
        assertEquals(3965, balanceBots2(readTestInput("/2016/BalanceBots.txt")))
    }
}