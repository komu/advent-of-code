package komu.adventofcode.aoc2017

fun spiralMemorySteps(index: Int): Int =
    distances().drop(index - 1).first()

private fun distances(): Sequence<Int> = sequence {
    yield(0)

    for (n in 1..Int.MAX_VALUE) {
        repeat(4) {
            for (v in n * 2 - 1 downTo n)
                yield(v)

            for (v in (n + 1)..n * 2)
                yield(v)
        }
    }
}

typealias Coordinate = Pair<Int, Int>

fun spiralPart2(input: Int): Int {
    val memory = mutableMapOf<Coordinate, Int>()
    memory[Coordinate(0, 0)] = 1

    for (coord in coordinates()) {
        val sum = coord.neighbors().sumBy { memory[it] ?: 0 }
        if (sum > input)
            return sum
        memory[coord] = sum
    }

    error("unexpected")
}

fun coordinates(): Sequence<Coordinate> = sequence {
    var x = 0
    var y = 0

    yield(Coordinate(++x, y))
    var n = 1
    while (true) {
        repeat(n) {
            yield(Coordinate(x, ++y))
        }
        repeat(n + 1) {
            yield(Coordinate(--x, y))
        }
        repeat(n + 1) {
            yield(Coordinate(x, --y))
        }
        repeat(n + 2) {
            yield(Coordinate(++x, y))
        }

        n += 2
    }
}

private fun Coordinate.neighbors(): List<Coordinate> =
    neighborDeltas.map { this + it }

private operator fun Coordinate.plus(d: Pair<Int, Int>): Coordinate =
    Coordinate(first + d.first, second + d.second)

private val neighborDeltas = listOf(
        Pair(0, 1), Pair(-1, 1),
        Pair(-1, 0), Pair(-1, -1),
        Pair(0, -1), Pair(1, -1),
        Pair(1, 0), Pair(1, 1))
