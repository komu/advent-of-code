package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SomeAssemblyRequiredTest {

    @Test
    fun `part 1`() {
        assertEquals(956, someAssemblyRequired(readInput()))
    }

    @Test
    fun `part 2`() {
        assertEquals(14643, someAssemblyRequired(readInput(), overrides = mapOf("b" to 40149)))
    }

    private fun readInput() = readTestInput("/2015/SomeAssemblyRequired.txt")
}
