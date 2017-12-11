package komu.adventofcode

import kotlin.coroutines.experimental.buildSequence

fun spiralMemorySteps(index: Int): Int =
    distances().drop(index - 1).first()

private fun distances(): Sequence<Int> = buildSequence {
    yield(0)

    for (n in 1..Int.MAX_VALUE) {
        repeat(4) {
            for (v in n*2-1 downTo n)
                yield(v)

            for (v in (n +1)..n *2)
                yield(v)
        }
    }
}
