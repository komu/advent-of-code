package komu.adventofcode.aoc2016

import komu.adventofcode.utils.hexEncodedMd5Hash

fun howAboutANiceGameOfChess1(doorId: String): String {
    var index = 0
    val output = StringBuilder()
    while (output.length < 8) {
        val hash = hexEncodedMd5Hash("$doorId$index")
        if (hash.startsWith("00000"))
            output.append(hash[5])
        index++
    }
    return output.toString()
}

fun howAboutANiceGameOfChess2(doorId: String): String {
    var index = 0
    val output = CharArray(8) { ' ' }
    while (' ' in output) {
        val hash = hexEncodedMd5Hash("$doorId$index")
        if (hash.startsWith("00000")) {
            val position = hash[5]
            val value = hash[6]
            if (position in '0'..'7') {
                val pos = position.digitToInt()

                if (output[pos] == ' ')
                    output[pos] = value
            }
        }
        index++
    }
    return output.joinToString("")
}
