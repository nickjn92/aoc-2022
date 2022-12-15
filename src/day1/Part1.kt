package day1

import PartSolver

class Part1 : PartSolver<Int> {
    override fun solve(input: List<String>, isTest: Boolean) = getCalories(input).max()
}
