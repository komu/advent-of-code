package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReindeerOlympicsTest {

    @Test
    fun `part 1`() {
        assertEquals(2696, reindeerOlympics1(readTestInput("/2015/ReindeerOlympics.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(1084, reindeerOlympics2(readTestInput("/2015/ReindeerOlympics.txt")))
    }
}
