package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RecursiveCircusTest {

    private val exampleInput = """
            pbga (66)
            xhth (57)
            ebii (61)
            havc (66)
            ktlj (57)
            fwft (72) -> ktlj, cntj, xhth
            qoyq (66)
            padx (45) -> pbga, havc, qoyq
            tknk (41) -> ugml, padx, fwft
            jptl (61)
            ugml (68) -> gyxo, ebii, jptl
            gyxo (61)
            cntj (57)
        """.trimIndent()

    @Test
    fun `example data`() {
        assertEquals("tknk", recursiveCircusBottom(exampleInput))
    }

    @Test
    fun `example balance`() {
        assertEquals(60, recursiveCircusBalance(exampleInput))
    }

    @Test
    fun `real data`() {
        assertEquals("bpvhwhh", recursiveCircusBottom(readTestInput("/2017/RecursiveCircus.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(256, recursiveCircusBalance(readTestInput("/2017/RecursiveCircus.txt")))
    }
}
