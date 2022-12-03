package day2

import day2.Strategy.DRAW
import day2.Strategy.LOSE
import day2.Strategy.WIN

enum class Move {
    ROCK,
    PAPER,
    SCISSORS;
}

enum class Strategy {
    LOSE,
    DRAW,
    WIN
}

val strategies = mapOf(
    "X" to LOSE,
    "Y" to DRAW,
    "Z" to WIN
)

val moves = mapOf(
    "A" to Move.ROCK,
    "X" to Move.ROCK,
    "B" to Move.PAPER,
    "Y" to Move.PAPER,
    "C" to Move.SCISSORS,
    "Z" to Move.SCISSORS
)

val winningMoves = mapOf(
    Move.ROCK to Move.PAPER,
    Move.PAPER to Move.SCISSORS,
    Move.SCISSORS to Move.ROCK
)

val losingMoves = mapOf(
    Move.ROCK to Move.SCISSORS,
    Move.PAPER to Move.ROCK,
    Move.SCISSORS to Move.PAPER
)

val points = mapOf(
    Move.ROCK to mapOf(
        Move.ROCK to 3,
        Move.PAPER to 0,
        Move.SCISSORS to 6
    ),
    Move.PAPER to mapOf(
        Move.ROCK to 6,
        Move.PAPER to 3,
        Move.SCISSORS to 0
    ),
    Move.SCISSORS to mapOf(
        Move.ROCK to 0,
        Move.PAPER to 6,
        Move.SCISSORS to 3
    )
)
