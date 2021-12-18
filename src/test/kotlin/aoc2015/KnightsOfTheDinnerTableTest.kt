package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class KnightsOfTheDinnerTableTest {

    @Test
    fun `example 1`() {
        assertEquals(330, knightsOfTheDinnerTable1(readTestInput("/2015/KnightsOfTheDinnerTable_test.txt")))
    }

    @Test
    fun `part 1`() {
        assertEquals(733, knightsOfTheDinnerTable1(readTestInput("/2015/KnightsOfTheDinnerTable.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(725, knightsOfTheDinnerTable2(readTestInput("/2015/KnightsOfTheDinnerTable.txt")))
    }
}
