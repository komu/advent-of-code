package komu.adventofcode.aoc2015

fun letItSnow1(row: Int, col: Int): Long {
    var value = 20151125L
    repeat(diagonalIndexOf(row, col)) {
        value = value * 252533 % 33554393
    }
    return value
}

private fun diagonalIndexOf(row: Int, col: Int): Int {
    var currentRow = 1
    var currentCol = 1
    var currentIndex = 0

    while (currentRow != row || currentCol != col) {
        if (currentRow == 1) {
            currentRow = currentCol + 1
            currentCol = 1
        } else {
            currentRow--
            currentCol++
        }

        currentIndex++
    }
    return currentIndex
}
