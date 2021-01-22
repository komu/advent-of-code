package aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BathroomSecurityTest {

    @Test
    fun `example 1`() {
        assertEquals("1985", bathroomSecurity1("""
            ULL
            RRDDD
            LURDL
            UUUUD
        """.trimIndent()))
    }

    @Test
    fun `example 2`() {
        assertEquals("5DB3", bathroomSecurity2("""
            ULL
            RRDDD
            LURDL
            UUUUD
        """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals("18843", bathroomSecurity1(readTestInput("/2016/BathroomSecurity.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals("67BB9", bathroomSecurity2(readTestInput("/2016/BathroomSecurity.txt")))
    }
}
