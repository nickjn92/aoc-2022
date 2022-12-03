package day3

import PartSolver

class Part1 : PartSolver<Int> {
    override fun solve(input: List<String>): Int {
        return input.map {
            val firstCompartment = it.substring(0 until it.length / 2).toSet()
            val secondCompartment = it.substring(it.length / 2).toSet()
            (firstCompartment intersect secondCompartment).single()
        }.sumOf { it.toPriority() }
    }
}
