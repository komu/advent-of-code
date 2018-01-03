package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ParticleSwarmTest {

    @Test
    fun `real input - closest`() {
        assertEquals(157, particleSwarmClosest(readTestInput("/input/ParticleSwarm.txt")))
    }

    @Test
    fun `real input - part 2`() {
        assertEquals(499, particleSwarmSurvivors(readTestInput("/input/ParticleSwarm.txt")))
    }
}
