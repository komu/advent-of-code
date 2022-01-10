package komu.adventofcode.aoc2020

import komu.adventofcode.utils.Dir
import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Point

fun rainRisk1(input: List<String>): Int {
    val instructions = input.map { ShipInstruction.parse(it) }

    var direction = Direction.RIGHT
    var ship = Point.ORIGIN
    for (inst in instructions) {
        when (inst.op) {
            'N' ->
                ship = ship.towards(Direction.UP, inst.value)
            'E' ->
                ship = ship.towards(Direction.RIGHT, inst.value)
            'S' ->
                ship = ship.towards(Direction.DOWN, inst.value)
            'W' ->
                ship = ship.towards(Direction.LEFT, inst.value)
            'F' ->
                ship = ship.towards(direction, inst.value)
            'L' ->
                repeat(inst.value / 90) {
                    direction = direction.left
                }
            'R' ->
                repeat(inst.value / 90) {
                    direction = direction.right
                }
        }
    }

    return ship.manhattanDistanceFromOrigin
}

fun rainRisk2(input: List<String>): Int {
    val instructions = input.map { ShipInstruction.parse(it) }

    var waypoint = Point(10, 1)
    var ship = Point.ORIGIN
    for (inst in instructions) {
        when (inst.op) {
            'N' ->
                waypoint = waypoint.towards(Dir.NORTH, inst.value)
            'E' ->
                waypoint = waypoint.towards(Dir.EAST, inst.value)
            'S' ->
                waypoint = waypoint.towards(Dir.SOUTH, inst.value)
            'W' ->
                waypoint = waypoint.towards(Dir.WEST, inst.value)
            'F' ->
                ship = Point(ship.x + inst.value * waypoint.x, ship.y + inst.value * waypoint.y)
            'L' ->
                repeat(inst.value / 90) {
                    waypoint = waypoint.rotateCounterClockwise()
                }
            'R' ->
                repeat(inst.value / 90) {
                    waypoint = waypoint.rotateClockwise()
                }
        }
    }
    return ship.manhattanDistanceFromOrigin
}

private data class ShipInstruction(val op: Char, val value: Int) {

    companion object {

        private val regex = Regex("""(.)(\d+)""")

        fun parse(line: String): ShipInstruction {
            val (_, op, value) = regex.matchEntire(line)?.groupValues ?: error("invalid line '$line'")

            return ShipInstruction(op[0], value.toInt())
        }
    }
}

