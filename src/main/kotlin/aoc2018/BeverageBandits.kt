package komu.adventofcode.aoc2018

import komu.adventofcode.aoc2018.Creature.Companion.hitPointComparator
import komu.adventofcode.aoc2018.Creature.Companion.positionComparator
import komu.adventofcode.aoc2018.CreatureType.ELF
import komu.adventofcode.aoc2018.CreatureType.GOBLIN
import komu.adventofcode.utils.Point
import komu.adventofcode.utils.Point.Companion.readingOrderComparator
import komu.adventofcode.utils.nonEmptyLines
import utils.shortestPathBetween

fun beverageBandits(input: String): Int {
    val world = BanditWorld(input.nonEmptyLines())

    var rounds = 0
    while (true) {
        if (!world.tick())
            break

        rounds++
    }

    return rounds * world.totalHitPoints
}

fun beverageBandits2(input: String): Int {

    fun checkPower(power: Int): Int? {
        val world = BanditWorld(input.nonEmptyLines(), elfPower = power)
        val originalElves = world.elves.size

        var rounds = 0
        while (world.elves.size == originalElves) {
            if (!world.tick())
                break

            rounds++
        }

        return if (world.elves.size == originalElves)
            rounds * world.totalHitPoints
        else
            null
    }

    var min = 4
    var max = 200
    var bestResult = 0

    // binary search for best power
    while (min < max) {
        val power = min + (max - min) / 2
        val result = checkPower(power)
        if (result != null) {
            max = power
            bestResult = result
        } else {
            min = power + 1
        }
    }

    return bestResult
}

private data class Creature(val type: CreatureType, var position: Point, val attackPower: Int = 3) {

    var hitPoints = 200

    val isAlive: Boolean
        get() = hitPoints > 0

    companion object {
        val positionComparator = compareBy<Creature, Point>(readingOrderComparator) { it.position }
        val hitPointComparator = compareBy<Creature> { it.hitPoints }.then(positionComparator)
    }
}

private enum class CreatureType {
    GOBLIN, ELF
}

private class BanditWorld(
    val spaces: Set<Point>,
    val goblins: MutableList<Creature>,
    val elves: MutableList<Creature>
) {

    val totalHitPoints: Int
        get() = goblins.sumOf { it.hitPoints } + elves.sumOf { it.hitPoints }

    private val occupiedPoints = (goblins + elves).mapTo(mutableSetOf()) { it.position }

    fun tick(): Boolean {
        val sortedUnits = (goblins + elves).sortedWith(positionComparator)
        for (unit in sortedUnits) {
            if (!unit.isAlive) continue

            val targets = when (unit.type) {
                ELF -> goblins
                GOBLIN -> elves
            }

            if (targets.isEmpty())
                return false

            val squaresInEnemyRange = targets.flatMap { it.position.neighbors }
            if (unit.position !in squaresInEnemyRange)
                if (!move(unit, squaresInEnemyRange))
                    continue

            val target = targets.filter { it.position.isAdjacentTo(unit.position) }.minWithOrNull(hitPointComparator)
            if (target != null) {
                target.hitPoints -= unit.attackPower
                if (!target.isAlive) {
                    targets -= target
                    occupiedPoints -= target.position
                }
            }
        }
        return true
    }

    private fun move(unit: Creature, squaresInEnemyRange: List<Point>): Boolean {
        val target = squaresInEnemyRange
            .filter { canMoveInto(it) }
            .mapNotNull { shortestPath(it, unit.position) }
            .minOrNull()
            ?.start
            ?: return false

        val pathStart = unit.position.neighbors
            .mapNotNull { shortestPath(it, target) }
            .minOrNull()
            ?.start!!

        occupiedPoints -= unit.position
        unit.position = pathStart
        occupiedPoints += unit.position
        return true
    }

    private fun shortestPath(start: Point, target: Point) =
        if (start == target)
            PathInfo(start, 0)
        else
            shortestPathBetween(target, start) { p -> p.neighbors.filter { canMoveInto(it) } }?.let { PathInfo(start, it.size) }

    private fun canMoveInto(p: Point) =
        p in spaces && p !in occupiedPoints

    private class PathInfo(val start: Point, val length: Int) : Comparable<PathInfo> {
        override fun compareTo(other: PathInfo): Int {
            val lengthResult = length.compareTo(other.length)
            return if (lengthResult != 0) lengthResult else readingOrderComparator.compare(start, other.start)
        }
    }

    companion object {
        operator fun invoke(lines: List<String>, elfPower: Int = 3): BanditWorld {
            val spaces = mutableSetOf<Point>()
            val goblins = mutableListOf<Creature>()
            val elves = mutableListOf<Creature>()

            for ((y, line) in lines.withIndex()) {
                for ((x, c) in line.withIndex()) {
                    val point = Point(x, y)
                    when (c) {
                        '.' -> spaces += point
                        'E' -> {
                            spaces += point
                            elves += Creature(ELF, point, attackPower = elfPower)
                        }
                        'G' -> {
                            spaces += point
                            goblins += Creature(GOBLIN, point)
                        }
                        '#' -> {
                            // wall
                        }
                        else ->
                            error("unexpected '$c' at line $y column $x")
                    }
                }
            }

            return BanditWorld(
                spaces = spaces,
                goblins = goblins,
                elves = elves
            )
        }
    }
}
