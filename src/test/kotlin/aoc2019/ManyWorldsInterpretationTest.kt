package komu.adventofcode.aoc2019

import komu.adventofcode.utils.nonEmptyLines
import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ManyWorldsInterpretationTest {

    @Nested
    inner class `part 1` {

        @Test
        fun `example 1`() {
            assertEquals(
                8, manyWorldsInterpretation(
                    """
                    #########
                    #b.A.@.a#
                    #########
                    """.trimIndent()
                )
            )
        }

        @Test
        fun `example 2`() {
            assertEquals(
                86, manyWorldsInterpretation(
                    """
                    ########################
                    #f.D.E.e.C.b.A.@.a.B.c.#
                    ######################.#
                    #d.....................#
                    ########################
                    """.trimIndent()
                )
            )
        }

        @Test
        fun `example 3`() {
            assertEquals(
                132, manyWorldsInterpretation(
                    """
                    ########################
                    #...............b.C.D.f#
                    #.######################
                    #.....@.a.B.c.d.A.e.F.g#
                    ########################
                    """.trimIndent()
                )
            )
        }

        @Test
        fun `example 4`() {
            assertEquals(
                81, manyWorldsInterpretation(
                    """
                    ########################
                    #@..............ac.GI.b#
                    ###d#e#f################
                    ###A#B#C################
                    ###g#h#i################
                    ########################
                    """.trimIndent()
                )
            )
        }

        @Test
        @Disabled("slow - takes about a minute")
        fun `part 1`() {
            assertEquals(4954, manyWorldsInterpretation(readTestInput("/2019/ManyWorldsInterpretation.txt")))
        }
    }

    @Nested
    inner class `part 2` {

        @Test
        fun `example 5`() {
            assertEquals(
                8, manyWorldsInterpretation(
                    """
                                #######
                                #a.#Cd#
                                ##@#@##
                                #######
                                ##@#@##
                                #cB#Ab#
                                #######                        
                                """.trimIndent()
                )
            )
        }

        @Test
        fun `example 6`() {
            assertEquals(
                24, manyWorldsInterpretation(
                    """
                                ###############
                                #d.ABC.#.....a#
                                ######@#@######
                                ###############
                                ######@#@######
                                #b.....#.....c#
                                ###############
                                """.trimIndent()
                )
            )
        }

        @Test
        fun `example 7`() {
            assertEquals(
                32, manyWorldsInterpretation(
                    """
                                #############
                                #DcBa.#.GhKl#
                                #.###@#@#I###
                                #e#d#####j#k#
                                ###C#@#@###J#
                                #fEbA.#.FgHi#
                                #############
                                """.trimIndent()
                )
            )
        }

        @Test
        fun `example 8`() {
            assertEquals(
                72, manyWorldsInterpretation(
                    """
                                #############
                                #g#f.D#..h#l#
                                #F###e#E###.#
                                #dCba@#@BcIJ#
                                #############
                                #nK.L@#@G...#
                                #M###N#H###.#
                                #o#m..#i#jk.#
                                #############
                                """.trimIndent()
                )
            )
        }

        @Test
        @Disabled("slow - takes about 20s")
        fun `part 2`() {
            val input = readTestInput("/2019/ManyWorldsInterpretation.txt").nonEmptyLines().map { StringBuilder(it) }
            val y =  input.indexOfFirst { '@' in it }
            val x = input[y].indexOf('@')

            input[y-1][x-1] = '@'
            input[y-1][x+1] = '@'
            input[y+1][x-1] = '@'
            input[y+1][x+1] = '@'
            input[y-1][x] = '#'
            input[y+1][x] = '#'
            input[y][x] = '#'
            input[y][x-1] = '#'
            input[y][x+1] = '#'

            assertEquals(2334, manyWorldsInterpretation(input.joinToString("\n")))
        }
    }
}
