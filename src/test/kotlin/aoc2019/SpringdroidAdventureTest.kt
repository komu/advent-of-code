package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SpringdroidAdventureTest {

    @Test
    fun `part 1`() {
        assertEquals(19352864, springdroidAdventure(readTestInput("/2019/SpringdroidAdventure.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(1142488337, springdroidAdventure2(readTestInput("/2019/SpringdroidAdventure.txt")))
    }
}
