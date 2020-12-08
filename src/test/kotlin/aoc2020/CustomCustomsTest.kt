package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CustomCustomsTest {

    private val exampleData = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()

    @Test
    fun `example 1`() {
        assertEquals(11, customCustoms1(exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(6351, customCustoms1(readTestInput("/2020/CustomCustoms.txt").trim()))
    }

    @Test
    fun `example 2`() {
        assertEquals(6, customCustoms2(exampleData))
    }

    @Test
    fun `part 2`() {
        assertEquals(3143, customCustoms2(readTestInput("/2020/CustomCustoms.txt").trim()))
    }
}
