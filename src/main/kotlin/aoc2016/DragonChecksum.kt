package komu.adventofcode.aoc2016

fun dragonChecksum1(input: String, len: Int): String {
    var data = input

    while (data.length < len)
        data = "${data}0${flip(data)}"

    return checksum(data.take(len))
}

private fun flip(s: String) = buildString {
    for (i in s.indices.reversed())
        when (s[i]) {
            '1' -> append('0')
            '0' -> append('1')
        }
}

private fun checksum(data: String): String {
    var checksum = data

    while (checksum.length % 2 == 0) {
        checksum = buildString {
            for (i in 0..checksum.lastIndex step 2)
                if (checksum[i] == checksum[i + 1])
                    append('1')
                else
                    append('0')
        }
    }

    return checksum
}
