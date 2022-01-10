package komu.adventofcode.aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FlawedFrequencyTransmissionTest {

    @Test
    fun `examples 1`() {
        assertEquals("24176176", flawedFrequencyTransmission1("80871224585914546619083218645595"))
        assertEquals("73745418", flawedFrequencyTransmission1("19617804207202209144916044189917"))
        assertEquals("52432133", flawedFrequencyTransmission1("69317163492948606335995924319873"))
    }

    @Test
    fun `part 1`() {
        assertEquals("42205986", flawedFrequencyTransmission1(readTestInput("/2019/FlawedFrequencyTransmission.txt").trim()))
    }

    @Test
    fun `examples 2`() {
        assertEquals("84462026", flawedFrequencyTransmission2("03036732577212944063491565474664"))
        assertEquals("78725270", flawedFrequencyTransmission2("02935109699940807407585447034323"))
        assertEquals("53553731", flawedFrequencyTransmission2("03081770884921959731165446850517"))
    }

    @Test
    fun `part 2`() {
        assertEquals("13270205", flawedFrequencyTransmission2(readTestInput("/2019/FlawedFrequencyTransmission.txt").trim()))
    }
}
