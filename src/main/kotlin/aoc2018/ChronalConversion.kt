package komu.adventofcode.aoc2018

import komu.adventofcode.utils.add
import java.util.*

fun chronalConversion1(input: String): Int =
    chronalEval(input)

fun chronalConversion2(): Int =
    chronalKotlin()

private fun chronalEval(input: String): Int {
    val vm = VM.load(input)

    while (true) {
        val inst = vm.currentInstruction
        if (inst != null && inst.opcode == OpCode.eqrr && inst.b == 0)
            return vm.regs[inst.a]

        vm.step()
    }
}

// Original code ported to Kotlin
private fun chronalKotlin(): Int {
    val seen = BitSet()
    var last = 0

    var r3 = 0

    while (true) {
        var r4 = r3 or 65536
        r3 = 7041048

        while (true) {
            r3 = (((r3 + (r4 and 255)) and 16_777_215) * 65_899) and 16_777_215

            if (r4 < 256) {
                if (!seen.add(r3))
                    return last

                last = r3

                break
            } else {

                var newR4 = 0

                while ((newR4 + 1) * 256 <= r4)
                    newR4 += 1

                r4 = newR4
            }
        }
    }
}
