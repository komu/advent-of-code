package komu.adventofcode.aoc2016

import komu.adventofcode.utils.nonEmptyLines
import java.util.*

fun twoFactorAuthentication1(input: String): Int =
    Screen.load(input).numberOfLitPixels

fun twoFactorAuthentication2(input: String) {
    Screen.load(input).dump()
}

private sealed class ScreenCommand {

    abstract fun applyTo(screen: Screen)

    data class Rect(val w: Int, val h: Int) : ScreenCommand() {
        override fun applyTo(screen: Screen) {
            screen.rect(w, h)
        }
    }

    data class RotateColumn(val x: Int, val by: Int) : ScreenCommand() {
        override fun applyTo(screen: Screen) {
            screen.rotateColumn(x, by)
        }
    }

    data class RotateRow(val y: Int, val by: Int) : ScreenCommand() {
        override fun applyTo(screen: Screen) {
            screen.rotateRow(y, by)
        }
    }

    companion object {
        private val rectPattern = Regex("""rect (\d+)x(\d+)""")
        private val rotateColumnPattern = Regex("""rotate column x=(\d+) by (\d+)""")
        private val rotateRowPattern = Regex("""rotate row y=(\d+) by (\d+)""")

        fun parse(s: String): ScreenCommand {
            rectPattern.matchEntire(s)?.destructured?.let { (x, y) -> return Rect(x.toInt(), y.toInt()) }
            rotateRowPattern.matchEntire(s)?.destructured?.let { (x, by) -> return RotateRow(x.toInt(), by.toInt()) }
            rotateColumnPattern.matchEntire(s)?.destructured?.let { (y, by) ->
                return RotateColumn(
                    y.toInt(),
                    by.toInt()
                )
            }
            error("invalid input '$s'")
        }
    }
}

private class Screen {

    private val buffer = BitSet(width * height)

    private operator fun set(x: Int, y: Int, v: Boolean) {
        buffer[y * width + x] = v
    }

    private operator fun get(x: Int, y: Int) =
        buffer[y * width + x]

    val numberOfLitPixels: Int
        get() = buffer.cardinality()

    fun rect(w: Int, h: Int) {
        for (y in 0 until h)
            for (x in 0 until w)
                this[x, y] = true
    }

    fun rotateColumn(x: Int, by: Int) {
        repeat(by) {
            val oldBottom = this[x, height - 1]

            for (y in height - 1 downTo 1)
                this[x, y] = this[x, y - 1]

            this[x, 0] = oldBottom
        }
    }

    fun rotateRow(y: Int, by: Int) {
        repeat(by) {
            val oldRight = this[width - 1, y]

            for (x in width - 1 downTo 1)
                this[x, y] = this[x - 1, y]

            this[0, y] = oldRight
        }
    }

    fun dump() {
        for (y in 0 until height) {
            for (x in 0 until width)
                print(if (this[x, y]) '#' else '.')
            println()
        }
    }

    companion object {
        private const val width = 50
        private const val height = 6

        fun load(input: String): Screen {
            val ops = input.nonEmptyLines().map { ScreenCommand.parse(it) }
            val screen = Screen()

            for (op in ops)
                op.applyTo(screen)

            return screen
        }
    }
}