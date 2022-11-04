package komu.adventofcode.aoc2016

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TwoFactorAuthenticationTest {

    @Test
    fun part1() {
        assertEquals(119, twoFactorAuthentication1(readTestInput("/2016/TwoFactorAuthentication.txt")))
    }

    @Test
    fun part2() {
        twoFactorAuthentication2(readTestInput("/2016/TwoFactorAuthentication.txt"))
    }
}