package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PermutationPromenadeTest {

    @Test
    fun `example input`() {
        assertEquals("baedc", dance("s1,x3/4,pe/b", programs = "abcde"))
    }

    @Test
    fun `real input`() {
        assertEquals("iabmedjhclofgknp", dance(readTestInput("/input/PermutationPromenade.txt")))
    }

    @Test
    fun period() {
        assertEquals("oildcmfeajhbpngk", dance(readTestInput("/input/PermutationPromenade.txt"), loops = 1_000_000_000))
    }
}
