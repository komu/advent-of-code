package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OxygenSystemTest {

    @Test
    fun `part 1`() {
        assertEquals(412, oxygenSystem1(readTestInput("/2019/OxygenSystem.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(418, oxygenSystem2(readTestInput("/2019/OxygenSystem.txt")))
    }
}
