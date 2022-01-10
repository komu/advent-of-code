package komu.adventofcode.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RambunctiousRecitationTest {

    @Test
    fun `example 1`() {
        assertEquals(436, rambunctiousRecitation1(listOf(0, 3, 6)))
        assertEquals(1, rambunctiousRecitation1(listOf(1, 3, 2)))
        assertEquals(10, rambunctiousRecitation1(listOf(2, 1, 3)))
        assertEquals(27, rambunctiousRecitation1(listOf(1, 2, 3)))
        assertEquals(78, rambunctiousRecitation1(listOf(2, 3, 1)))
        assertEquals(438, rambunctiousRecitation1(listOf(3, 2, 1)))
        assertEquals(1836, rambunctiousRecitation1(listOf(3, 1, 2)))
    }

    @Test
    fun `example 2`() {
        assertEquals(175594, rambunctiousRecitation2(listOf(0, 3, 6)))
        assertEquals(2578, rambunctiousRecitation2(listOf(1, 3, 2)))
        assertEquals(3544142, rambunctiousRecitation2(listOf(2, 1, 3)))
        assertEquals(261214, rambunctiousRecitation2(listOf(1, 2, 3)))
        assertEquals(6895259, rambunctiousRecitation2(listOf(2, 3, 1)))
        assertEquals(18, rambunctiousRecitation2(listOf(3, 2, 1)))
        assertEquals(362, rambunctiousRecitation2(listOf(3, 1, 2)))
    }

    @Test
    fun `part 1`() {
        assertEquals(959, rambunctiousRecitation1(listOf(18, 11, 9, 0, 5, 1)))
    }

    @Test
    fun `part 2`() {
        assertEquals(116590, rambunctiousRecitation2(listOf(18, 11, 9, 0, 5, 1)))
    }
}
