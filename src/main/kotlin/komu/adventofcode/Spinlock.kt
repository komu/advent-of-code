package komu.adventofcode

fun spinlock1(steps: Int): Int {
    val buffer = mutableListOf(0)
    var position = 0

    for (value in 1..2017) {
        position = ((position + steps) % buffer.size) + 1
        buffer.add(position, value)
    }

    return buffer[position + 1]
}

fun spinlock2(steps: Int): Int {
    var position = 0
    var last = -1

    for (i in 1..50_000_000) {
        position = (position + steps) % i
        if (position++ == 0)
            last = i
    }

    return last
}
