package komu.adventofcode.aoc2020

fun operationOrder1(s: String) =
    ExpressionParser1(s).expr()

fun operationOrder2(s: String) =
    ExpressionParser2(s).expr()

private abstract class BaseExpressionParser(s: String) {

    protected val lexer = ArithmeticLexer(s)

    abstract fun expr(): Long

    protected fun prim(): Long {
        val token = lexer.readNext() ?: error("unexpected end")
        return if (token == "(") {
            val value = expr()
            check(lexer.readNext() == ")")
            value
        } else
            token.toLong()
    }
}

private class ExpressionParser1(s: String) : BaseExpressionParser(s) {

    override fun expr(): Long {
        var value = prim()

        while (lexer.peek() in setOf("+", "*"))
            when (lexer.readNext()) {
                "+" -> value += prim()
                "*" -> value *= prim()
            }

        return value
    }
}

private class ExpressionParser2(s: String) : BaseExpressionParser(s) {

    override fun expr(): Long {
        val value = term()

        return if (lexer.readNextIf("*"))
            value * expr()
        else
            value
    }

    private fun term(): Long {
        val value = prim()

        return if (lexer.readNextIf("+"))
            value + term()
        else
            value
    }
}

private class ArithmeticLexer(private val s: String) {

    private var index = 0

    fun readNextIf(expected: String): Boolean {
        val ok = peek() == expected
        if (ok)
            readNext()
        return ok
    }

    fun peek(): String? {
        val old = index
        val value = readNext()
        index = old
        return value
    }

    fun readNext(): String? {
        skipSpace()
        if (index >= s.length) return null

        val start = index
        if (s[index] in "()+*") {
            index++
        } else {
            check(s[index].isDigit())
            while (index < s.length && s[index].isDigit())
                index++
        }
        return s.substring(start, index)
    }

    private fun skipSpace() {
        while (index < s.length && s[index] == ' ')
            index++
    }
}
