package komu.adventofcode.aoc2017

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StreamProcessingTest {

    @Test
    fun `example data`() {
        assertEquals(1, streamScore("{}"))
        assertEquals(6, streamScore("{{{}}}"))
        assertEquals(5, streamScore("{{},{}}"))
        assertEquals(16, streamScore("{{{},{},{{}}}}"))
        assertEquals(1, streamScore("{<a>,<a>,<a>,<a>}"))
        assertEquals(9, streamScore("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
        assertEquals(9, streamScore("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
        assertEquals(3, streamScore("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
    }

    @Test
    fun `real data`() {
        assertEquals(17537, streamScore(readTestInput("/2017/StreamProcessing.txt")))
    }

    @Test
    fun `real data 2`() {
        assertEquals(7539, streamGarbage(readTestInput("/2017/StreamProcessing.txt")))
    }
}
