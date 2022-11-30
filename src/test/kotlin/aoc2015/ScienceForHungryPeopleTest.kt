package komu.adventofcode.aoc2015

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ScienceForHungryPeopleTest {

    private val testData = listOf(
        Ingredient(capacity = 4, durability = -2, flavor = 0, texture = 0, calories = 5),
        Ingredient(capacity = 0, durability = 5, flavor = -1, texture = 0, calories = 8),
        Ingredient(capacity = -1, durability = 0, flavor = 5, texture = 0, calories = 6),
        Ingredient(capacity = 0, durability = 0, flavor = -2, texture = 2, calories = 1),
    )

    @Test
    fun part1() {
        assertEquals(18965440, scienceForHungryPeople1(testData))
    }

    @Test
    fun part2() {
        assertEquals(15862900, scienceForHungryPeople2(testData))
    }
}
