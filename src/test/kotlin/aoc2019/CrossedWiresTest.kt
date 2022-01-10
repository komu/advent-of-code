package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CrossedWiresTest {

    @Test
    fun `part 1`() {
        assertEquals(1285, crossedWires(readTestInput("/2019/CrossedWires.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(14228, crossedWires2(readTestInput("/2019/CrossedWires.txt")))
    }
}
