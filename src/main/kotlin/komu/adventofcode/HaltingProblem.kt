package komu.adventofcode

class TuringMachine(val startState: String, config: TuringMachine.() -> Unit) {

    private val states = mutableMapOf<String, StateDefinition>()
    private val tape = Tape()

    init {
        config()
    }

    fun state(name: String, config: StateDefinition.() -> Unit) {
        val state = StateDefinition()
        state.config()
        states[name] = state
    }

    fun checksum() = tape.checksum()

    fun run(steps: Int) {
        var state = states[startState]!!

        repeat(steps) {
            val actions = state[tape.value]
            tape.value = actions.write == 1 // TODO
            tape.move(direction = actions.move)
            state = states[actions.nextState]!!
        }
    }
}

private class Tape {
    private val list = mutableMapOf<Int, Boolean>()
    private var index = 0

    var value: Boolean
        get() = list[index] ?: false
        set(v) {
            list[index] = v
        }

    fun checksum() = list.values.count { it }

    fun move(direction: TuringDirection) {
        when (direction) {
            TuringDirection.LEFT -> index--
            TuringDirection.RIGHT -> index++
        }
    }
}

class Actions(val write: Int, val move: TuringDirection, val nextState: String)

class StateDefinition {

    lateinit var onZero: Actions
    lateinit var onOne: Actions

    operator fun get(b: Boolean) = if (b) onOne else onZero

    fun onZero(write: Int, move: TuringDirection, nextState: String) {
        onZero = Actions(write, move, nextState)
    }

    fun onOne(write: Int, move: TuringDirection, nextState: String) {
        onOne = Actions(write, move, nextState)
    }
}

enum class TuringDirection {
    LEFT, RIGHT
}
