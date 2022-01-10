package komu.adventofcode.aoc2019

import komu.adventofcode.utils.nonEmptyLines
import kotlin.math.pow

fun planetOfDiscord(input: String): Int {
    var grid = BugGrid(input)
    val seen = mutableSetOf<BugGrid>()

    while (true) {
        grid = grid.step()
        if (!seen.add(grid))
            break
    }

    return grid.biodiversityRating()
}

fun planetOfDiscord2(input: String): Int {
    var grid = BugGrid(input)

    repeat(200) {
        grid = grid.recursiveStep()
    }

    return grid.bugs.size
}

private data class BugCell(val x: Int, val y: Int, val level: Int) {

    val simpleNeighbors: List<BugCell>
        get() = listOf(
            BugCell(x - 1, y, level),
            BugCell(x + 1, y, level),
            BugCell(x, y - 1, level),
            BugCell(x, y + 1, level)
        )

    val recursiveNeighbors: List<BugCell>
        get() {
            val result = mutableListOf<BugCell>()

            when {
                x == 2 && y == 1 -> {
                    result += copy(x = 1)
                    result += copy(x = 3)
                    result += copy(y = 0)
                    result += (0..4).map { BugCell(x = it, y = 0, level = level + 1) }
                }
                x == 1 && y == 2 -> {
                    result += copy(x = 0)
                    result += copy(y = 1)
                    result += copy(y = 3)
                    result += (0..4).map { BugCell(x = 0, y = it, level = level + 1) }
                }
                x == 3 && y == 2 -> {
                    result += copy(x = 4)
                    result += copy(y = 1)
                    result += copy(y = 3)
                    result += (0..4).map { BugCell(x = 4, y = it, level = level + 1) }
                }
                x == 2 && y == 3 -> {
                    result += copy(x = 1)
                    result += copy(x = 3)
                    result += copy(y = 4)
                    result += (0..4).map { BugCell(x = it, y = 4, level = level + 1) }
                }
                else -> {
                    result += if (x == 0) BugCell(x = 1, y = 2, level = level - 1) else copy(x = x - 1)
                    result += if (x == 4) BugCell(x = 3, y = 2, level = level - 1) else copy(x = x + 1)
                    result += if (y == 0) BugCell(x = 2, y = 1, level = level - 1) else copy(y = y - 1)
                    result += if (y == 4) BugCell(x = 2, y = 3, level = level - 1) else copy(y = y + 1)
                }
            }

            return result
        }
}

private data class BugGrid(val bugs: Set<BugCell>) {

    fun biodiversityRating(): Int =
        (0..24).sumOf { i ->
            if (BugCell(x = i % 5, y = i / 5, level = 0) in bugs)
                2.0.pow(i).toInt()
            else
                0
        }

    fun step(): BugGrid {
        val newPoints = mutableSetOf<BugCell>()

        for (y in 0..4)
            for (x in 0..4) {
                val p = BugCell(x, y, level = 0)

                val neighbors = p.simpleNeighbors.count { it in bugs }

                if (p in bugs) {
                    if (neighbors == 1)
                        newPoints += p
                } else {
                    if (neighbors in 1..2)
                        newPoints += p
                }
            }

        return BugGrid(newPoints)
    }

    fun recursiveStep(): BugGrid {
        val newPoints = mutableSetOf<BugCell>()

        val candidates = (bugs + bugs.flatMap { it.recursiveNeighbors }).toSet()
        for (point in candidates) {
            val neighbors = point.recursiveNeighbors.count { it in bugs }

            if (point in bugs) {
                if (neighbors == 1)
                    newPoints += point
            } else {
                if (neighbors in 1..2)
                    newPoints += point
            }
        }

        return BugGrid(newPoints)
    }

    companion object {
        operator fun invoke(input: String): BugGrid {
            val bugs = mutableSetOf<BugCell>()

            for ((y, line) in input.nonEmptyLines().withIndex())
                for ((x, c) in line.withIndex())
                    if (c == '#')
                        bugs += BugCell(x, y, level = 0)

            return BugGrid(bugs)
        }
    }
}
