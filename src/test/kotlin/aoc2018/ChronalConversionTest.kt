package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ChronalConversionTest {

    @Test
    fun `part 1`() {
        assertEquals(9107763, chronalConversion1(readTestInput("/2018/ChronalConversion.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(7877093, chronalConversion2())
    }
}
