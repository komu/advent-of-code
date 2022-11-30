package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AntSueTest {

    @Test
    fun `part 1`() {
        assertEquals(213, antSue1(readTestInput("/2015/AntSue.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(323, antSue2(readTestInput("/2015/AntSue.txt")))
    }
}
