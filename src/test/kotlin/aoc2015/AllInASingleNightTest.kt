package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AllInASingleNightTest {

    @Test
    fun `part 1 example`() {
        assertEquals(605, allInASingleNight("""
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals(117, allInASingleNight(readTestInput("/2015/AllInASingleNight.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(909, allInASingleNight(readTestInput("/2015/AllInASingleNight.txt"), longest = true))
    }
}
