package day7

import PartSolver

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val directories = parseDirectories(input)

        return directories.values
            .map { it.size() }
            .filter { it < 100000 }
            .sum()
    }
}
