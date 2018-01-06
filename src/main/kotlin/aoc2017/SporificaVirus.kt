package komu.adventofcode.aoc2017

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines

fun sporificaVirus1(input: String): Int {
    val infectedPoints = VirusMap(input.nonEmptyLines())
    var point = Point.ORIGIN
    var dir = Direction.UP
    var infections = 0

    repeat(10000) {
        if (infectedPoints[point] == VirusState.INFECTED) {
            dir = dir.right
            infectedPoints[point] = VirusState.CLEAN
        } else {
            dir = dir.left
            infectedPoints[point] = VirusState.INFECTED
            infections++
        }

        point += dir
    }

    return infections
}

fun sporificaVirus2(input: String): Int {
    val infectedPoints = VirusMap(input.nonEmptyLines())
    var point = Point.ORIGIN
    var dir = Direction.UP
    var infections = 0

    repeat(10000000) {
        val state = infectedPoints[point]

        dir = when (state) {
            VirusState.CLEAN -> dir.left
            VirusState.WEAKENED -> dir
            VirusState.INFECTED -> dir.right
            VirusState.FLAGGED -> dir.opposite
        }

        infectedPoints[point] = state.next

        if (state.next == VirusState.INFECTED)
            infections++

        point += dir
    }

    return infections
}

private enum class VirusState {
    CLEAN, WEAKENED, INFECTED, FLAGGED;

    val next: VirusState
        get() = values()[(ordinal + 1) % 4]
}

private class VirusMap(lines: List<String>) {

    private val infectedPoints = mutableMapOf<Point, VirusState>()

    init {
        val dy = lines.size / 2
        val dx = lines[0].length / 2

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#')
                    infectedPoints[Point(x - dx, y - dy)] = VirusState.INFECTED
            }
        }
    }

    operator fun get(point: Point) = infectedPoints[point] ?: VirusState.CLEAN

    operator fun set(point: Point, state: VirusState) {
        infectedPoints[point] = state
    }
}

