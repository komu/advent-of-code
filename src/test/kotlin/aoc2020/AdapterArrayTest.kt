package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AdapterArrayTest {

    private val testData = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
    private val testData2 = listOf(28, 33, 18, 42, 31, 14, 46, 20, 48, 47, 24, 23, 49, 45, 19, 38, 39, 11, 1, 32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3)

    @Test
    fun `example 1`() {
        assertEquals(35, adapterArray1(testData))
    }

    @Test
    fun `part 1`() {
        assertEquals(2030, adapterArray1(readTestInput("/2020/AdapterArray.txt").nonEmptyLines().map { it.toInt() }))
    }

    @Test
    fun `example 2`() {
        assertEquals(8, adapterArray2(testData))
        assertEquals(19208, adapterArray2(testData2))
    }

    @Test
    fun `part 2`() {
        assertEquals(42313823813632, adapterArray2(readTestInput("/2020/AdapterArray.txt").nonEmptyLines().map { it.toInt() }))
    }
}
