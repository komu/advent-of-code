package aoc2016

import komu.adventofcode.utils.Direction
import komu.adventofcode.utils.nonEmptyLines

fun bathroomSecurity1(input: String) =
    bathroomSecurity(KeypadLayout.part1, input)

fun bathroomSecurity2(input: String) =
    bathroomSecurity(KeypadLayout.part2, input)

private fun bathroomSecurity(layout: KeypadLayout, input: String) =
    input.nonEmptyLines()
        .map { parseLine(it) }
        .runningFold(layout.five) { v, line -> layout.keypadFor(line, v) }
        .drop(1)
        .joinToString("") { it.code.toString() }

private fun parseLine(s: String): List<Direction> =
    s.map { c -> Direction.values().first { it.name.startsWith(c) } }

private class KeypadLayout(private val keys: List<Key>) {

    fun keypadFor(instructions: List<Direction>, initial: Key): Key =
        instructions.fold(initial) { v, d -> toward(v, d) }

    fun toward(key: Key, d: Direction): Key {
        val xx = key.x + d.dx
        val yy = key.y + d.dy

        return this.keys.find { it.x == xx && it.y == yy } ?: key
    }

    val five: Key
        get() = this.keys.first { it.code == '5' }

    companion object {
        val part1 = KeypadLayout((1..9).map { Key('0' + it, x = (it - 1) % 3, y = (it - 1) / 3) })
        val part2 = KeypadLayout(
            listOf(
                Key('1', x = 2, y = 0),
                Key('2', x = 1, y = 1), Key('3', x = 2, y = 1), Key('4', x = 3, y = 1),
                Key('5', x = 0, y = 2), Key('6', x = 1, y = 2), Key('7', x = 2, y = 2), Key('8', x = 3, y = 2), Key('9', x = 4, y = 2),
                Key('A', x = 1, y = 3), Key('B', x = 2, y = 3), Key('C', x = 3, y = 3),
                Key('D', x = 2, y = 4),
            )
        )
    }

    class Key(val code: Char, val x: Int, val y: Int)
}

