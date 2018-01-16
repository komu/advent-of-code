package komu.adventofcode.aoc2015

fun jsabacus(input: String, bailOnRed: Boolean = false): Int {
    val tree = JsonReader(input).parseValue()

    return tree.sum(bailOnValue = if (bailOnRed) JsonString("red") else null)
}

private fun JsonValue.sum(bailOnValue: JsonValue? = null) : Int = when (this) {
    is JsonNumber -> value
    is JsonString -> 0
    is JsonArray -> elements.sumBy { it.sum(bailOnValue) }
    is JsonObject -> if (bailOnValue in map.values) 0 else map.values.sumBy { it.sum(bailOnValue) }
}

private sealed class JsonValue
private data class JsonNumber(val value: Int) : JsonValue()
private data class JsonString(val value: String) : JsonValue()
private data class JsonObject(val map: Map<JsonString, JsonValue>) : JsonValue()
private data class JsonArray(val elements: List<JsonValue>) : JsonValue()

private class JsonReader(private val input: String) {

    private var index = 0

    private val hasMore: Boolean
        get() = index < input.length

    fun parseValue(): JsonValue {
        skipSpace()
        if (!hasMore)
            error("unexpected eof")

        return when (peek()) {
            '{' -> parseObject()
            '[' -> parseArray()
            '"' -> parseString()
            else -> parseNumber()
        }
    }

    private fun parseObject(): JsonObject {
        consume('{')

        val map = mutableMapOf<JsonString, JsonValue>()
        while (peek() != '}') {
            val name = parseString()
            consume(':')
            map[name] = parseValue()
            if (peek() == ',')
                consume(',')
        }

        consume('}')
        return JsonObject(map)
    }

    private fun parseArray(): JsonArray {
        consume('[')

        val elements = mutableListOf<JsonValue>()
        while (peek() != ']') {
            elements += parseValue()
            if (peek() == ',')
                consume(',')
        }

        consume(']')
        return JsonArray(elements)
    }

    private fun parseString(): JsonString {
        consume('"')
        val value = takeWhile { it != '"' }
        consume('"')

        return JsonString(value)
    }

    private fun parseNumber(): JsonNumber {
        val value = takeWhile { it == '-' || it.isDigit() }.toInt()
        skipSpace()
        return JsonNumber(value)
    }

    private fun peek() = input[index]

    private fun read() = input[index++]

    private fun consume(expected: Char) {
        check(peek() == expected) { "expected '$expected' but got '${peek()}'"}
        index++
        skipSpace()
    }

    private inline fun takeWhile(predicate: (Char) -> Boolean) = buildString {
        while (hasMore && predicate(peek()))
            append(read())
    }

    private fun skipSpace() {
        while (hasMore && peek().isWhitespace())
            index++
    }
}
