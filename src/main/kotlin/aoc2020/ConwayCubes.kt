package aoc2020

import komu.adventofcode.utils.nonEmptyLines

fun conwayCubes(input: String, fourDee: Boolean): Int {
    var grid = ConwayGrid.parse(input)

    repeat(6) {
        grid = grid.step(fourDee)
    }

    return grid.activeCells.size
}

private data class ConwayPoint(val x: Int, val y: Int, val z: Int, val w: Int) {
    operator fun plus(p: ConwayPoint) = ConwayPoint(x + p.x, y + p.y, z + p.z, w + p.w)

    fun neighbors(fourDee: Boolean) =
        neighborDeltas(fourDee).map { this + it }

    companion object {
        private fun neighborDeltas(fourDee: Boolean) =
            sequence {
                val ws = if (fourDee) -1..1 else 0..0
                for (dx in -1..1)
                    for (dy in -1..1)
                        for (dz in -1..1)
                            for (dw in ws)
                                if (dx != 0 || dy != 0 || dz != 0 || dw != 0)
                                    yield(ConwayPoint(dx, dy, dz, dw))
            }.toList()
    }
}

private class ConwayGrid(val activeCells: Set<ConwayPoint>) {

    fun step(fourDee: Boolean) =
        ConwayGrid(nextGenerationCoordinateCandidates().filter { p ->
            val activeNeighbors = p.neighbors(fourDee).count { it in activeCells }
            if (p in activeCells)
                activeNeighbors == 2 || activeNeighbors == 3
            else
                activeNeighbors == 3
        }.toSet())

    private fun nextGenerationCoordinateCandidates() = sequence {
        val xs = activeCells.map { it.x }.enlargedRange
        val ys = activeCells.map { it.y }.enlargedRange
        val zs = activeCells.map { it.z }.enlargedRange
        val ws = activeCells.map { it.w }.enlargedRange

        for (x in xs)
            for (y in ys)
                for (z in zs)
                    for (w in ws)
                        yield(ConwayPoint(x, y, z, w))
    }

    companion object {

        fun parse(data: String): ConwayGrid {
            val grid = data.nonEmptyLines()
            val activeCells = mutableSetOf<ConwayPoint>()

            for ((y, line) in grid.withIndex())
                for ((x, c) in line.withIndex())
                    if (c == '#')
                        activeCells += ConwayPoint(x, y, 0, 0)

            return ConwayGrid(activeCells)
        }

        private val List<Int>.enlargedRange: IntRange
            get() = (minOrNull()!! - 1)..(maxOrNull()!! + 1)
    }
}
