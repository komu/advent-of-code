package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ChronalClassificationTest {

    @Test
    fun `part 1`() {
        assertEquals(590, chronalClassification(readTestInput("/2018/ChronalClassification.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(475, chronalClassification2(readTestInput("/2018/ChronalClassification.txt")))
    }
}
