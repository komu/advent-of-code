package komu.adventofcode.aoc2017

fun corruptionChecksum(input: List<List<Int>>): Int =
    input.sumBy { it.maxOrNull()!! - it.minOrNull()!! }

fun corruptionChecksum2(input: List<List<Int>>): Int =
    input.sumBy { it.lineChecksum() }

private fun List<Int>.lineChecksum(): Int {
    forEachIndexed { iIndex, i ->
        forEachIndexed { jIndex, j ->
            if (iIndex != jIndex) {
                if (i divides j) return j / i
                if (j divides i) return i / j
            }
        }
    }

    error("invalid line: $this")
}

private infix fun Int.divides(rhs: Int) = rhs % this == 0
