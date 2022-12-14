package day14

import PartSolver

class Part1 : PartSolver<Long> {

    override fun solve(input: List<String>): Long {
        println("Part 1:")
        val cave = input.toCave()
        cave.simulateUntilEternalFlow()
        cave.draw()
        return cave.settledSand.size.toLong() - 1
    }
}
