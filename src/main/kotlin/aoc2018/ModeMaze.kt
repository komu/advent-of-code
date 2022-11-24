package komu.adventofcode.aoc2018

import komu.adventofcode.utils.Point
import utils.shortestPathWithCost
import java.util.*

fun modeMaze1(depth: Int, target: Point) =
    CaveSystem(depth, target).riskLevel()

fun modeMaze2(depth: Int, target: Point) =
    CaveSystem(depth, target).fastestRoute()

private class CaveSystem(private val depth: Int, private val target: Point) {

    private val geologicIndexCache = mutableMapOf<Point, Int>()

    private fun geologicIndex(p: Point): Int = geologicIndexCache.getOrPut(p) {
        when {
            p == Point.ORIGIN -> 0
            p == target -> 0
            p.y == 0 -> p.x * 16807
            p.x == 0 -> p.y * 48271
            else -> erosionLevel(Point(p.x - 1, p.y)) * erosionLevel(Point(p.x, p.y - 1))
        }
    }

    private fun erosionLevel(p: Point): Int =
        (geologicIndex(p) + depth) % 20183

    private fun regionType(p: Point) =
        RegionType.forErosionLevel(erosionLevel(p))

    fun riskLevel(): Int {
        var level = 0

        for (y in 0..target.y)
            for (x in 0..target.x)
                level += regionType(Point(x, y)).riskLevel

        return level
    }

    fun fastestRoute(): Int {
        val initial = MazeState(Point.ORIGIN, Tool.TORCH)
        val goal = MazeState(target, Tool.TORCH)

        return shortestPathWithCost(initial, { it == goal }, this::transitions)!!.second
    }

    private fun isCandidate(p: Point) =
        p.x in 0..target.x + 40 && p.y in 0..target.y + 40

    private fun transitions(state: MazeState): List<Pair<MazeState, Int>> {
        val result = mutableListOf<Pair<MazeState, Int>>()

        for (n in state.position.neighbors)
            if (isCandidate(n) && state.tool in regionType(n).allowedTools)
                result += Pair(MazeState(n, state.tool), 1)

        for (tool in regionType(state.position).allowedTools)
            if (tool != state.tool)
                result += Pair(MazeState(state.position, tool), 7)

        return result
    }
}

private data class MazeState(val position: Point, val tool: Tool)

private enum class Tool {
    TORCH, CLIMBING_GEAR, NEITHER
}

private enum class RegionType(val riskLevel: Int, val allowedTools: EnumSet<Tool>) {
    ROCKY(riskLevel = 0, EnumSet.of(Tool.TORCH, Tool.CLIMBING_GEAR)),
    WET(riskLevel = 1, EnumSet.of(Tool.CLIMBING_GEAR, Tool.NEITHER)),
    NARROW(riskLevel = 2, EnumSet.of(Tool.TORCH, Tool.NEITHER));

    companion object {
        fun forErosionLevel(level: Int) = when (level % 3) {
            0 -> ROCKY
            1 -> WET
            2 -> NARROW
            else -> error("invalid level: $level")
        }
    }
}
