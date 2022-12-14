package day14

import PartSolver

class Part2 : PartSolver<Long> {

    override fun solve(input: List<String>): Long {
        println("Part 2:")
        val cave = input.toCave()
        cave.simulateUntilBlockedFlow()
        cave.draw()
        return cave.settledSand.size.toLong()
    }
}
