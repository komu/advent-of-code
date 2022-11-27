package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class LikeARogueTest {

    @Test
    fun example1() {
        assertEquals(38, likeARogue(".^^.^.^^^^", 10))
    }

    @Test
    fun part1() {
        assertEquals(1956, likeARogue(readTestInput("/2016/LikeARogue.txt"), 40))
    }

    @Test
    fun part2() {
        assertEquals(19995121, likeARogue(readTestInput("/2016/LikeARogue.txt"), 400_000))
    }
}