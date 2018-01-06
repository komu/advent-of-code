package komu.adventofcode.aoc2015

fun notQuiteLisp(s: String): Int {
    var floor = 0
    for (c in s) {
        when (c) {
            '(' -> floor++
            ')' -> floor--
        }
    }
    return floor
}

fun notQuiteLispBasement(s: String): Int {
    var floor = 0

    s.forEachIndexed { index, c ->
        when (c) {
            '(' -> floor++
            ')' -> floor--
        }

        if (floor == -1)
            return index + 1
    }

    return floor
}
