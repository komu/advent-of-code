package komu.adventofcode.aoc2016

fun anElephantNamedJoseph1(count: Int): Int {
    val elves = Elf.createElves(count)

    var elf = elves.first()
    while (elf != elf.next) {
        elf.removeNext()
        elf = elf.next
    }

    return elf.id
}

fun anElephantNamedJoseph2(count: Int): Int {
    val elves = Elf.createElves(count)

    var elf = elves.first()
    var stealPrev = elves[elves.size / 2 - 1]
    var remaining = elves.size

    while (elf != elf.next) {
        stealPrev.removeNext()

        if (remaining % 2 == 1)
            stealPrev = stealPrev.next

        elf = elf.next
        remaining--
    }

    return elf.id
}

private class Elf(val id: Int) {
    lateinit var next: Elf

    fun removeNext() {
        next = next.next
    }

    companion object {
        fun createElves(count: Int): List<Elf> {
            val elves = (1..count).map { Elf(it) }

            for ((elf, next) in elves.zipWithNext())
                elf.next = next
            elves.last().next = elves.first()
            return elves
        }
    }
}
