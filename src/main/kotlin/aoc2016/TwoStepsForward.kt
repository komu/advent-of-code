package komu.adventofcode.aoc2016

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.hexEncodedMd5Hash
import utils.shortestPath

fun twoStepsForward1(passcode: String): String =
    shortestPath(State.initial, State::isGoal) { it.transitions(passcode) }!!.last().path

fun twoStepsForward2(passcode: String): Int {
    fun recurse(state: State): Int =
        if (state.isGoal)
            state.path.length
        else
            state.transitions(passcode).maxOfOrNull { recurse(it) } ?: -1

    return recurse(State.initial)
}

private class Room private constructor(val x: Int, val y: Int) {

    val transitions by lazy {
        val transitions = mutableListOf<Pair<Direction, Room>>()

        if (this != goal) {
            for (d in Direction.values()) {
                val xx = x + d.dx
                val yy = y + d.dy
                if (xx in 0..3 && yy in 0..3)
                    transitions.add(d to Room[xx, yy])
            }
        }
        transitions
    }

    companion object {
        private val allRooms = (0..3).map { x -> (0..3).map { y -> Room(x, y) } }

        operator fun get(x: Int, y: Int) = allRooms[x][y]
        val start = this[0, 0]
        val goal = this[3, 3]
    }
}

private data class State(val room: Room, val path: String) {

    val isGoal: Boolean
        get() = room == Room.goal

    fun transitions(passcode: String): List<State> = buildList {
        val openDoors = OpenDoors.forPath(passcode, path)

        for ((direction, targetRoom) in room.transitions)
            if (openDoors.isOpen(direction))
                add(State(targetRoom, path + direction.pathCode))
    }

    companion object {
        val initial = State(Room.start, "")
    }
}

private val Direction.pathCode: Char
    get() = when (this) {
        Direction.UP -> 'U'
        Direction.DOWN -> 'D'
        Direction.LEFT -> 'L'
        Direction.RIGHT -> 'R'
    }

private data class OpenDoors(val up: Boolean, val down: Boolean, val left: Boolean, val right: Boolean) {

    fun isOpen(d: Direction) = when (d) {
        Direction.UP -> up
        Direction.DOWN -> down
        Direction.LEFT -> left
        Direction.RIGHT -> right
    }

    companion object {

        fun forPath(passcode: String, path: String): OpenDoors {
            val (up, down, left, right) = hexEncodedMd5Hash(passcode + path).toList()

            return OpenDoors(
                up = up.isOpen(),
                down = down.isOpen(),
                left = left.isOpen(),
                right = right.isOpen(),
            )
        }

        private fun Char.isOpen() = this in "bcdef"
    }
}
