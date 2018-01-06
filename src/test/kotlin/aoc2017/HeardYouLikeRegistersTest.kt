package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HeardYouLikeRegistersTest {

    private val example = """
        b inc 5 if a > 1
        a inc 1 if b < 5
        c dec -10 if a >= 1
        c inc -20 if c == 10
    """.trimIndent()

    @Test
    fun `example input`() {
        assertEquals(1, heardYouLikeRegisters(example).first)
    }

    @Test
    fun `real input`() {
        assertEquals(4647, heardYouLikeRegisters(readTestInput("/2017/HeardYouLikeRegisters.txt")).first)
    }

    @Test
    fun `example input 2`() {
        assertEquals(10, heardYouLikeRegisters(example).second)
    }

    @Test
    fun `real input 2`() {
        assertEquals(5590, heardYouLikeRegisters(readTestInput("/2017/HeardYouLikeRegisters.txt")).second)
    }
}
