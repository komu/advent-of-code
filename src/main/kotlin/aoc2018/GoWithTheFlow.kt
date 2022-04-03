package komu.adventofcode.aoc2018

// sum of divisors of n
fun goWithTheFlowReverseEngineered(n: Int): Int =
    (1..n).filter { n % it == 0 }.sum()

fun goWithTheFlow(input: String, init: Int = 0): Int {
    val vm = VM.load(input)
    vm.regs[0] = init
    vm.run()
    return vm.regs[0]
}
