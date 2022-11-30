package komu.adventofcode.aoc2015

import komu.adventofcode.utils.contentMatch
import komu.adventofcode.utils.nonEmptyLines

fun medicineForRudolph1(s: String): Int {
    val rules = MedicineRule.parseRules(s)
    val medicine = Molecule.parse(s.nonEmptyLines().last())

    return rules.flatMap { it.rewrite(medicine) }.toSet().size
}

fun medicineForRudolph2(s: String): Int {
    val rules = MedicineRule.parseRules(s)
    val medicine = Molecule.parse(s.nonEmptyLines().last())

    fun recurse(state: Molecule, depth: Int): Int {
        if (state.isEmpty)
            return depth

        for (rule in rules)
            for (replacement in rule.reverseRewrite(state)) {
                val result = recurse(replacement, depth + 1)
                if (result != -1)
                    return result
            }

        return -1
    }

    return recurse(medicine, 0)
}

@JvmInline
private value class Atom(val name: String)

@JvmInline
private value class Molecule(val atoms: List<Atom>) {

    val isEmpty: Boolean
        get() = atoms.isEmpty()

    fun replace(from: Int, count: Int, newAtoms: List<Atom>) =
        Molecule(atoms.subList(0, from) + newAtoms + atoms.subList(from + count, atoms.size))

    companion object {

        fun parse(s: String): Molecule {
            val atoms = mutableListOf<Atom>()
            var start = 0
            for (i in 1..s.lastIndex) {
                if (s[i].isUpperCase()) {
                    atoms.add(Atom(s.substring(start, i)))
                    start = i
                }
            }

            atoms.add(Atom(s.substring(start)))
            return Molecule(atoms)
        }
    }
}

private data class MedicineRule(val from: Atom?, val to: Molecule) {

    fun rewrite(molecule: Molecule): List<Molecule> = molecule.atoms.withIndex().mapNotNull { (i, atom) ->
        if (atom == from)
            molecule.replace(i, 1, to.atoms)
        else
            null
    }

    fun reverseRewrite(molecule: Molecule): List<Molecule> = molecule.atoms.indices.mapNotNull { i ->
        if (molecule.atoms.contentMatch(i, to.atoms))
            molecule.replace(i, to.atoms.size, listOfNotNull(from))
        else
            null
    }

    companion object {

        fun parseRules(s: String): List<MedicineRule> =
            s.nonEmptyLines().dropLast(1).map { line ->
                val (f, to) = line.split(" => ")
                val from = if (f == "e") null else Atom(f)
                MedicineRule(from, Molecule.parse(to))
            }
    }
}

