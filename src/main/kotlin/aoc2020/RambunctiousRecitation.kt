package komu.adventofcode.aoc2020

fun rambunctiousRecitation1(startingNumbers: List<Int>) =
    rambunctiousRecitation(startingNumbers, 2020)

fun rambunctiousRecitation2(startingNumbers: List<Int>) =
    rambunctiousRecitation(startingNumbers, 30_000_000)

private fun rambunctiousRecitation(startingNumbers: List<Int>, count: Int): Int {
    val lastSpoken = IntArray(count) { -1 }

    for ((turn, number) in startingNumbers.withIndex())
        lastSpoken[number] = turn

    var next = -1
    var last = -1

    for (turn in startingNumbers.size until count) {
        val number = if (next == -1) 0 else (turn - 1) - next

        next = lastSpoken[number]
        lastSpoken[number] = turn
        last = number
    }

    return last
}
