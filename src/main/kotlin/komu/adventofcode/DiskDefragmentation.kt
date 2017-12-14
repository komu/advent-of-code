package komu.adventofcode

fun diskDefragmentationUsedSquares(input: String) = Grid(input).usedSquares

fun diskDefragmentationRegions(input: String) = Grid(input).markAndCountRegions()

private class Grid(input: String) {

    private val rows = Array(128) { row ->
        val bits = knotHash("$input-$row").hexBits
        Array(128) { if (bits[it]) UNMARKED else 0 }
    }

    operator fun get(y: Int) = rows[y]

    val usedSquares: Int
        get() = rows.sumBy { it.count { it != 0 } }

    fun markAndCountRegions(): Int {
        var counter = 0

        rows.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (value == UNMARKED)
                    mark(x, y, ++counter)
            }
        }

        return counter
    }

    private fun mark(x: Int, y: Int, value: Int) {
        if (rows.getOrNull(y)?.getOrNull(x) == UNMARKED) {
            rows[y][x] = value

            mark(x + 1, y, value)
            mark(x - 1, y, value)
            mark(x, y + 1, value)
            mark(x, y - 1, value)
        }
    }

    companion object {
        const val UNMARKED = -1
    }
}
