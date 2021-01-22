package aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ComboBreakerTest {

    @Test
    fun `example 1`() {
        assertEquals(14897079, comboBreaker1(5764801, 17807724))
    }

    @Test
    fun `part 1`() {
        assertEquals(9177528, comboBreaker1(8421034, 15993936))
    }
}
