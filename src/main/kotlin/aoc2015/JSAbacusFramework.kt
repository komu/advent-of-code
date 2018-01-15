package komu.adventofcode.aoc2015

fun jsabacus(input: String, bailOnRed: Boolean = false) =
        JsonCounter(input, bailOnRed).parseValue()

private class JsonCounter(input: String, private val bailOnRed: Boolean) {

    private val reader = JsonReader(input)

    fun parseValue(): Int? {
        reader.skipSpace()
        if (!reader.hasMore)
            return 0

        return when (reader.peek()) {
            '{' -> parseObject()
            '[' -> parseArray()
            '"' -> {
                val s = parseString()
                if (s == "red" && bailOnRed)
                    return null
                else
                    0
            }
            else -> parseNumber()
        }
    }

    private fun parseObject(): Int {
        reader.consume('{')

        var red = false
        var sum = 0
        while (reader.peek() != '}') {
            parseString()
            reader.consume(':')
            sum += parseValue() ?: run {
                red = true
                0
            }
            if (reader.peek() == ',')
                reader.consume(',')
        }

        reader.consume('}')
        return if (red) 0 else sum
    }

    private fun parseArray(): Int {
        reader.consume('[')

        var sum = 0
        while (reader.peek() != ']') {
            sum += parseValue() ?: 0
            if (reader.peek() == ',')
                reader.consume(',')
        }

        reader.consume(']')
        return sum
    }

    private fun parseString(): String {
        reader.consume('"')
        val value = reader.takeWhile { it != '"' }
        reader.consume('"')

        return value
    }

    private fun parseNumber(): Int {
        val value = reader.takeWhile { it == '-' || it.isDigit() }.toInt()
        reader.skipSpace()
        return value
    }
}

private class JsonReader(private val input: String) {

    private var index = 0

    val hasMore get() = index < input.length

    fun peek() = input[index]

    fun read() = input[index++]

    fun consume(expected: Char) {
        check(peek() == expected) { "expected '$expected' but got '${peek()}'"}
        index++
        skipSpace()
    }

    inline fun takeWhile(predicate: (Char) -> Boolean) = buildString {
        while (hasMore && predicate(peek()))
            append(read())
    }

    fun skipSpace() {
        while (hasMore && peek().isWhitespace())
            index++
    }
}
