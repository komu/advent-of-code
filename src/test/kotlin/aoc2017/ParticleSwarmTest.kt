package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticleSwarmTest {

    @Test
    fun `real input - closest`() {
        assertEquals(157, particleSwarmClosest(readTestInput("/2017/ParticleSwarm.txt")))
    }

    @Test
    fun `real input - part 2`() {
        assertEquals(499, particleSwarmSurvivors(readTestInput("/2017/ParticleSwarm.txt")))
    }
}
