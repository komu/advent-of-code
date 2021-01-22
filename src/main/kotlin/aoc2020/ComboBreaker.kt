package aoc2020

fun comboBreaker1(cardKey: Long, doorKey: Long): Long =
    transformSubject(doorKey, detectLoopSize(cardKey))

private fun detectLoopSize(secret: Long): Int {
    var value = 1L

    for (size in 1..1_000_000_000) {
        value = (value * 7) % 20201227
        if (value == secret)
            return size
    }

    error("could not detect loop size for secret $secret")
}

private fun transformSubject(subject: Long, loopSize: Int): Long {
    var value = 1L
    repeat(loopSize) {
        value = (value * subject) % 20201227
    }
    return value
}
