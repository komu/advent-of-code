package komu.adventofcode.aoc2016

fun explosivesInCyberspace1(input: CharSequence): Long =
    explosivesInCyberspace(input, recurse = false)

fun explosivesInCyberspace2(input: CharSequence): Long =
    explosivesInCyberspace(input, recurse = true)

private fun explosivesInCyberspace(input: CharSequence, recurse: Boolean): Long {
    val data = input.filter { !it.isWhitespace() }

    var result = 0L
    var pos = 0
    while (pos < data.length) {
        val marker = Marker.from(data, pos)
        if (marker != null) {
            val len = if (recurse) explosivesInCyberspace(marker.data, recurse = true) else marker.data.length.toLong()
            result += marker.repetitions * len
            pos = marker.next
        } else {
            result += 1
            pos++
        }
    }

    return result
}

private data class Marker(
    val repetitions: Int,
    val data: CharSequence,
    val next: Int
) {

    companion object {
        private val markerRegex = Regex("""\((\d+)x(\d+)\)""")

        fun from(s: CharSequence, pos: Int): Marker? {
            val match = markerRegex.matchAt(s, pos) ?: return null

            val length = match.groupValues[1].toInt()
            val count = match.groupValues[2].toInt()
            val offset = pos + match.value.length

            return Marker(
                repetitions = count,
                data = s.subSequence(offset, offset + length),
                next = offset + length
            )
        }
    }
}

