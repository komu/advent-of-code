package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PlanetOfDiscordTest {

    @Test
    fun `part 1`() {
        assertEquals(23846449, planetOfDiscord(readTestInput("/2019/PlanetOfDiscord.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(1934, planetOfDiscord2(readTestInput("/2019/PlanetOfDiscord.txt")))
    }
}
