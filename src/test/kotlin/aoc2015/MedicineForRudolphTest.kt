package komu.adventofcode.aoc2015

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MedicineForRudolphTest {

    @Test
    fun example1() {
        assertEquals(4, medicineForRudolph1("""
            H => HO
            H => OH
            O => HH

            HOH
        """.trimIndent()))
    }

    @Test
    fun example2() {
        assertEquals(3, medicineForRudolph2("""
            e => H
            e => O
            H => HO
            H => OH
            O => HH
            
            HOH
        """.trimIndent()))
    }

    @Test
    fun part1() {
        assertEquals(535, medicineForRudolph1(readTestInput("/2015/MedicineForRudolph.txt")))
    }

    @Test
    fun part2() {
        assertEquals(212, medicineForRudolph2(readTestInput("/2015/MedicineForRudolph.txt")))
    }
}
