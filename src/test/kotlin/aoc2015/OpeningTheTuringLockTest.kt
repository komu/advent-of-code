package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OpeningTheTuringLockTest {

    @Test
    fun part1() {
        assertEquals(170, openingTheTuringLock1(readTestInput("/2015/OpeningTheTuringLock.txt")))
    }

    @Test
    fun part2() {
        assertEquals(247, openingTheTuringLock2(readTestInput("/2015/OpeningTheTuringLock.txt")))
    }
}
