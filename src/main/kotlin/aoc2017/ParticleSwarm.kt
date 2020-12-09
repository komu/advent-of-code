package komu.adventofcode.aoc2017

import komu.adventofcode.utils.nonEmptyLines
import java.util.Comparator.comparing
import kotlin.math.sqrt

fun particleSwarmClosest(input: String): Int =
        Particle.parseAll(input).minWithOrNull(comparing(Particle::accelerationDistance)
                .thenComparing(Particle::velocityDistance)
                .thenComparing(Particle::positionDistance))!!.index

fun particleSwarmSurvivors(input: String): Int {
    val particles = Particle.parseAll(input).toMutableList()

    // 40 is enough simulations to get the correct answer
    val colliders = mutableSetOf<Particle>()
    repeat(40) {
        for (p in particles)
            p.step()

        particles.forEachIndexed { index, particle ->
            if (particle !in colliders) {
                for (other in particles.subList(index + 1, particles.size)) {
                    if (particle.position == other.position) {
                        colliders.add(particle)
                        colliders.add(other)
                    }
                }
            }
        }

        particles.removeAll(colliders)
        colliders.clear()
    }

    return particles.size
}

private class Particle(val index: Int,
                       var position: Vector3,
                       var velocity: Vector3,
                       val acceleration: Vector3) {

    val accelerationDistance get() = acceleration.distance
    val velocityDistance get() = velocity.distance
    val positionDistance get() = position.distance

    fun step() {
        velocity += acceleration
        position += velocity
    }

    companion object {
        private val regex = Regex("""p=(.+), v=(.+), a=(.+)""")

        fun parseAll(input: String): List<Particle> =
                input.nonEmptyLines().mapIndexed { index, s -> parse(index, s) }

        fun parse(index: Int, s: String): Particle {
            val m = regex.matchEntire(s) ?: error("invalid particle: '$s'")
            val (_, p, v, a) = m.groupValues
            return Particle(index, Vector3.parse(p), Vector3.parse(v), Vector3.parse(a))
        }
    }
}

private data class Vector3(val x: Long, val y: Long, val z: Long) {
    operator fun plus(v: Vector3) = Vector3(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3) = Vector3(x - v.x, y - v.y, z - v.z)

    val distance: Double = sqrt((x * x + y * y + z * z).toDouble())

    companion object {
        private val regex = Regex("""<(-?\d+),(-?\d+),(-?\d+)>""")

        fun parse(s: String): Vector3 {
            val m = regex.matchEntire(s) ?: error("invalid vector: '$s'")
            val (_, x, y, z) = m.groupValues
            return Vector3(x.toLong(), y.toLong(), z.toLong())
        }
    }
}
