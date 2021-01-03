package aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TicketTranslationTest {

    @Test
    fun `example 1`() {
        assertEquals(71, ticketTranslation1("""
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50

            your ticket:
            7,1,14

            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
        """.trimIndent()))
    }

    @Test
    fun `example 2`() {
        ticketTranslation2("""
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19
            
            your ticket:
            11,12,13
            
            nearby tickets:
            3,9,18
            15,1,5
            5,14,9
        """.trimIndent())
    }

    @Test
    fun `part 1`() {
        assertEquals(24110, ticketTranslation1(readTestInput("/2020/TicketTranslation.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(6766503490793, ticketTranslation2(readTestInput("/2020/TicketTranslation.txt")))
    }
}
