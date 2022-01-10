package komu.adventofcode.aoc2019

import komu.adventofcode.utils.Direction.DOWN
import komu.adventofcode.utils.Direction.RIGHT
import komu.adventofcode.utils.Point.Companion.ORIGIN

fun tractorBeam1(input: String): Int {
    val machine = IntCodeMachine(input)
    return (0..49).sumBy { y -> (0..49).count { x -> machine.isOnBeam(x, y) } }
}

fun tractorBeam2(input: String): Int {
    val machine = IntCodeMachine(input)

    val p = machine.followLowerSide().first { it.y >= 99 && machine.isOnBeam(it.x + 99, it.y - 99) }
    return p.x * 10000 + (p.y - 99)
}

private fun IntCodeMachine.followLowerSide() = sequence {
    var point = ORIGIN

    while (true) {
        yield(point)
        point += DOWN
        while (!isOnBeam(point.x, point.y))
            point += RIGHT
    }
}

private fun IntCodeMachine.isOnBeam(x: Int, y: Int): Boolean {
    val clone = clone()
    clone.sendInput(x.toLong(), y.toLong())
    return clone.waitForOutput() == 1L
}
