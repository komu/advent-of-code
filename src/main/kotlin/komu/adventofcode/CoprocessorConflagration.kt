package komu.adventofcode

import java.math.BigInteger

fun coprocessorConflagrationTest(input: String): Int {
    val ops = CCOp.parseOps(input)

    val state = CCState(ops)

    while (state.running)
        state.step()

    return state.muls
}

fun coprocessorConflagrationTest2(): Int {
    // converted from original code, refactored and optimized
    var count = 0

    for (num in 109900..126900 step 17) {
        val upper = num + 100
        outer@ for (x in 2..Math.sqrt(upper.toDouble()).toInt())
            for (y in x..upper)
                if (x * y == num) {
                    count++
                    break@outer
                }
    }

    return count
}

private class CCState(val ops: List<CCOp>) {
    private var pos = 0
    val regs = mutableMapOf<CCReg, BigInteger>()
    var muls = 0

    val running: Boolean
        get() = pos in ops.indices

    fun step() {
        ops[pos++].func(this)
    }

    fun set(x: CCReg, y: CCSource) {
        regs[x] = y.eval(regs)
    }

    fun sub(x: CCReg, y: CCSource) {
        regs[x] = x.eval(regs) - y.eval(regs)
    }

    fun mul(x: CCReg, y: CCSource) {
        muls++
        regs[x] = x.eval(regs) * y.eval(regs)
    }

    fun jnz(x: CCSource, y: CCSource) {
        if (x.eval(regs) != BigInteger.ZERO) {
            pos += y.eval(regs).toInt() - 1
        }
    }
}

private class CCOp(val s: String, val func: CCState.() -> Unit) {

    override fun toString(): String {
        return s
    }

    companion object {

        private val setRegex = Regex("""set (\w) (.+)""")
        private val subRegex = Regex("""sub (\w) (.+)""")
        private val mulRegex = Regex("""mul (\w) (.+)""")
        private val jnzRegex = Regex("""jnz (\w) (.+)""")

        fun parseOps(s: String) =
                s.nonEmptyLines().map { parse(it) }

        fun parse(s: String): CCOp {
            setRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return CCOp(s) { set(x, y) }
            }

            subRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return CCOp(s) { sub(x, y) }
            }

            mulRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return CCOp(s) { mul(x, y) }
            }

            jnzRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toSource()
                val y = m.groupValues[2].toSource()
                return CCOp(s) { jnz(x, y) }
            }

            error("invalid op '$s'")
        }
    }
}

private fun String.toSource(): CCSource =
    if (this[0].isLetter()) toReg() else CCConst(BigInteger(this))

private fun String.toReg(): CCReg = CCReg(this)

private sealed class CCSource {
    abstract fun eval(regs: Map<CCReg, BigInteger>): BigInteger
}

private class CCConst(val value: BigInteger) : CCSource() {
    override fun eval(regs: Map<CCReg, BigInteger>) = value
    override fun toString() = value.toString()
}

private data class CCReg(val name: String) : CCSource() {
    override fun eval(regs: Map<CCReg, BigInteger>): BigInteger = regs[this] ?: BigInteger.ZERO
    override fun toString() = name
}
