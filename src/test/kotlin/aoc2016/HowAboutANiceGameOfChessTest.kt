package komu.adventofcode.aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HowAboutANiceGameOfChessTest {

    @Test
    fun part1() {
        assertEquals("f97c354d", howAboutANiceGameOfChess1("reyedfim"))
    }

    @Test
    fun part2() {
        assertEquals("863dde27", howAboutANiceGameOfChess2("reyedfim"))
    }
}