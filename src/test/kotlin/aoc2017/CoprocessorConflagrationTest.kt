package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CoprocessorConflagrationTest {

    @Test
    fun `real data`() {
        assertEquals(9409, coprocessorConflagrationTest(readTestInput("/2017/CoprocessorConflagration.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(913, coprocessorConflagrationTest2())
    }
}
