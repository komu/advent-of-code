package komu.adventofcode.aoc2016

import kotlin.test.Test
import kotlin.test.assertEquals

class TwoStepsForwardTest {

    private val input = "pgflpeqp"

    @Test
    fun examples1() {
        assertEquals("DDRRRD", twoStepsForward1("ihgpwlah"))
        assertEquals("DDUDRLRRUDRD", twoStepsForward1("kglvqrro"))
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", twoStepsForward1("ulqzkmiv"))
    }

    @Test
    fun examples2() {
        assertEquals(370, twoStepsForward2("ihgpwlah"))
        assertEquals(492, twoStepsForward2("kglvqrro"))
        assertEquals(830, twoStepsForward2("ulqzkmiv"))
    }

    @Test
    fun part1() {
        assertEquals("RDRLDRDURD", twoStepsForward1(input))
    }

    @Test
    fun part2() {
        assertEquals(596, twoStepsForward2(input))
    }
}