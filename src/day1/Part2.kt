package day1

import PartSolver

class Part2 : PartSolver<Int> {
    override fun solve(input: List<String>) = getCalories(input).sortedDescending().take(3).sum()
}
