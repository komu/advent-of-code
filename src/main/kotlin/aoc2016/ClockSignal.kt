package komu.adventofcode.aoc2016

fun clockSignal(): Int =
    (0..Int.MAX_VALUE).find { handOptimized(it).isExpectedResult() } ?: error("no result")

fun verifyClockSignalCompatibility(input: String) {
    for (i in 0..1000) {
        val result = handOptimized(i)

        val vm = SignallingAssembunnyVM(input, i, result.count())
        vm.execute()

        if (result != vm.output)
            error("$i failed: $result != ${vm.output}")
    }
}

private fun handOptimized(init: Int) = buildList {
    var a = init + 2534

    while (a != 0) {
        add(a % 2)
        a /= 2
    }
}

private fun List<Int>.isExpectedResult() =
    withIndex().all { (index, value) -> index % 2 == value }

private class SignallingAssembunnyVM(code: String, init: Int, val maxOutput: Int) : AssembunnyVM(code) {

    init {
        set(Reg.A, init)
    }

    val output = mutableListOf<Int>()

    override fun out(value: Int) {
        output.add(value)
        if (output.size == maxOutput)
            stop()
    }
}