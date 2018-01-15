package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MatchsticksTest {

    @Test
    fun `part 1`() {
        assertEquals(1350, matchsticks(readTestInput("/2015/Matchsticks.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(2085, matchsticks2(readTestInput("/2015/Matchsticks.txt")))
    }
}
