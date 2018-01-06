package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoTimeForATaxicabTest {

    @Test
    fun `part 1`() {
        assertEquals(209, taxicab(readTestInput("/2016/NoTimeForATaxicabTest.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(136, taxicab2(readTestInput("/2016/NoTimeForATaxicabTest.txt")))
    }
}
