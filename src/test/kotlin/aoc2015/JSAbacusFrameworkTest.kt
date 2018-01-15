package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JSAbacusFrameworkTest {

    @Test
    fun `part 1`() {
        assertEquals(191164, jsabacus(readTestInput("/2015/JSAbacusFramework.txt")))
    }

    @Test
    fun `part 12`() {
        assertEquals(87842, jsabacus(readTestInput("/2015/JSAbacusFramework.txt"), bailOnRed = true))
    }
}
