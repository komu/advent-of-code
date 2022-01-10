package komu.adventofcode.aoc2020

import komu.adventofcode.utils.Point
import komu.adventofcode.utils.nonEmptyLines

fun lobbyLayout1(input: String) =
    layout(input).size

fun lobbyLayout2(input: String) =
    (1..100).fold(layout(input)) { p, _ -> evolve(p) }.size

private fun layout(input: String): Set<Point> {
    val points = input.nonEmptyLines().map {
        HexMove.parseMoves(it).fold(Point.ORIGIN, Point::plus)
    }

    val blacks = mutableSetOf<Point>()

    for (p in points)
        if (!blacks.add(p))
            blacks.remove(p)

    return blacks
}

private fun evolve(previousBlacks: Set<Point>): Set<Point> {
    val candidatePoints = (previousBlacks + previousBlacks.flatMap { it.hexNeighbors }).toSet()

    val result = mutableSetOf<Point>()

    for (p in candidatePoints) {
        val blackNeighbors = p.hexNeighbors.count { it in previousBlacks }
        if (p in previousBlacks) {
            if (blackNeighbors == 1 || blackNeighbors == 2)
                result += p

        } else {
            if (blackNeighbors == 2)
                result += p
        }
    }

    return result
}

private enum class HexMove(val dx: Int, val dy: Int) {
    E(1, 0),
    NE(1, -1),
    SE(0, 1),
    W(-1, 0),
    SW(-1, 1),
    NW(0, -1);

    companion object {
        fun parseMoves(line: String): List<HexMove> {
            val result = mutableListOf<HexMove>()

            var i = 0
            while (i < line.length) {
                val takenChars = if (line[i] in "ew") 1 else 2
                result += valueOf(line.substring(i, i + takenChars).toUpperCase())
                i += takenChars
            }

            return result
        }
    }
}

private val Point.hexNeighbors: Collection<Point>
    get() = HexMove.values().map { this + it }

private operator fun Point.plus(move: HexMove) =
    Point(x + move.dx, y + move.dy)
