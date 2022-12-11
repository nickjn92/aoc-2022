package day11

import PartSolver
import math.lcm

class Part2 : PartSolver<Long> {

    override fun solve(input: List<String>): Long {
        val inspections = mutableMapOf<Int, Long>()
        val monkeys = input.toMonkeys()

        val lcm = monkeys.values.map { it.divisibleBy.toLong() }.reduce(::lcm)

        repeat(10_000) {
            playRound(monkeys.values.toList(), inspections) { it % lcm }
        }

        return inspections.values.sortedDescending()
            .take(2)
            .reduce { acc, i -> acc * i }
    }
}
