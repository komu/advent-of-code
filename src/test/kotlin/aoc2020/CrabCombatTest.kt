package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrabCombatTest {

    private val exampleData = """
            Player 1:
            9
            2
            6
            3
            1

            Player 2:
            5
            8
            4
            7
            10
        """.trimIndent()

    @Test
    fun `example 1`() {
        assertEquals(306, crabCombat1(exampleData))
    }

    @Test
    fun `example 2`() {
        assertEquals(291, crabCombat2(exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(32199, crabCombat1(readTestInput("/2020/CrabCombat.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(33780, crabCombat2(readTestInput("/2020/CrabCombat.txt")))
    }
}
