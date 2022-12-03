package day2

import PartSolver

class Part2 : PartSolver<Int> {
    override fun solve(input: List<String>): Int {
        return input.sumOf {
            val (oppMoveStr, strategy) = it.split(" ")

            val oppMove = moves[oppMoveStr]
            val ownMove = when (strategies[strategy]!!) {
                Strategy.LOSE -> losingMoves[oppMove]!!
                Strategy.DRAW -> oppMove
                Strategy.WIN -> winningMoves[oppMove]!!
            }!!

            (ownMove.ordinal + 1) + points[ownMove]!![oppMove]!!
        }
    }
}
