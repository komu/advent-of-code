package komu.adventofcode.aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SeatingSystemTest {

    private val exampleData = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent()

    @Test
    fun `example 1`() {
        assertEquals(37, seatingSystem1(exampleData))
    }

    @Test
    fun `example 2`() {
        assertEquals(26, seatingSystem2(exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(2483, seatingSystem1(readTestInput("/2020/SeatingSystem.txt").trim()))
    }

    @Test
    fun `part 2`() {
        assertEquals(2285, seatingSystem2(readTestInput("/2020/SeatingSystem.txt").trim()))
    }
}
