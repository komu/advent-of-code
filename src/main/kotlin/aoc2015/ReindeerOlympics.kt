package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun reindeerOlympics1(input: String): Int =
    input.nonEmptyLines().map { Reindeer.parse(it) }.maxOf { it.distanceAfter(2503) }

fun reindeerOlympics2(input: String): Int {
    val reindeers = input.nonEmptyLines().map { Reindeer.parse(it) }

    for (time in 1..2503) {
        val distances = reindeers.map { it to it.distanceAfter(time) }
        val leadingDistance = distances.maxOf { it.second }

        for ((reindeer, _) in distances.filter { (_, d) -> d == leadingDistance })
            reindeer.score++
    }

    return reindeers.maxOf { it.score }
}

private data class Reindeer(val name: String, val speed: Int, val flySeconds: Int, val restSeconds: Int) {

    var score = 0

    fun distanceAfter(seconds: Int): Int {
        var remaining = seconds

        var distance = 0
        while (remaining > 0) {
            distance += speed * minOf(remaining, flySeconds)
            remaining -= (flySeconds + restSeconds)
        }

        return distance
    }

    companion object {
        private val regex =
            Regex("""(.+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""")

        fun parse(s: String): Reindeer {
            val (name, speed, flySeconds, restSeconds) = regex.matchEntire(s)?.destructured
                ?: error("failed to parse '$s'")

            return Reindeer(name, speed.toInt(), flySeconds.toInt(), restSeconds.toInt())
        }
    }
}