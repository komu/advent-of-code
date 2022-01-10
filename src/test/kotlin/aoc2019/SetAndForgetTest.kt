package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SetAndForgetTest {

    @Test
    fun `part 1`() {
        assertEquals(3920, setAndForget1(readTestInput("/2019/SetAndForget.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(673996, setAndForget2(readTestInput("/2019/SetAndForget.txt")))
    }

    @Test
    fun `uncompressed solution`() {
        val program = "R,8,L,4,R,4,R,10,R,8,R,8,L,4,R,4,R,10,R,8,L,12,L,12,R,8,R,8,R,10,R,4,R,4,L,12,L,12,R,8," +
                "R,8,R,10,R,4,R,4,L,12,L,12,R,8,R,8,R,10,R,4,R,4,R,10,R,4,R,4,R,8,L,4,R,4,R,10,R,8"

        assertTrue(verifySolution(readTestInput("/2019/SetAndForget.txt"), program))
    }

    @Test
    fun `compressed solution`() {
        val program = "R,8,L,4,R,4,R,10,R,8,R,8,L,4,R,4,R,10,R,8,L,12,L,12,R,8,R,8,R,10,R,4,R,4,L,12,L,12,R,8," +
                "R,8,R,10,R,4,R,4,L,12,L,12,R,8,R,8,R,10,R,4,R,4,R,10,R,4,R,4,R,8,L,4,R,4,R,10,R,8"

        val main = "A,A,B,C,B,C,B,C,C,A"
        val a = "R,8,L,4,R,4,R,10,R,8"
        val b = "L,12,L,12,R,8,R,8"
        val c = "R,10,R,4,R,4"

        assertTrue(isValidCompressedSolution(main, a, b, c, program))
    }
}
