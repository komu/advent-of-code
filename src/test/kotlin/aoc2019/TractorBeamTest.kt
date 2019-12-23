package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TractorBeamTest {

    @Test
    fun `part 1`() {
        assertEquals(129, tractorBeam1(readTestInput("/2019/TractorBeam.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(14040699, tractorBeam2(readTestInput("/2019/TractorBeam.txt")))
    }
}
