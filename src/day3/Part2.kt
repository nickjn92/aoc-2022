package day3

import PartSolver

class Part2 : PartSolver<Int> {
    override fun solve(input: List<String>): Int {
        return input.asSequence().chunked(3)
            .map { it.map { backpack -> backpack.toSet() } }
            .map { it.reduce { acc, elem -> acc intersect elem } }
            .flatten()
            .sumOf { it.toPriority() }
    }
}
