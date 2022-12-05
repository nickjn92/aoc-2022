package day5

import PartSolver

class Part1 : PartSolver<String> {
    override fun solve(input: List<String>): String {
        val state = input.getInitialCrateState()

        val moves = input.subList(input.indexOf("") + 1, input.size)
        moves.mapNotNull { regex.matchEntire(it) }
            .forEach {
                val nbr = it.groupValues[1].toInt()
                val from = it.groupValues[2].toInt() - 1
                val to = it.groupValues[3].toInt() - 1

                repeat(nbr) {
                    val crate = state.stacks[from].pop()
                    state.stacks[to].push(crate)
                }
            }

        return state.stacks.map { it.peek() }
            .joinToString("")
    }

    companion object {
        val regex = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
    }
}
