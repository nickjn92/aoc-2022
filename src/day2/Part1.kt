package day2

import PartSolver

class Part1 : PartSolver<Int> {
    override fun solve(input: List<String>): Int {
        return input.sumOf {
            val (oppMove, ownMove) = it.split(" ").map { moveStr -> moves[moveStr]!! }
            (ownMove.ordinal + 1) + points[ownMove]!![oppMove]!!
        }
    }
}
