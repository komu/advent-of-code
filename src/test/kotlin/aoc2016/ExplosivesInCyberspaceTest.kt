package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class ExplosivesInCyberspaceTest {

    @Test
    fun part1() {
        assertEquals(97714, explosivesInCyberspace1(readTestInput("/2016/ExplosivesInCyberspace.txt")))
    }

    @Test
    fun part2() {
        assertEquals(10762972461, explosivesInCyberspace2(readTestInput("/2016/ExplosivesInCyberspace.txt")))
    }
}