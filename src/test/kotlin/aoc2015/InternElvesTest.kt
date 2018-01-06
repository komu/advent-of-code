package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InternElvesTest {

    @Test
    fun `part 1`() {
        assertEquals(236, countNiceStrings(readTestInput("/2015/InternElves.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(51, countNiceStrings2(readTestInput("/2015/InternElves.txt")))
    }
}
