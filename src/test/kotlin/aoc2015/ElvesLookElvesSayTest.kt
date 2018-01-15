package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ElvesLookElvesSayTest {

    @Test
    fun `part 1`() {
        assertEquals(329356, elvesLookElvesSay("3113322113", 40))
    }

    @Test
    fun `part 2`() {
        assertEquals(4666278, elvesLookElvesSay("3113322113", 50))
    }
}
