package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SafeCrackingTest {

    @Test
    fun part1() {
        assertEquals(11200, safeCracking(readTestInput("/2016/SafeCracking.txt"), eggs = 7))
    }

    @Test
    fun part2() {
        assertEquals(479007760, safeCracking(readTestInput("/2016/SafeCracking.txt"), eggs = 12))
    }
}
