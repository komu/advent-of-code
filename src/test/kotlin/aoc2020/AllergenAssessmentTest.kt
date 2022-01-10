package komu.adventofcode.aoc2020

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AllergenAssessmentTest {

    private val exampleData = """
            mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
            trh fvjkl sbzzf mxmxvkd (contains dairy)
            sqjhc fvjkl (contains soy)
            sqjhc mxmxvkd sbzzf (contains fish)
        """.trimIndent()

    @Test
    fun `example 1`() {
        assertEquals(5, allergenAssessment1(exampleData))
    }

    @Test
    fun `example 2`() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", allergenAssessment2(exampleData))
    }

    @Test
    fun `part 1`() {
        assertEquals(2627, allergenAssessment1(readTestInput("/2020/AllergenAssessment.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals("hn,dgsdtj,kpksf,sjcvsr,bstzgn,kmmqmv,vkdxfj,bsfqgb", allergenAssessment2(readTestInput("/2020/AllergenAssessment.txt")))
    }
}
