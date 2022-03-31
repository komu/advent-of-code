package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GoWithTheFlowTest {

    @Test
    fun `example 1`() {
        assertEquals(7, goWithTheFlow("""
            #ip 0
            seti 5 0 1
            seti 6 0 2
            addi 0 1 0
            addr 1 2 3
            setr 1 0 0
            seti 8 0 4
            seti 9 0 5
        """.trimIndent()))
    }

    @Test
    fun `part 1`() {
        assertEquals(1344, goWithTheFlow(readTestInput("/2018/GoWithTheFlow.txt")))
    }

    @Test
    fun `part 1 - optimized`() {
        assertEquals(1344, goWithTheFlowReverseEngineered(n = 861))
    }

    @Test
    fun `part 2`() {
        assertEquals(16078144, goWithTheFlowReverseEngineered(n = 10551261))
    }
}

