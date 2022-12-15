package day13

import PartSolver

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        return input.toPackets()
            .chunked(2)
            .mapIndexed { idx, packets ->
                println(" == Pair ${idx + 1} ==")
                val (left, right) = packets
                (idx + 1) to (left <= right)
            }
            .filter { it.second }
            .sumOf { it.first }
    }
}
