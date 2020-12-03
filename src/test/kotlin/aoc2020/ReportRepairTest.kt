package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReportRepairTest {

    @Test
    fun `example input - part 1`() {
        assertEquals(514579, reportRepair(listOf(1721, 979, 366, 299, 675)))
    }

    @Test
    fun `example input - part 2`() {
        assertEquals(241861950, reportRepair2(listOf(1721, 979, 366, 299, 675)))
    }

    @Test
    fun `part 1`() {
        assertEquals(1006176, reportRepair(readTestInput("/2020/ReportRepair.txt").nonEmptyLines().map { it.toInt() }))
    }

    @Test
    fun `part 2`() {
        assertEquals(199132160, reportRepair2(readTestInput("/2020/ReportRepair.txt").nonEmptyLines().map { it.toInt() }))
    }
}
