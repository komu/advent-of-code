package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HandheldHaltingTest {

    private val exampleData = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent()


    @Test
    fun `example 1`() {
        assertEquals(5, handheldHalting1(exampleData))
    }

    @Test
    fun `example 2`() {
        assertEquals(8, handheldHalting2(exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(1684, handheldHalting1(readTestInput("/2020/HandheldHalting.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(2188, handheldHalting2(readTestInput("/2020/HandheldHalting.txt")))
    }
}
