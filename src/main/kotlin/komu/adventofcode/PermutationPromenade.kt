package komu.adventofcode

private val regexSpin = Regex("""s(\d+)""")
private val regexExchange = Regex("""x(\d+)/(\d+)""")
private val regexPartner = Regex("""p(\w)/(\w)""")
private const val DEFAULT_PROGRAMS = "abcdefghijklmnop"

fun dance(input: String, programs: String = DEFAULT_PROGRAMS, loops: Int = 1): String {
    val ops = Op.parseList(input)

    val dance = PermutationPromenade(programs)
    repeat(loops % dancePeriod(ops, programs)) {
        for (op in ops)
            op.func(dance)
    }

    return dance.toString()
}

private fun dancePeriod(ops: List<Op>, programs: String): Int {
    val dance = PermutationPromenade(programs)

    var counter = 0

    while (true) {
        for (op in ops)
            op.func(dance)

        counter++

        if (dance.toString() == programs)
            return counter
    }
}

private class Op(val func: PermutationPromenade.() -> Unit) {

    companion object {

        fun parseList(str: String): List<Op> =
            str.trim().split(",").map { Op.parse(it) }

        fun parse(str: String): Op {
            regexSpin.matchEntire(str)?.let { m ->
                val x = m.groupValues[1].toInt()
                return Op { spin(x) }
            }

            regexExchange.matchEntire(str)?.let { m ->
                val a = m.groupValues[1].toInt()
                val b = m.groupValues[2].toInt()
                return Op { exchange(a, b) }
            }

            regexPartner.matchEntire(str)?.let { m ->
                val a = m.groupValues[1].first()
                val b = m.groupValues[2].first()
                return Op { partner(a, b) }
            }

            error("invalid input '$str'")
        }
    }
}

private class PermutationPromenade(programs: String) {

    val state = programs.toCharArray().toMutableList()

    fun spin(x: Int) {
        state.rotate(x)
    }

    fun exchange(a: Int, b: Int) {
        state.swap(a, b)
    }

    fun partner(a: Char, b: Char) {
        state.swap(state.indexOf(a), state.indexOf(b))
    }

    override fun toString() = state.joinToString("")
}
