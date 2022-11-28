package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import kotlin.test.Test
import kotlin.test.assertEquals

class ScrambledLettersAndHashTest {

    @Test
    fun operations() {
        assertEquals("ebcda", "abcde".swap(4, 0))

        assertEquals("bcdea", "abcde".rotateLeft(1))
        assertEquals("cdeab", "abcde".rotateLeft(2))

        assertEquals("eabcd", "abcde".rotateRight(1))
        assertEquals("deabc", "abcde".rotateRight(2))

        assertEquals("bdeac", "bcdea".move(from = 1, to = 4))
        assertEquals("abdec", "bdeac".move(from = 3, to = 0))

        assertEquals("ecabd", "abdec".rotateBasedOnLetter('b'))
        assertEquals("decab", "ecabd".rotateBasedOnLetter('d'))

        assertEquals("edcba", "abcde".reverse(0, 4))
        assertEquals("adcbe", "abcde".reverse(1, 3))
    }

    @Test
    fun part1() {
        assertEquals("bfheacgd", scrambledLettersAndHash1(readTestInput("/2016/ScrambledLettersAndHash.txt")))
    }

    @Test
    fun part2() {
        assertEquals("gcehdbfa", scrambledLettersAndHash2(readTestInput("/2016/ScrambledLettersAndHash.txt")))
    }
}
