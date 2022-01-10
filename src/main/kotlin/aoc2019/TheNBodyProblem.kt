package komu.adventofcode.aoc2019

import komu.adventofcode.utils.choosePairs
import komu.adventofcode.utils.lcm3
import kotlin.math.abs

fun nBodyProblem(moons: List<Moon>, steps: Int): Int {
    val pairs = moons.choosePairs()

    repeat(steps) {
        for ((m1, m2) in pairs) {
            m1.applyGravity(m2)
            m2.applyGravity(m1)
        }

        for (m in moons)
            m.applyVelocity()
    }

    return moons.sumBy { it.energy }
}

fun nBodyProblem2(moons: List<Moon>) = lcm3(
    period(moons.map { it.x.position }),
    period(moons.map { it.y.position }),
    period(moons.map { it.z.position })
)

fun period(xs: List<Int>): Long {
    val states = xs.map { AxisState(it) }
    val pairs = states.choosePairs()
    val seen = mutableSetOf<List<AxisState>>()

    var steps = 0L
    while (seen.add(states.map { it.copy() })) {
        for ((m1, m2) in pairs) {
            m1.applyGravity(m2)
            m2.applyGravity(m1)
        }

        for (m in states)
            m.applyVelocity()

        steps++
    }

    return steps
}

data class AxisState(var position: Int, var velocity: Int = 0) {

    fun applyGravity(s: AxisState) {
        velocity += gravity(position, s.position)
    }

    fun applyVelocity() {
        position += velocity
    }

    companion object {
        private fun gravity(a: Int, b: Int) = when {
            a < b -> 1
            a > b -> -1
            else -> 0
        }
    }
}

class Moon(x: Int, y: Int, z: Int) {
    val x = AxisState(x)
    val y = AxisState(y)
    val z = AxisState(z)

    fun applyGravity(m: Moon) {
        x.applyGravity(m.x)
        y.applyGravity(m.y)
        z.applyGravity(m.z)
    }

    fun applyVelocity() {
        x.applyVelocity()
        y.applyVelocity()
        z.applyVelocity()
    }

    val energy: Int
        get() = (abs(x.position) + abs(y.position) + abs(z.position)) * (abs(x.velocity) + abs(y.velocity) + abs(z.velocity))
}
