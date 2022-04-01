package komu.adventofcode.aoc2018

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.Direction.*
import komu.adventofcode.utils.Point
import utils.shortestPathBetween

fun regularMap(input: String) =
    RoomMap.create(input).solution()

fun regularMap2(input: String) =
    RoomMap.create(input).solution2()

private class RoomMap {

    private val rooms = mutableMapOf(Point.ORIGIN to Room(Point.ORIGIN))

    fun solution(): Int =
        rooms.values.maxOf { from -> shortestPathBetween(Point.ORIGIN, from.point) { this[it].neighbors }!!.size }

    fun solution2(): Int =
        rooms.values.count { from -> shortestPathBetween(Point.ORIGIN, from.point) { this[it].neighbors }!!.size >= 1000 }

    operator fun get(p: Point): Room =
        rooms[p] ?: error("no room for $p")

    fun move(fromRoom: Room, dir: Direction): Room {
        val target = fromRoom.point + dir
        val targetRoom = rooms.getOrPut(target) { Room(target) }

        fromRoom.doors += dir
        targetRoom.doors += dir.opposite
        return targetRoom
    }

    fun move(from: Point, dir: Direction): Room =
        move(this[from], dir)

    class Room(val point: Point) {
        val doors = mutableSetOf<Direction>()
        val neighbors: List<Point>
            get() = doors.map { point + it }
    }

    companion object {
        fun create(input: String): RoomMap {
            val parsed = RegexParser(input).parse()
            val roomMap = RoomMap()
            parsed.traverse(Point.ORIGIN, roomMap)
            return roomMap
        }
    }
}

private sealed class MapRegex {

    abstract fun traverse(p: Point, roomMap: RoomMap): Set<Point>

    data class Walk(val directions: List<Direction>) : MapRegex() {
        override fun traverse(p: Point, roomMap: RoomMap): Set<Point> {
            var pt = p
            for (d in directions) {
                roomMap.move(pt, d)
                pt += d
            }

            return setOf(pt)
        }
    }

    data class Sequence(val exps: List<MapRegex>) : MapRegex() {
        override fun traverse(p: Point, roomMap: RoomMap): Set<Point> =
            exps.fold(listOf(p)) { ps, r -> ps.flatMap { r.traverse(it, roomMap) } }.toSet()
    }

    data class Alternation(val exps: List<MapRegex>) : MapRegex() {
        override fun traverse(p: Point, roomMap: RoomMap) =
            exps.flatMap { it.traverse(p, roomMap) }.toSet()
    }
}

private fun directionFor(c: Char) = when (c) {
    'N' -> UP
    'E' -> RIGHT
    'S' -> DOWN
    'W' -> LEFT
    else -> error("invalid direction '$c'")
}

private class RegexParser(private val s: String) {

    private var i = 0

    fun parse(): MapRegex {
        expect('^')

        val exp = parseExp()

        expect('$')
        check(i == s.length)
        return exp
    }

    private fun parseExp(): MapRegex {
        val exps = mutableListOf<MapRegex>()
        while (nextChar != '$' && nextChar != ')' && nextChar != '|') {
            exps += when (val c = readChar()) {
                in "NWSE" -> {
                    val directions = mutableListOf(directionFor(c))
                    while (nextChar in "NWSE")
                        directions += directionFor(readChar())

                    MapRegex.Walk(directions)
                }
                '(' ->
                    parseAlternation()
                else ->
                    error("unexpected character '$c'")
            }
        }

        return exps.singleOrNull() ?: MapRegex.Sequence(exps)
    }

    private fun parseAlternation(): MapRegex {
        val exps = mutableListOf<MapRegex>()

        exps += parseExp()
        while (nextChar == '|') {
            expect('|')
            exps += parseExp()
        }

        expect(')')
        return exps.singleOrNull() ?: MapRegex.Alternation(exps)
    }

    private fun readChar() = s[i++]

    private val nextChar: Char
        get() = s[i]

    private fun expect(c: Char) {
        check(readChar() == c)
    }
}