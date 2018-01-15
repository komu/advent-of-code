package komu.adventofcode.aoc2015

fun elvesLookElvesSay(input: String, iterations: Int): Int {
    var s = input
    repeat(iterations) {
        s = lookAndSay(s)
    }
    return s.length
}

private fun lookAndSay(s: String) = buildString {
    var previous = ' '
    var count = 0

    for (c in s) {
        if (c == previous) {
            count++
        } else {
            if (count > 0)
                append(count).append(previous)
            previous = c
            count = 1
        }
    }

    if (count > 0)
        append(count).append(previous)
}
