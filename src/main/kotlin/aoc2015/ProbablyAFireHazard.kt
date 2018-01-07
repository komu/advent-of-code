package komu.adventofcode.aoc2015

import komu.adventofcode.utils.nonEmptyLines

fun probablyAFireHazard(input: String) = runGridCommands(input, object : CommandProcessor {
    override fun turnOn(value: Int) = 1
    override fun turnOff(value: Int) = 0
    override fun toggle(value: Int) = if (value == 0) 1 else 0
}).lit

fun probablyAFireHazard2(input: String) = runGridCommands(input, object : CommandProcessor {
    override fun turnOn(value: Int) = value + 1
    override fun turnOff(value: Int) = (value - 1).coerceAtLeast(0)
    override fun toggle(value: Int) = value + 2
}).totalBrightness

private fun runGridCommands(input: String, commandProcessor: CommandProcessor): LightGrid {
    val grid = LightGrid()
    val regex = Regex("""(.+) (\d+),(\d+) through (\d+),(\d+)""")

    for (line in input.nonEmptyLines()) {
        val match = regex.matchEntire(line) ?: error("invalid line '$line'")
        val command = match.groupValues[1]
        val (x1, y1, x2, y2) = match.groupValues.drop(2).map { it.toInt() }

        when (command) {
            "turn on" -> grid.modify(x1, y1, x2, y2, commandProcessor::turnOn)
            "turn off" -> grid.modify(x1, y1, x2, y2, commandProcessor::turnOff)
            "toggle" -> grid.modify(x1, y1, x2, y2, commandProcessor::toggle)
            else -> error("unknown command '$command'")
        }
    }

    return grid
}

private interface CommandProcessor {
    fun turnOn(value: Int): Int
    fun turnOff(value: Int): Int
    fun toggle(value: Int): Int
}

private class LightGrid {

    private val grid = IntArray(WIDTH * HEIGHT)

    val lit: Int
        get() = grid.count { it != 0 }

    val totalBrightness: Int
        get() = grid.sum()

    fun modify(x1: Int, y1: Int, x2: Int, y2: Int, f: (Int) -> Int) {
        for (y in y1..y2)
            for (x in x1..x2) {
                val i = y * WIDTH + x
                grid[i] = f(grid[i])
            }
    }

    companion object {

        private const val WIDTH = 1000
        private const val HEIGHT = 1000
    }
}
