package komu.adventofcode

fun heardYouLikeRegisters(input: String): Pair<Int, Int> {
    val ops = input.nonEmptyLines().map(Operation.Companion::parse)

    val registers = mutableMapOf<String, Int>()
    var max = 0
    for (op in ops) {
        val conditionValue = registers[op.conditionRegister] ?: 0

        if (op.conditionOperator(conditionValue, op.conditionConstant)) {
            val value = op.mode.eval(registers[op.register] ?: 0, op.amount)
            registers[op.register] = value
            max = max.coerceAtLeast(value)
        }
    }

    return Pair(registers.values.max() ?: 0, max)
}

private class Operation(val register: String,
                        val mode: Mode,
                        val amount: Int,
                        val conditionRegister: String,
                        val conditionOperator: RelOp,
                        val conditionConstant: Int) {

    companion object {
        private val regex = Regex("""(\w+) (inc|dec) (-?\d+) if (\w+) ([=!<>]+) (-?\d+)""")

        fun parse(s: String): Operation {
            val m = regex.matchEntire(s) ?: error("no match for '$s'")

            return Operation(
                    register = m.groupValues[1],
                    mode = Mode.valueOf(m.groupValues[2].toUpperCase()),
                    amount = m.groupValues[3].toInt(),
                    conditionRegister = m.groupValues[4],
                    conditionOperator = parseRelOp(m.groupValues[5]),
                    conditionConstant = m.groupValues[6].toInt())
        }
    }
}

private enum class Mode {
    INC {
        override fun eval(x: Int, y: Int): Int = x + y
    },
    DEC {
        override fun eval(x: Int, y: Int): Int = x - y
    };

    abstract fun eval(x: Int, y: Int): Int
}

private typealias RelOp = (Int, Int) -> Boolean

fun parseRelOp(s: String): RelOp = when (s) {
    "==" -> { x, y -> x == y }
    "!=" -> { x, y -> x != y }
    "<"  -> { x, y -> x < y }
    "<=" -> { x, y -> x <= y }
    ">"  -> { x, y -> x > y }
    ">=" -> { x, y -> x >= y }
    else -> error("invalid operator '$s'")
}
