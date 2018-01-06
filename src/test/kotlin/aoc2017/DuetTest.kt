package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DuetTest {

    @Test
    fun `example data`() {
        assertEquals(4, duet("""
            set a 1
            add a 2
            mul a a
            mod a 5
            snd a
            set a 0
            rcv a
            jgz a -1
            set a 1
            jgz a -2
        """.trimIndent()))
    }

    @Test
    fun `real data`() {
        assertEquals(1187, duet(readTestInput("/2017/Duet.txt")))
    }

    @Test
    fun `test data 2`() {
        assertEquals(3, duet2("""
            snd 1
            snd 2
            snd p
            rcv a
            rcv b
            rcv c
            rcv d
        """.trimIndent()))
    }

    @Test
    fun `real data 2`() {
        assertEquals(5969, duet2(readTestInput("/2017/Duet.txt")))
    }
}
