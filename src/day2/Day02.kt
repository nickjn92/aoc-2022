package day2

import day2.Move.Companion.losingMoves
import day2.Move.Companion.moves
import day2.Move.Companion.points
import day2.Move.Companion.strategies
import day2.Move.Companion.winningMoves
import day2.Strategy.DRAW
import day2.Strategy.LOSE
import day2.Strategy.WIN
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (oppMove, ownMove) = it.split(" ").map { moveStr -> moves[moveStr]!! }
            (ownMove.ordinal + 1) + points[ownMove]!![oppMove]!!
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (oppMoveStr, strategy) = it.split(" ")

            val oppMove = moves[oppMoveStr]
            val ownMove = when (strategies[strategy]!!) {
                LOSE -> losingMoves[oppMove]!!
                DRAW -> oppMove
                WIN -> winningMoves[oppMove]!!
            }!!

            (ownMove.ordinal + 1) + points[ownMove]!![oppMove]!!
        }
    }

    val testInput = readInput("day2/test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day2/input")
    println(part1(input))
    println(part2(input))
}
