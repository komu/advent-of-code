package komu.adventofcode.aoc2019

import komu.adventofcode.utils.powerset

fun cryostasis(input: String): Int {
    val machine = IntCodeMachine(input)

    machine.writeLine("west")
    machine.writeLine("take ornament")
    machine.writeLine("west")
    machine.writeLine("take astrolabe")
    machine.writeLine("south")
    machine.writeLine("take hologram")
    machine.writeLine("north")
    machine.writeLine("north")
    machine.writeLine("take fuel cell")
    machine.writeLine("south")
    machine.writeLine("east") // navigation
    machine.writeLine("south")
    machine.writeLine("east")
    machine.writeLine("take weather machine")
    machine.writeLine("west")
    machine.writeLine("north")
    machine.writeLine("east")
    machine.writeLine("east")
    machine.writeLine("take mug") // engineering
    machine.writeLine("north")
    machine.writeLine("take monolith")
    machine.writeLine("south") // engineering
    machine.writeLine("south")
    machine.writeLine("west")
    machine.writeLine("north")
    machine.writeLine("west")
    machine.writeLine("take bowl of rice")
    machine.writeLine("north")
    machine.writeLine("west")
    machine.writeLine("inv")

    val items = listOf("bowl of rice", "monolith", "mug", "weather machine", "fuel cell", "astrolabe", "ornament", "hologram")

    while (machine.input.isNotEmpty())
        machine.tick()

    for (droppedItems in items.powerset()) {
        val copy = machine.clone()

        val collectedOutput = StringBuilder()
        copy.writeOutput = { output ->
            collectedOutput.append(output.toInt().toChar())
        }

        for (item in droppedItems)
            copy.writeLine("drop $item")

        copy.writeLine("north")

        while (copy.running) {
            if (collectedOutput.contains("Alert"))
                break
            copy.tick()
        }

        val result = Regex("You should be able to get in by typing (\\d+) on the keypad at the main airlock.").find(collectedOutput) ?: continue
        return result.groupValues[1].toInt()
    }

    return 0
}

/**
                          stables                                                     holodeck
                            |                                                            |
                          arcade --- navigation  ------------ hull breach ---------- engineering
                            |            |                           |                   |
gift wrapping center -- corridor         |                           |                   |
                                         |                           |                   |
                         hallway -- observatory -- storage    hot chocolate fountain     |
                                                     |                                   |
                                                  passages                               |
                                                                                         |
                                                                                         |
                                                                                         |
                                      |                                                  |
                               security checkpoint --- warp drive maintenance            |
                                                                |                        |
                                                         crew quarters -- science lab    |
                                                                              |          |
                                                                           sick bay -- kitchen

hot chocolate fountain: photons -> grue
escape pod -> space
navigation: ornament
observatory: escape pod
arcade: astrolabe
storage: weather machine
engineering: mug
corridor: hologram
gift wrapping center: giant electromagnet -> stuck
stables: fuel cell
kitchen: infinite loop -> infinite loop
science lab: molten lava -> melt
crew quarters: bowl of rice
holodeck: monolith
 */
