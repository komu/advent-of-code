package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NotQuiteLispTest {

    @Test
    fun `real data`() {
        assertEquals(138, notQuiteLisp(readTestInput("/2015/NotQuiteLisp.txt")))
    }

    @Test
    fun `real data - 2`() {
        assertEquals(1771, notQuiteLispBasement(readTestInput("/2015/NotQuiteLisp.txt")))
    }
}
