package komu.adventofcode.aoc2018

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ImmuneSystemSimulatorTest {

    @Test
    fun `example 1`() {
        assertEquals(
            5216,
            immuneSystemSimulator1(
                """
                Immune System:
                17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
                989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
                
                Infection:
                801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
                4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4
                """.trimIndent()
            )
        )
    }

    @Test
    fun `part 1`() {
        assertEquals(15165, immuneSystemSimulator1(readTestInput("/2018/ImmuneSystemSimulator.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(4037, immuneSystemSimulator2(readTestInput("/2018/ImmuneSystemSimulator.txt")))
    }
}
