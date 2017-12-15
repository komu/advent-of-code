package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DuelingGeneratorsTest {

    @Test
    fun `example input`() {
        assertEquals(1, duelingGenerators(startA = 65, startB = 8921, rounds = 5))
    }

    @Test
    fun `real input`() {
        assertEquals(609, duelingGenerators(startA = 883, startB = 879, rounds = 40_000_000))
    }

    @Test
    fun `example input 2`() {
        assertEquals(0, duelingGenerators(startA = 65, startB = 8921, rounds = 5, multiplesOfA = 4, multiplesOfB = 8))
    }

    @Test
    fun `real input 2`() {
        assertEquals(253, duelingGenerators(startA = 883, startB = 879, rounds = 5_000_000, multiplesOfA = 4, multiplesOfB = 8))
    }
}