package komu.adventofcode.aoc2016

fun leonardosMonorail(slow: Boolean): Int {
    var a = 1
    var b = 1

    repeat(if (slow) 33 else 26) {
        val tmp = a
        a += b
        b = tmp
    }

    return a + 196
}
