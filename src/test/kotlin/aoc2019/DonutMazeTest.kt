package aoc2019

import komu.adventofcode.utils.readTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DonutMazeTest {

    @Test
    fun `example 1`() {
        assertEquals(23, donutMaze1(
            """
                         A           
                         A           
                  #######.#########  
                  #######.........#  
                  #######.#######.#  
                  #######.#######.#  
                  #######.#######.#  
                  #####  B    ###.#  
                BC...##  C    ###.#  
                  ##.##       ###.#  
                  ##...DE  F  ###.#  
                  #####    G  ###.#  
                  #########.#####.#  
                DE..#######...###.#  
                  #.#########.###.#  
                FG..#########.....#  
                  ###########.#####  
                             Z       
                             Z       
            """.trimIndent()
        ))
    }

    @Test
    fun `part 1`() {
        assertEquals(410, donutMaze1(readTestInput("/2019/DonutMaze.txt")))
    }

    @Test
    fun `part 2`() {
        assertEquals(5084, donutMaze2(readTestInput("/2019/DonutMaze.txt")))
    }
}
