package komu.adventofcode.aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrabCupsTest {

    @Test
    fun `example 1`() {
        assertEquals("67384529", crabCups1("389125467"))
    }

    @Test
    fun `example 2`() {
        assertEquals(149245887792, crabCups2("389125467"))
    }

    @Test
    fun `part 1`() {
        assertEquals("89372645", crabCups1("614752839"))
    }

    @Test
    fun `part 2`() {
        assertEquals(21273394210, crabCups2("614752839"))
    }
}
