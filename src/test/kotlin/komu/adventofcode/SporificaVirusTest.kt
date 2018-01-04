package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SporificaVirusTest {

    @Test
    fun `example data`() {
        assertEquals(5587, sporificaVirus1("""
            ..#
            #..
            ...
        """.trimIndent()))
    }

    @Test
    fun `example data 2`() {
        assertEquals(2511944, sporificaVirus2("""
            ..#
            #..
            ...
        """.trimIndent()))
    }

    @Test
    fun `real data`() {
        assertEquals(5570, sporificaVirus1(readTestInput("/input/SporificaVirus.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(2512022, sporificaVirus2(readTestInput("/input/SporificaVirus.txt")))
    }
}
