package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SpaceStoichiometryTest {

    @Test
    fun `part 1`() {
        assertEquals(720484, spaceStoichiometry1(readTestInput("/2019/SpaceStoichiometry.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(1993284, spaceStoichiometry2(readTestInput("/2019/SpaceStoichiometry.txt")))
    }
}
