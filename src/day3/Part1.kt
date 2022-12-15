package day3

import PartSolver

class Part1 : PartSolver<Int> {
    override fun solve(input: List<String>, isTest: Boolean): Int {
        return input.map {
            val firstCompartment = it.substring(0 until it.length / 2)
            val secondCompartment = it.substring(it.length / 2)
            (firstCompartment intersect secondCompartment).single()
        }.sumOf { it.toPriority() }
    }
}
