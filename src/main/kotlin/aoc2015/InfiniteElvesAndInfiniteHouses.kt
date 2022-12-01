package komu.adventofcode.aoc2015

fun infiniteElvesAndInfiniteHouses1(input: Int): Int =
    infiniteElvesAndInfiniteHouses(input, visits = Int.MAX_VALUE, presents = 10)

fun infiniteElvesAndInfiniteHouses2(input: Int): Int =
    infiniteElvesAndInfiniteHouses(input, visits = 50, presents = 11)

private fun infiniteElvesAndInfiniteHouses(input: Int, visits: Int, presents: Int): Int {
    val houses = IntArray(1_000_000)

    for (elf in 1 until houses.size) {
        val last = if (visits == Int.MAX_VALUE) houses.lastIndex else (elf * visits).coerceAtMost(houses.lastIndex)
        for (n in elf .. last step elf)
            houses[n] += elf * presents
    }

    return houses.indexOfFirst { it > input }
}
