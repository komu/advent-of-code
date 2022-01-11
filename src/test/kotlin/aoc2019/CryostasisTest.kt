package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CryostasisTest {

    @Test
    fun `part 1`() {
        assertEquals(1073874948, cryostasis(readTestInput("/2019/Cryostasis.txt")))
    }
}
