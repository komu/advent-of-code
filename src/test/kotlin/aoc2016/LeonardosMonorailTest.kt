package komu.adventofcode.aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LeonardosMonorailTest {

    @Test
    fun `part 1`() {
        assertEquals(318007, leonardosMonorail(slow = false))
    }

    @Test
    fun `part 2`() {
        assertEquals(9227661, leonardosMonorail(slow = true))
    }
}
