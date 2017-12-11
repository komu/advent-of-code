package komu.adventofcode

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HighEntropyPassphrasesTest {

    @Test
    fun `example inputs`() {
        assertTrue(isValidPassphrase("aa bb cc dd ee"))
        assertFalse(isValidPassphrase("aa bb cc dd aa"))
        assertTrue(isValidPassphrase("aa bb cc dd aaa "))
    }

    @Test
    fun `count valid passphrases in input`() {
        assertEquals(337, countValidPassphrases(readTestInputLines("input/HighEntropyPassphrases.txt")))
    }

    @Test
    fun `count valid passphrases in input2`() {
        assertEquals(231, countValidPassphrases2(readTestInputLines("input/HighEntropyPassphrases.txt")))
    }
}