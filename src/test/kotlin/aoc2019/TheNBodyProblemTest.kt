package aoc2019

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TheNBodyProblemTest {

    @Test
    fun `example 1`() {
        assertEquals(179, nBodyProblem(exampleInput1, 10))
    }

    @Test
    fun `example 2`() {
        assertEquals(2772, nBodyProblem2(exampleInput1))
    }

    @Test
    fun `example 3`() {
        assertEquals(4686774924, nBodyProblem2(exampleInput2))
    }

    @Test
    fun `part 1`() {
        assertEquals(12070, nBodyProblem(input, 1000))
    }

    @Test
    fun `part 2`() {
        assertEquals(500903629351944, nBodyProblem2(input))
    }

    private val input = listOf(
        Moon(x = -3, y = 15, z = -11),
        Moon(x = 3, y = 13, z = -19),
        Moon(x = -13, y = 18, z = -2),
        Moon(x = 6, y = 0, z = -1))

    private val exampleInput1 = listOf(
        Moon(x = -1, y = 0, z = 2),
        Moon(x = 2, y = -10, z = -7),
        Moon(x = 4, y = -8, z = 8),
        Moon(x = 3, y = 5, z = -1))

    private val exampleInput2 = listOf(
        Moon(x = -8, y = -10, z = 0),
        Moon(x = 5, y = 5, z = 10),
        Moon(x = 2, y = -7, z = 3),
        Moon(x = 9, y = -8, z = -3))
}
