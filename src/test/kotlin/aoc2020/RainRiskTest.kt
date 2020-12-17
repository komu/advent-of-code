package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RainRiskTest {

    private val exampleInput = listOf("F10", "N3", "F7", "R90", "F11")

    @Test
    fun `example 1`() {
        assertEquals(25, rainRisk1(exampleInput))
    }

    @Test
    fun `example 2`() {
        assertEquals(286, rainRisk2(exampleInput))
    }

    @Test
    fun `part 1`() {
        assertEquals(1186, rainRisk1(readTestInput("/2020/RainRisk.txt").nonEmptyLines()))
    }

    @Test
    fun `part 2`() {
        assertEquals(47806, rainRisk2(readTestInput("/2020/RainRisk.txt").nonEmptyLines()))
    }
}
