package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InternetProtocolVersion7Test {

    @Test
    fun part1() {
        assertEquals(105, ipv7Part1(readTestInput("/2016/InternetProtocolVersion7.txt")))
    }

    @Test
    fun part2() {
        assertEquals(258, ipv7Part2(readTestInput("/2016/InternetProtocolVersion7.txt")))
    }
}
