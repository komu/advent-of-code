package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class InverseCaptchaTest {

    @Test
    fun `calculate example inputs`() {
        assertEquals(3, inverseCaptcha("1122"))
        assertEquals(4, inverseCaptcha("1111"))
        assertEquals(0, inverseCaptcha("1234"))
        assertEquals(9, inverseCaptcha("91212129"))
    }
}