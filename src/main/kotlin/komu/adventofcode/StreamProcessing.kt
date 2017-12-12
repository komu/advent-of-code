package komu.adventofcode

fun streamScore(input: String): Int =
    parseThing(StreamReader(input)).score(1)

fun streamGarbage(input: String): Int =
    parseThing(StreamReader(input)).garbageAmount

private fun parseThing(input: StreamReader): Thing = when (input.next) {
    '{' -> parseGroup(input)
    '<' -> parseJunk(input)
    else -> error("unexpected ${input.next} in $input")
}

private fun parseGroup(input: StreamReader): Group {
    input.expect('{')

    val things = mutableListOf<Thing>()
    while (input.next != '}') {
        things += parseThing(input)

        if (input.next != '}')
            input.expect(',')
    }

    input.expect('}')

    return Group(things)
}

private fun parseJunk(input: StreamReader): Junk {
    input.expect('<')

    var count = 0
    while (true) {
        val ch = input.read()
        if (ch == '>')
            return Junk(count)

        if (ch == '!')
            input.read()
        else
            count++
    }
}

private class StreamReader(private val s: String) {

    private var offset = 0

    val next: Char get() = s[offset]

    fun expect(ch: Char) {
        check (s[offset] == ch)
        offset++
    }

    fun read(): Char = s[offset++]

    override fun toString() = "$s [offset=$offset]"
}

private sealed class Thing {
    abstract fun score(level: Int): Int
    abstract val garbageAmount: Int
}

private class Junk(override val garbageAmount: Int) : Thing() {
    override fun score(level: Int) = 0
    override fun toString() = "*"
}

private data class Group(val things: List<Thing>) : Thing() {
    override fun score(level: Int) = level + things.sumBy { it.score(level + 1) }
    override fun toString() = things.toString()
    override val garbageAmount: Int get() = things.sumBy { it.garbageAmount }
}
