package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class FirewallRulesTest {

    @Test
    fun part1() {
        assertEquals(31053880u, firewallRules1(readTestInput("/2016/FirewallRules.txt")))
    }

    @Test
    fun part2() {
        assertEquals(117u, firewallRules2(readTestInput("/2016/FirewallRules.txt")))
    }
}
