package komu.adventofcode.aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ShuttleSearchTest {

    @Test
    fun `example 1`() {
        assertEquals(295, shuttleService1(939, "7,13,x,x,59,x,31,19"))
    }

    @Test
    fun `part 1`() {
        assertEquals(3385, shuttleService1(1002632, "23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,829,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,x,x,x,x,x,x,x,x,x,x,29,x,677,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,19"))
    }

    @Test
    fun `example 2`() {
        assertEquals(3417, shuttleService2("17,x,13,19"))
        assertEquals(754018, shuttleService2("67,7,59,61"))
        assertEquals(779210, shuttleService2("67,x,7,59,61"))
        assertEquals(1261476, shuttleService2("67,7,x,59,61"))
        assertEquals(1068781, shuttleService2("7,13,x,x,59,x,31,19"))
        assertEquals(1202161486, shuttleService2("1789,37,47,1889"))
    }

    @Test
    fun `part 2`() {
        assertEquals(600689120448303, shuttleService2("23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,829,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,x,x,x,x,x,x,x,x,x,x,29,x,677,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,19"))
    }
}
