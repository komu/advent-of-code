package komu.adventofcode.aoc2020

import komu.adventofcode.utils.lcm

fun shuttleService1(time: Long, input: String): Int {
    val shuttles = input.split(",").filter { it != "x" }.map { it.toInt() }

    val (shuttle, waitTime) = shuttles.map { it to shuttleWait(it, time) }.minByOrNull { it.second }!!

    return shuttle * waitTime
}

private fun shuttleWait(shuttle: Int, time: Long): Int = ((shuttle - time % shuttle) % shuttle).toInt()

private data class ShuttleConstrains(val period: Int, val offset: Int) {
    fun satisfies(time: Long) = shuttleWait(period, time + offset) == 0
}

fun shuttleService2(input: String): Long {
    var constraints = input.split(",").map { it.toIntOrNull() }.withIndex()
        .mapNotNull { (i, v) -> v?.let { ShuttleConstrains(it, i) } }
        .sortedByDescending { it.period }

    var delta = 1L
    var t = 0L

    var steps = 0
    while (true) {
        steps++
        if (constraints.any { it.satisfies(t) }) {
            val (satisfied, rest) = constraints.partition { it.satisfies(t) }

            delta = satisfied.fold(delta) { a, c -> lcm(a, c.period.toLong()) }

            constraints = rest

            if (constraints.isEmpty())
                return t
        }

        t += delta
    }
}
