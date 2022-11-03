package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SignalsAndNoiseTest {

    @Test
    fun part1() {
        assertEquals("xdkzukcf", signalsAndNoise1(readTestInput("/2016/SignalsAndNoise.txt")))
    }

    @Test
    fun part2() {
        assertEquals("cevsgyvd", signalsAndNoise2(readTestInput("/2016/SignalsAndNoise.txt")))
    }
}