package komu.adventofcode.aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import komu.adventofcode.utils.sumByLong
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OperationOrderTest {

    @Test
    fun `example 1`() {
        assertEquals(71, operationOrder1("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals(51, operationOrder1("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals(26, operationOrder1("2 * 3 + (4 * 5)"))
        assertEquals(437, operationOrder1("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals(12240, operationOrder1("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals(13632, operationOrder1("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }

    @Test
    fun `example 2`() {
        assertEquals(231, operationOrder2("1 + 2 * 3 + 4 * 5 + 6"))
        assertEquals(51, operationOrder2("1 + (2 * 3) + (4 * (5 + 6))"))
        assertEquals(46, operationOrder2("2 * 3 + (4 * 5)"))
        assertEquals(1445, operationOrder2("5 + (8 * 3 + 9 + 3 * 4 * 3)"))
        assertEquals(669060, operationOrder2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
        assertEquals(23340, operationOrder2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"))
    }

    @Test
    fun `part 1`() {
        assertEquals(98621258158412, readTestInput("/2020/OperationOrder.txt").nonEmptyLines().sumByLong { operationOrder1(it) })
    }

    @Test
    fun `part 2`() {
        assertEquals(241216538527890, readTestInput("/2020/OperationOrder.txt").nonEmptyLines().sumByLong { operationOrder2(it) })
    }
}
