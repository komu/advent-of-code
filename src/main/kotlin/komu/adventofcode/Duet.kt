package komu.adventofcode

import java.math.BigInteger
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

fun duet(input: String): Int {
    val ops = DuetOp.parseOps(input)

    val buffer = ReceiveLastSentBuffer<BigInteger>()
    val state = DuetState(ops, 0, buffer, buffer)
    while (state.running && !buffer.received)
        state.step()

    return buffer.value.toInt()
}

fun duet2(input: String): Int {
    val ops = DuetOp.parseOps(input)

    val channels = ChannelPair<BigInteger>()

    val p0 = DuetState(ops, 0, channels.channel1, channels.channel0)
    val p1 = DuetState(ops, 1, channels.channel0, channels.channel1)

    val threads = listOf(p0, p1).map { p ->
        thread {
            try {
                while (p.running)
                    p.step()
            } catch (e: DeadlockException) {
            }
        }
    }

    threads.forEach { it.join() }

    return channels.channel1.sends.get()
}

interface Send<in T> {
    fun send(value: T)
}

interface Receive<out T> {
    fun receive(): T
}

class DeadlockException : RuntimeException()

class ChannelPair<T> {
    private var activeReceivers = 0
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    val channel0 = FifoBuffer<T>()
    val channel1 = FifoBuffer<T>()

    inner class FifoBuffer<T> : Send<T>, Receive<T> {

        val sends = AtomicInteger()
        private val buffer = ArrayDeque<T>()

        override fun send(value: T) = lock.withLock {
            sends.incrementAndGet()
            buffer.addLast(value)
            condition.signal()
        }

        override fun receive(): T = lock.withLock {
            activeReceivers++

            while (buffer.isEmpty()) {
                if (activeReceivers > 1 && channel0.buffer.isEmpty() && channel1.buffer.isEmpty()) {
                    condition.signalAll()
                    throw DeadlockException()
                }
                condition.await()
            }

            activeReceivers--

            return buffer.removeFirst()
        }
    }
}

class ReceiveLastSentBuffer<T : Any> : Send<T>, Receive<T> {

    lateinit var value: T
    var received = false

    override fun send(value: T) {
        this.value = value
    }

    override fun receive(): T {
        received = true
        return value
    }
}

private open class DuetState(val ops: List<DuetOp>,
                             val pid: Int,
                             val input: Receive<BigInteger>,
                             val output: Send<BigInteger>) {

    private var pos = 0
    private val regs = mutableMapOf<DuetReg, BigInteger>().apply {
        this[DuetReg("p")] = pid.toBigInteger()
    }

    val running: Boolean
        get() = pos in ops.indices

    fun step() {
        ops[pos++].func(this)
    }

    fun snd(x: DuetSource) {
        output.send(x.eval(regs))
    }

    fun set(x: DuetReg, y: DuetSource) {
        regs[x] = y.eval(regs)
    }

    fun add(x: DuetReg, y: DuetSource) {
        regs[x] = x.eval(regs) + y.eval(regs)
    }

    fun mul(x: DuetReg, y: DuetSource) {
        regs[x] = x.eval(regs) * y.eval(regs)
    }

    fun mod(x: DuetReg, y: DuetSource) {
        regs[x] = x.eval(regs) % y.eval(regs)
    }

    fun rcv(x: DuetReg) {
        regs[x] = input.receive()
    }

    fun jgz(x: DuetSource, y: DuetSource) {
        if (x.eval(regs) > BigInteger.ZERO) {
            pos += y.eval(regs).toInt() - 1
        }
    }
}

private class DuetOp(val func: DuetState.() -> Unit) {

    companion object {

        private val sndRegex = Regex("""snd (.+)""")
        private val setRegex = Regex("""set (\w) (.+)""")
        private val addRegex = Regex("""add (\w) (.+)""")
        private val mulRegex = Regex("""mul (\w) (.+)""")
        private val modRegex = Regex("""mod (\w) (.+)""")
        private val rcvRegex = Regex("""rcv (\w)""")
        private val jgzRegex = Regex("""jgz (\w) (.+)""")

        fun parseOps(s: String) =
                s.nonEmptyLines().map { DuetOp.parse(it) }

        fun parse(s: String): DuetOp {
            sndRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toSource()
                return DuetOp { snd(x) }
            }

            setRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return DuetOp { set(x, y) }
            }

            addRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return DuetOp { add(x, y) }
            }

            mulRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return DuetOp { mul(x, y) }
            }

            modRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                val y = m.groupValues[2].toSource()
                return DuetOp { mod(x, y) }
            }

            rcvRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toReg()
                return DuetOp { rcv(x) }
            }

            jgzRegex.matchEntire(s)?.let { m ->
                val x = m.groupValues[1].toSource()
                val y = m.groupValues[2].toSource()
                return DuetOp { jgz(x, y) }
            }

            error("invalid op '$s'")
        }
    }
}

private fun String.toSource(): DuetSource =
        if (this[0].isLetter()) toReg() else DuetConst(BigInteger(this))

private fun String.toReg(): DuetReg = DuetReg(this)

private sealed class DuetSource {
    abstract fun eval(regs: Map<DuetReg, BigInteger>): BigInteger
}

private class DuetConst(val value: BigInteger) : DuetSource() {
    override fun eval(regs: Map<DuetReg, BigInteger>) = value
    override fun toString() = value.toString()
}

private data class DuetReg(val name: String) : DuetSource() {
    override fun eval(regs: Map<DuetReg, BigInteger>): BigInteger = regs[this] ?: BigInteger.ZERO
    override fun toString() = name
}
