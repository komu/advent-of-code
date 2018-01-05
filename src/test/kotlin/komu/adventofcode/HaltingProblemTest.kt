package komu.adventofcode

import komu.adventofcode.TuringDirection.LEFT
import komu.adventofcode.TuringDirection.RIGHT
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HaltingProblemTest {

    @Test
    fun `example data`() {
        val machine = TuringMachine(startState = "A") {
            state("A") {
                onZero(write = 1, move = RIGHT, nextState = "B")
                onOne(write = 0, move = LEFT, nextState = "B")
            }
            state("B") {
                onZero(write = 1, move = LEFT, nextState = "A")
                onOne(write = 1, move = RIGHT, nextState = "A")
            }
        }

        machine.run(6)

        assertEquals(3, machine.checksum())
    }

    @Test
    fun `real data`() {
        val machine = TuringMachine(startState = "A") {
            state("A") {
                onZero(write = 1, move = RIGHT, nextState = "B")
                onOne(write = 0, move = RIGHT, nextState = "F")
            }
            state("B") {
                onZero(write = 0, move = LEFT, nextState = "B")
                onOne(write = 1, move = LEFT, nextState = "C")
            }
            state("C") {
                onZero(write = 1, move = LEFT, nextState = "D")
                onOne(write = 0, move = RIGHT, nextState = "C")
            }
            state("D") {
                onZero(write = 1, move = LEFT, nextState = "E")
                onOne(write = 1, move = RIGHT, nextState = "A")
            }
            state("E") {
                onZero(write = 1, move = LEFT, nextState = "F")
                onOne(write = 0, move = LEFT, nextState = "D")
            }
            state("F") {
                onZero(write = 1, move = RIGHT, nextState = "A")
                onOne(write = 0, move = LEFT, nextState = "E")
            }
        }

        machine.run(12425180)

        assertEquals(3099, machine.checksum())
    }
}
