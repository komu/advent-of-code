package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CorporatePolicyTest {

    @Test
    fun `part 1`() {
        assertEquals("vzbxxyzz", corporatePolicy("vzbxkghb"))
    }

    @Test
    fun `part 2`() {
        assertEquals("vzcaabcc", corporatePolicy("vzbxxyzz"))
    }
}
