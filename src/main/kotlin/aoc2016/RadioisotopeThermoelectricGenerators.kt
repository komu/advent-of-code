package komu.adventofcode.aoc2016

import komu.adventofcode.aoc2016.Isotope.*

fun radioisotopeThermoelectricGenerators1(): Int =
    solve(GameState.initial1())

fun radioisotopeThermoelectricGenerators2(): Int =
    solve(GameState.initial2())

private enum class Isotope {
    POLONIUM, THULIUM, PROMETHIUM, RUTHENIUM, COBALT, ELERIUM, DILITHIUM
}

@JvmInline
private value class Item private constructor(val mask: Int) {

    operator fun plus(item: Item) = ItemSet.fromBits(mask or item.mask)

    companion object {
        val indices = 0..13

        operator fun get(index: Int) = Item(1 shl index)
        fun gen(isotope: Isotope) = this[isotope.ordinal]
        fun chip(isotope: Isotope) = this[isotope.ordinal + 7]
    }
}

@JvmInline
private value class ItemSet private constructor(val bits: Int) {

    operator fun plus(item: Item) = ItemSet(bits or item.mask)
    operator fun plus(items: ItemSet) = ItemSet(bits or items.bits)
    operator fun minus(items: ItemSet) = ItemSet(bits and items.bits.inv())
    operator fun contains(item: Item) = item.mask and bits != 0

    companion object {
        fun of(item: Item) = ItemSet(item.mask)
        fun of(item1: Item, item2: Item) = ItemSet(item1.mask or item2.mask)
        fun fromBits(bits: Int) = ItemSet(bits)

        val empty = ItemSet(0)
    }
}

@JvmInline
private value class GameState private constructor(
    private val bits: Long
) {

    val currentFloor: Int
        get() = (bits shr (4 * 14)).toInt() + 1

    private fun itemsOn(floor: Int) =
        ItemSet.fromBits(((bits shr ((floor - 1) * 14)) and 16383).toInt())

    fun toGoal(): GameState =
        GameState(4, ItemSet.empty, ItemSet.empty, ItemSet.empty, itemsOn(1) + itemsOn(2) + itemsOn(3) + itemsOn(4))

    val isValid: Boolean
        get() = isValid(floor = 1) && isValid(floor = 2) && isValid(floor = 3) && isValid(floor = 4)

    private fun isValid(floor: Int): Boolean {
        val bits = itemsOn(floor).bits
        val generators = bits and 255
        val microchips = ((bits shr 7) and 255)

        return generators == 0 || microchips and generators.inv() == 0
    }

    fun transitions(): List<GameState> {
        val currentFloor = currentFloor
        val result = mutableListOf<GameState>()
        val itemsOnFloor = itemsOn(currentFloor)
        val neighboringFloors = neighbors[currentFloor - 1]

        for (i in 0..Item.indices.last) {
            val item1 = Item[i]

            if (item1 in itemsOnFloor) {
                for (targetFloor in neighboringFloors) {
                    val targetState1 = moveTo(currentFloor, targetFloor, ItemSet.of(item1))
                    if (targetState1.isValid)
                        result += targetState1
                }

                for (j in i + 1..Item.indices.last) {
                    val item2 = Item[j]

                    if (item2 in itemsOnFloor) {
                        for (targetFloor in neighboringFloors) {
                            val targetState2 = moveTo(currentFloor, targetFloor, ItemSet.of(item1, item2))
                            if (targetState2.isValid)
                                result += targetState2
                        }
                    }
                }
            }
        }

        return result
    }

    private fun moveTo(currentFloor: Int, targetFloor: Int, items: ItemSet) = GameState(
        targetFloor,
        newItemsForFloor(1, currentFloor, targetFloor, items),
        newItemsForFloor(2, currentFloor, targetFloor, items),
        newItemsForFloor(3, currentFloor, targetFloor, items),
        newItemsForFloor(4, currentFloor, targetFloor, items),
    )

    private fun newItemsForFloor(floor: Int, currentFloor: Int, targetFloor: Int, movedItems: ItemSet) =
        when (floor) {
            currentFloor -> itemsOn(floor) - movedItems
            targetFloor -> itemsOn(floor) + movedItems
            else -> itemsOn(floor)
        }

    companion object {

        private val neighbors = listOf(intArrayOf(2), intArrayOf(1, 3), intArrayOf(2, 4), intArrayOf(3))

        operator fun invoke(
            currentFloor: Int,
            floor1: ItemSet,
            floor2: ItemSet,
            floor3: ItemSet,
            floor4: ItemSet
        ): GameState {
            require(currentFloor in 1..4)
            return GameState(
                floor1.bits.toLong()
                        or (floor2.bits.toLong() shl 14)
                        or (floor3.bits.toLong() shl (2 * 14))
                        or (floor4.bits.toLong() shl (3 * 14))
                        or ((currentFloor - 1).toLong() shl (4 * 14))
            )
        }

        fun initial1() = GameState(
            currentFloor = 1,
            floor1 = Item.gen(POLONIUM) +
                    Item.gen(THULIUM) +
                    Item.chip(THULIUM) +
                    Item.gen(PROMETHIUM) +
                    Item.gen(RUTHENIUM) +
                    Item.chip(RUTHENIUM) +
                    Item.gen(COBALT) +
                    Item.chip(COBALT),
            floor2 = Item.chip(POLONIUM) + Item.chip(PROMETHIUM),
            floor3 = ItemSet.empty,
            floor4 = ItemSet.empty
        )

        fun initial2(): GameState {
            val initial1 = initial1()

            return GameState(
                currentFloor = 1,
                floor1 = initial1.itemsOn(1)
                        + Item.gen(ELERIUM) + Item.chip(ELERIUM) + Item.gen(DILITHIUM) + Item.chip(DILITHIUM),
                floor2 = initial1.itemsOn(2),
                floor3 = initial1.itemsOn(3),
                floor4 = initial1.itemsOn(4)
            )
        }
    }
}

private fun solve(initial: GameState): Int {
    val goal = initial.toGoal()
    val seen = mutableSetOf(initial)
    val queue = ArrayDeque(listOf(Pair(0, initial)))
    var maxDepth = 0

    while (queue.isNotEmpty()) {
        val (steps, state) = queue.removeFirst()
        if (steps > maxDepth) {
            print("\rdepth: $steps")
            System.out.flush()
            maxDepth = steps
        }

        if (state == goal)
            return steps

        for (next in state.transitions())
            if (seen.add(next))
                queue += Pair(steps + 1, next)
    }

    error("no path found")
}
