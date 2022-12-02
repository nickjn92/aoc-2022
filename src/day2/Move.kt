package day2

import day2.Strategy.DRAW
import day2.Strategy.LOSE
import day2.Strategy.WIN

enum class Move {
    ROCK,
    PAPER,
    SCISSORS;

    companion object {
        val strategies = mapOf(
            "X" to LOSE,
            "Y" to DRAW,
            "Z" to WIN
        )

        val moves = mapOf(
            "A" to ROCK,
            "X" to ROCK,
            "B" to PAPER,
            "Y" to PAPER,
            "C" to SCISSORS,
            "Z" to SCISSORS
        )

        val winningMoves = mapOf(
            ROCK to PAPER,
            PAPER to SCISSORS,
            SCISSORS to ROCK
        )

        val losingMoves = mapOf(
            ROCK to SCISSORS,
            PAPER to ROCK,
            SCISSORS to PAPER
        )

        val points = mapOf(
            ROCK to mapOf(
                ROCK to 3,
                PAPER to 0,
                SCISSORS to 6
            ),
            PAPER to mapOf(
                ROCK to 6,
                PAPER to 3,
                SCISSORS to 0
            ),
            SCISSORS to mapOf(
                ROCK to 0,
                PAPER to 6,
                SCISSORS to 3
            )
        )
    }
}
enum class Strategy {
    LOSE,
    DRAW,
    WIN
}
