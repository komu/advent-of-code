package komu.adventofcode.aoc2018

import komu.adventofcode.aoc2018.MapElement.*
import komu.adventofcode.utils.Point

fun settlersOfTheNorthPoleTest(input: String) =
    (1..10).fold(SettlersMap.parse(input)) { m, _ -> m.step() }.resourceValue

fun settlersOfTheNorthPoleTest2(input: String): Int {
    var map = SettlersMap.parse(input)

    val maps = mutableListOf(map)
    while (true) {
        map = map.step()
        if (map in maps) {
            val prefixLength = maps.indexOf(map)
            val loopLength = maps.size - prefixLength
            return maps[prefixLength + (1_000_000_000 - prefixLength) % loopLength].resourceValue
        }
        maps += map
    }
}

private enum class MapElement(val code: Char) {
    GROUND('.'), TREE('|'), LUMBERYARD('#');

    companion object {
        fun forCode(code: Char) =
            values().find { it.code == code } ?: error("invalid code '$code'")
    }
}

private class SettlersMap(private val width: Int, private val height: Int, init: (Point) -> MapElement) {

    private val data = Array(width * height) { init(Point(it % width, it / width)) }

    override fun equals(other: Any?): Boolean = other is SettlersMap && data.contentEquals(other.data)
    override fun hashCode() = data.contentHashCode()

    val resourceValue: Int
        get() = count(TREE) * count(LUMBERYARD)

    operator fun get(p: Point) = data[p.y * width + p.x]
    operator fun contains(p: Point) = p.x in (0 until width) && p.y in (0 until height)

    fun step() = SettlersMap(width, height) { p -> step(p) }

    fun step(p: Point): MapElement = when (this[p]) {
        GROUND -> if (countAdjacent(p, TREE) >= 3) TREE else GROUND
        TREE -> if (countAdjacent(p, LUMBERYARD) >= 3) LUMBERYARD else TREE
        LUMBERYARD -> if (countAdjacent(p, LUMBERYARD) >= 1 && countAdjacent(p, TREE) >= 1) LUMBERYARD else GROUND
    }

    private fun countAdjacent(p: Point, type: MapElement): Int =
        p.allNeighbors.count { it in this && this[it] == type }

    fun count(type: MapElement): Int =
        data.count { it == type }

    companion object {
        fun parse(input: String): SettlersMap {
            val lines = input.lines()
            val height = lines.size
            val width = lines[0].length

            return SettlersMap(width, height) { p -> MapElement.forCode(lines[p.y][p.x]) }
        }
    }
}