package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SquaresWithThreeSidesTest {

    @Test
    fun `part 1`() {
        assertEquals(993, squaresWithThreeSides1(readTestInput("/2016/SquaresWithThreeSides.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(1849, squaresWithThreeSides2(readTestInput("/2016/SquaresWithThreeSides.txt")))
    }
}
