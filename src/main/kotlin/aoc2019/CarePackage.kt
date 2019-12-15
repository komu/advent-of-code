package aoc2019

import komu.adventofcode.utils.Point
import java.math.BigInteger.ZERO

private const val TILE_PADDLE = 3
private const val TILE_BALL = 4

fun carePackage1(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.run()

    val tiles = mutableMapOf<Point, Int>()

    val output = machine.outputToList()
    for ((x, y, tile) in output.chunked(3))
        tiles[Point(x.toInt(), y.toInt())] = tile.toInt()

    return tiles.values.count { it == 2 }
}

fun carePackage2(input: String): Int {
    val machine = IntCodeMachine(input).apply {
        memory[0] = 2
    }

    var ballX = -1
    var paddleX = -1

    machine.readInput = {
        val expectedBallX = machine.calculateExpectedPosition() ?: ballX
        when {
            expectedBallX < paddleX ->
                -1
            expectedBallX > paddleX ->
                1
            else ->
                0
        }.toBigInteger()
    }

    var score = 0
    val outputBuffer = mutableListOf<Int>()
    machine.writeOutput = { output ->
        outputBuffer += output.toInt()

        if (outputBuffer.size == 3) {
            val (x, y, param) = outputBuffer
            outputBuffer.clear()

            when {
                x == -1 && y == 0 ->
                    score = param
                param == TILE_PADDLE ->
                    paddleX = x
                param == TILE_BALL ->
                    ballX = x
            }
        }
    }

    machine.run()

    return score
}

/**
 * Calculate where the ball will go by cloning the machine and evaluating
 * it until the ball ends up in row 18.
 */
private fun IntCodeMachine.calculateExpectedPosition(): Int? {
    val clone = this.clone()

    var seenInputAfterBall = false
    clone.readInput = {
        seenInputAfterBall = true
        ZERO
    }

    var result: Int? = null
    while (clone.running && !seenInputAfterBall) {
        clone.tick()

        if (clone.outputSize == 3) {
            val x = clone.readNext().toInt()
            val y = clone.readNext().toInt()
            val param = clone.readNext().toInt()

            if (x != -1 && y == 18 && param == TILE_BALL) {
                seenInputAfterBall = false
                result = x
            }
        }
    }

    return result
}
