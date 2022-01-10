package komu.adventofcode.aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EncodingErrorTest {

    private val exampleData = listOf(35, 20, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576).map { it.toLong() }

    @Test
    fun `example 1`() {
        assertEquals(127, encodingError1(5, exampleData))
    }

    @Test
    fun `example 2`() {
        assertEquals(62, encodingError2(5, exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(14144619, encodingError1(25, readTestInput("/2020/EncodingError.txt").nonEmptyLines().map { it.toLong() }))
    }

    @Test
    fun `part 2`() {
        assertEquals(1766397, encodingError2(25, readTestInput("/2020/EncodingError.txt").nonEmptyLines().map { it.toLong() }))
    }
}
