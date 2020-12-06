package aoc2020

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordPhilosophyTest {

    @Test
    fun example() {
        assertEquals(2, passwordPhilosophy(listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")))
    }

    @Test
    fun `part 1`() {
        assertEquals(422, passwordPhilosophy(readTestInput("/2020/PasswordPhilosophy.txt").nonEmptyLines()))
    }

    @Test
    fun example2() {
        assertEquals(1, passwordPhilosophy2(listOf("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")))
    }

    @Test
    fun `part 2`() {
        assertEquals(451, passwordPhilosophy2(readTestInput("/2020/PasswordPhilosophy.txt").nonEmptyLines()))
    }
}
