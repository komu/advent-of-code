package aoc2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SecureContainerTest {

    @Test
    fun `part 1`() {
        assertEquals(1764, secureContainer1(152085, 670283))
    }

    @Test
    fun `part 2`() {
        assertEquals(1196, secureContainer2(152085, 670283))
    }
}
