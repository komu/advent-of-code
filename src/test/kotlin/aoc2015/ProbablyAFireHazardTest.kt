package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ProbablyAFireHazardTest {

    @Test
    fun `part 1`() {
        assertEquals(543903, probablyAFireHazard(readTestInput("/2015/ProbablyAFireHazard.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(14687245, probablyAFireHazard2(readTestInput("/2015/ProbablyAFireHazard.txt")))
    }
}
