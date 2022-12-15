package day6

import PartSolver

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean) = solve(input.first(), STEP_SIZE)

    companion object {
        private const val STEP_SIZE = 14
    }
}
