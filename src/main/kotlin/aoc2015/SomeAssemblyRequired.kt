package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

typealias Signal = Int
typealias Source = () -> Signal

fun someAssemblyRequired(input: String, overrides: Map<String, Signal> = emptyMap()): Int {
    val sources = SourceMap()

    fun parse(line: String) {
        Regex("""(.+) LSHIFT (.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[3])
            target.input = BinaryOp(sources[m.groupValues[1]], BitOp.LSHIFT, sources[m.groupValues[2]])
            return
        }
        Regex("""(.+) RSHIFT (.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[3])
            target.input = BinaryOp(sources[m.groupValues[1]], BitOp.RSHIFT, sources[m.groupValues[2]])
            return
        }
        Regex("""(.+) OR (.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[3])
            target.input = BinaryOp(sources[m.groupValues[1]], BitOp.OR, sources[m.groupValues[2]])
            return
        }
        Regex("""(.+) AND (.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[3])
            target.input = BinaryOp(sources[m.groupValues[1]], BitOp.AND, sources[m.groupValues[2]])
            return
        }
        Regex("""NOT (.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[2])
            target.input = Not(sources[m.groupValues[1]])
            return
        }
        Regex("""(.+) -> (.+)""").matchEntire(line)?.let { m ->
            val target = sources.getWire(m.groupValues[2])
            target.input = sources[m.groupValues[1]]
            return
        }

        error("unknown '$line'")
    }

    input.nonEmptyLines().forEach { line ->
        parse(line)
    }

    for ((name, value) in overrides)
        sources.getWire(name).input = { value }

    return sources["a"]()
}

private class SourceMap {

    private val wires = mutableMapOf<String, Wire>()

    operator fun get(name: String): Source =
            if (name.matches(Regex("""\d+"""))) {
                val value = name.toInt()
                fun() = value
            } else {
                getWire(name)
            }

    fun getWire(name: String): Wire =
            wires.getOrPut(name) { Wire(name) }

}

class Wire(val name: String) : Source {
    var input: Source? = null
    private val signal by lazy { input?.invoke() ?: error("wire '$name' is not initialized") }

    override fun invoke() = signal
}

private enum class BitOp(val func: (Signal, Signal) -> Signal) {
    AND(Signal::and),
    OR(Signal::or),
    LSHIFT({ lhs, rhs -> (lhs shl rhs) and 0xffff }),
    RSHIFT(Signal::ushr);

    operator fun invoke(lhs: Signal, rhs: Signal) = func(lhs, rhs)
}

private class BinaryOp(val lhs: Source, val op: BitOp, val rhs: Source) : Source {

    override fun invoke() = op(lhs(), rhs())
}

private class Not(val lhs: Source) : Source {

    override fun invoke() = lhs().inv()
}
