package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class IWasToldThereWouldBeNoMathTest {

    @Test
    fun `part 1`() {
        assertEquals(1606483, totalWrappingPaperNeeded(readTestInput("/2015/IWasToldThereWouldBeNoMath.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(3842356, totalRibbonNeeded(readTestInput("/2015/IWasToldThereWouldBeNoMath.txt")))
    }
}
