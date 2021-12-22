package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SecurityThroughObscurityTest {

    @Test
    fun `example 1`() {
        assertEquals(1514, securityThroughObscurity(listOf(
            "aaaaa-bbb-z-y-x-123[abxyz]",
            "a-b-c-d-e-f-g-h-987[abcde]",
            "not-a-real-room-404[oarel]",
            "totally-real-room-200[decoy]")))
    }

    @Test
    fun `example 2`() {
        assertEquals("very encrypted name", ObscureRoom("qzmt-zixmtkozy-ivhz", 343, "").decrypt())
    }

    @Test
    fun `part 1`() {
        assertEquals(185371, securityThroughObscurity(readTestInput("/2016/SecurityThroughObscurity.txt").nonEmptyLines()))
    }

    @Test
    fun `part 2`() {
        assertEquals(984, securityThroughObscurity2(readTestInput("/2016/SecurityThroughObscurity.txt").nonEmptyLines()))
    }
}
