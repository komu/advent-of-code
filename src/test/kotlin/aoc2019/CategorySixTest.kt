package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CategorySixTest {

    @Test
    fun `part 1`() {
        assertEquals(17714, categorySix(readTestInput("/2019/CategorySix.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(10982, categorySix2(readTestInput("/2019/CategorySix.txt")))
    }
}
