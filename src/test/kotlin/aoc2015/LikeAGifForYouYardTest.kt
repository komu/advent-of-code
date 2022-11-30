package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LikeAGifForYouYardTest {

    @Test
    fun part1() {
        assertEquals(768, likeAGifForYouYard1(readTestInput("/2015/LikeAGifForYouYard.txt")))
    }

    @Test
    fun part2() {
        assertEquals(781, likeAGifForYouYard1(readTestInput("/2015/LikeAGifForYouYard.txt"), buggy = true))
    }
}
