package komu.adventofcode.aoc2016

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point
import kotlin.math.absoluteValue

fun taxicab(s: String): Int {
    val directions = s.trim().split(", ").map { TaxicabStep.parse(it) }
    var point = Point.ORIGIN
    var dir = Direction.UP

    for ((turn, steps) in directions) {
        dir = turn(dir, turn)
        point = point.towards(dir, steps)
    }

    return (point.x + point.y).absoluteValue
}

fun taxicab2(s: String): Int {
    val directions = s.trim().split(", ").map { TaxicabStep.parse(it) }
    var point = Point.ORIGIN
    var dir = Direction.UP
    val visited = mutableSetOf<Point>()

    for ((turn, steps) in directions) {
        dir = turn(dir, turn)

        repeat(steps) {
            point += dir

            if (!visited.add(point))
                return (point.x + point.y).absoluteValue
        }
    }

    error("nothing visited twice")
}

private fun turn(dir: Direction, turn: String) = when (turn) {
    "L" -> dir.left
    "R" -> dir.right
    else -> error("invalid turn '$turn'")
}

private data class TaxicabStep(val turn: String, val steps: Int) {
    companion object {

        private val regex = Regex("""(.)(\d+)""")

        fun parse(s: String): TaxicabStep {
            val m = regex.matchEntire(s) ?: error("invalid input '$s'")
            return TaxicabStep(m.groupValues[1], m.groupValues[2].toInt())
        }
    }
}
