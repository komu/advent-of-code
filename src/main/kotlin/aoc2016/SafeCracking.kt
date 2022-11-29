package komu.adventofcode.aoc2016

import komu.adventofcode.aoc2016.AssembunnyInstruction.*
import komu.adventofcode.aoc2016.Reg.*

fun safeCracking(input: String, eggs: Int): Int {
    val vm = PatchedAssembunnyVM(input)

    vm.set(A, eggs)
    vm.execute()

    return vm.get(A)
}

private class PatchedAssembunnyVM(code: String) : AssembunnyVM(code) {

    override fun step() {
        if (matches(Cpy(B, C), Inc(A), Dec(C), Jnz(C, -2), Dec(D), Jnz(D, -5))) {
            set(A, get(A) + get(B) * get(D))
            set(C, 0)
            set(D, 0)
            ip += 6
        } else {
            super.step()
        }
    }
}
