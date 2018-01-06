package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TheIdealStockingStufferTest {

    @Test
    fun `part 1`() {
        assertEquals(117946, idealStockingStuffer("ckczppom", zeroes = 5))
    }

    @Test
    fun `part 2`() {
        assertEquals(3938038, idealStockingStuffer("ckczppom", zeroes = 6))
    }
}
