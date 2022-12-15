package day5

import PartSolver

class Part2 : PartSolver<String> {
    override fun solve(input: List<String>, isTest: Boolean): String {
        val state = input.getInitialCrateState()

        val moves = input.subList(input.indexOf("") + 1, input.size)
        moves.mapNotNull { Part1.regex.matchEntire(it) }
            .forEach {
                val nbr = it.groupValues[1].toInt()
                val from = it.groupValues[2].toInt() - 1
                val to = it.groupValues[3].toInt() - 1

                val crates = mutableListOf<Char>()
                repeat(nbr) {
                    crates.add(state.stacks[from].pop())
                }
                crates.reversed().forEach { crate ->
                    state.stacks[to].push(crate)
                }
            }

        return state.stacks.map { it.peek() }
            .joinToString("")
    }
}
