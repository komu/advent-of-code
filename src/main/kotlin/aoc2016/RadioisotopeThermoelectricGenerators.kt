package komu.adventofcode.aoc2016

import komu.adventofcode.aoc2016.Isotope.*

fun radioisotopeThermoelectricGenerators1(): Int {
    return solve(GameState.initial())
}

fun radioisotopeThermoelectricGenerators2(): Int =
    solve(GameState.initial2())

private enum class Isotope {
    POLONIUM, THULIUM, PROMETHIUM, RUTHENIUM, COBALT, ELERIUM, DILITHIUM
}

@JvmInline
private value class Item private constructor(val mask: Int) {

    companion object {
        val indices = 0..13
        fun forIndex(index: Int) = Item(1 shl index)
        fun generator(isotope: Isotope) = forIndex(isotope.ordinal)
        fun microchip(isotope: Isotope) = forIndex(isotope.ordinal + 7)
    }
}

@JvmInline
private value class ItemSet private constructor(private val bits: Int) {

    constructor(items: List<Item>) : this(items.fold(0) { s, i -> s or i.mask })

    operator fun plus(item: Item) = ItemSet(bits or item.mask)
    operator fun plus(items: ItemSet) = ItemSet(bits or items.bits)
    operator fun minus(items: ItemSet) = ItemSet(bits and items.bits.inv())
    operator fun contains(item: Item) = item.mask and bits != 0

    val isEmpty: Boolean
        get() = bits == 0

    val isValid: Boolean
        get() {
            val generators = bits and 255
            val microchips = bits shr 7

            return generators == 0 || (microchips and generators.inv()) == 0
        }

    companion object {
        val empty = ItemSet(0)
    }
}

private data class GameState(
    val currentFloor: Int,
    val floors: List<ItemSet>,
) {

    val isGoal: Boolean
        get() = floors[0].isEmpty && floors[1].isEmpty && floors[2].isEmpty

    val isValid: Boolean
        get() = floors.all { it.isValid }

    private val neighboringFloors: List<Int>
        get() = when (currentFloor) {
            0 -> listOf(1)
            3 -> listOf(2)
            else -> listOf(currentFloor - 1, currentFloor + 1)
        }

    fun transitions(): List<GameState> {
        val result = mutableListOf<GameState>()

        for (items in picks())
            for (targetFloor in neighboringFloors) {
                val targetState = moveTo(targetFloor, items)
                if (targetState.isValid)
                    result += targetState
            }

        return result
    }

    // pick any 1 or 2 items from current floor
    private fun picks(): List<ItemSet> {
        val result = mutableListOf<ItemSet>()

        val itemsOnFloor = floors[currentFloor]
        for (i in Item.indices) {
            val item1 = Item.forIndex(i)
            if (item1 in itemsOnFloor) {
                val single = ItemSet.empty + item1
                result += single

                for (j in i + 1..Item.indices.last) {
                    val item2 = Item.forIndex(j)
                    if (item2 in itemsOnFloor)
                        result += (single + item2)
                }
            }
        }

        return result
    }

    private fun moveTo(targetFloor: Int, items: ItemSet) =
        GameState(targetFloor, floors.withIndex().map { (num, floor) ->
            when (num) {
                currentFloor -> floor - items
                targetFloor -> floor + items
                else -> floor
            }
        })

    companion object {
        fun initial() = GameState(
            currentFloor = 0,
            listOf(
                ItemSet(
                    listOf(
                        Item.generator(POLONIUM),
                        Item.generator(THULIUM),
                        Item.microchip(THULIUM),
                        Item.generator(PROMETHIUM),
                        Item.generator(RUTHENIUM),
                        Item.microchip(RUTHENIUM),
                        Item.generator(COBALT),
                        Item.microchip(COBALT)
                    ),
                ),
                ItemSet(
                    listOf(
                        Item.microchip(POLONIUM),
                        Item.microchip(PROMETHIUM)
                    )
                ),
                ItemSet.empty,
                ItemSet.empty
            )
        )

        fun initial2() = GameState(
            currentFloor = 0,
            listOf(
                ItemSet(
                    listOf(
                        Item.generator(POLONIUM),
                        Item.generator(THULIUM),
                        Item.microchip(THULIUM),
                        Item.generator(PROMETHIUM),
                        Item.generator(RUTHENIUM),
                        Item.microchip(RUTHENIUM),
                        Item.generator(COBALT),
                        Item.microchip(COBALT),
                        Item.generator(ELERIUM),
                        Item.microchip(ELERIUM),
                        Item.generator(DILITHIUM),
                        Item.microchip(DILITHIUM),
                    )
                ),
                ItemSet(
                    listOf(
                        Item.microchip(POLONIUM),
                        Item.microchip(PROMETHIUM)
                    )
                ),
                ItemSet.empty,
                ItemSet.empty
            )
        )
    }
}

private fun solve(initial: GameState): Int {
    val seen = mutableSetOf(initial)
    val queue = ArrayDeque(listOf(Pair(0, initial)))
    var maxDepth = 0

    while (queue.isNotEmpty()) {
        val (steps, state) = queue.removeFirst()
        if (steps > maxDepth) {
            println("depth: $steps")
            maxDepth = steps
        }

        if (state.isGoal) {
            return steps
        }

        for (next in state.transitions())
            if (seen.add(next))
                queue += Pair(steps + 1, next)
    }

    error("no path found")
}
