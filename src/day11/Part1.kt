package day11

import PartSolver

class Part1 : PartSolver<Long> {

    override fun solve(input: List<String>, isTest: Boolean): Long {
        val inspections = mutableMapOf<Int, Long>()
        val monkeys = input.toMonkeys()

        repeat(20) {
            playRound(monkeys.values.toList(), inspections) { it / 3 }
        }

        return inspections.values.sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
    }
}
