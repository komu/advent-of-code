package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PermutationPromenadeTest {

    @Test
    fun `example input`() {
        assertEquals("baedc", dance("s1,x3/4,pe/b", programs = "abcde"))
    }

    @Test
    fun `real input`() {
        assertEquals("iabmedjhclofgknp", dance(readTestInput("/2017/PermutationPromenade.txt")))
    }

    @Test
    fun period() {
        assertEquals("oildcmfeajhbpngk", dance(readTestInput("/2017/PermutationPromenade.txt"), loops = 1_000_000_000))
    }
}
