package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RPGSimulatorTest {

    private val boss = RPGCharacter(hitPoints = 109, damage = 8, armor = 2)

    @Test
    fun part1() {
        assertEquals(111, rpgSimulator1(boss))
    }

    @Test
    fun part2() {
        assertEquals(188, rpgSimulator2(boss))
    }
}
