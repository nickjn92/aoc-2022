package day4

import readInput

fun main() {
    fun part1(input: Bingo): Int {
        val nbrIterator = input.numbers.iterator()
        var winner: Board? = null
        var currentNbr: Int? = null
        var drawnNbrs = mutableListOf<Int>()

        while (nbrIterator.hasNext() && winner == null) {
            currentNbr = nbrIterator.next()
            drawnNbrs.add(currentNbr)
            input.boards.forEach { it.markTaken(currentNbr!!) }
            winner = input.boards.find { it.isWinner() }
        }
        return winner!!.calculateUnmarkedSum(drawnNbrs) * currentNbr!!
    }

    fun part2(input: Bingo): Int {
        val nbrIterator = input.numbers.iterator()
        val winners = mutableSetOf<Board>()
        var currentNbr: Int? = null
        var lastWinningDrawnNbr: Int? = null

        while (nbrIterator.hasNext()) {
            currentNbr = nbrIterator.next()
            input.boards.forEach { it.markTaken(currentNbr!!) }

            val winningBoards = input.boards.filter { it.isWinner() }
            winners.addAll(winningBoards)

            if (winners.size == input.boards.size) {
                lastWinningDrawnNbr = currentNbr
                break
            }
        }
        return winners.last()
            .calculateUnmarkedSum(input.numbers.subList(0, input.numbers.indexOf(lastWinningDrawnNbr) + 1)) * currentNbr!!
    }

    fun parseInput(input: List<String>): Bingo {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = input.subList(2, input.size)
            .windowed(5, 6)
            .map {
                it.map { i ->
                    i.chunked(3).map { strNbr -> strNbr.trim().toInt() }
                }
            }
            .map { Board(it) }

        return Bingo(numbers, boards)
    }

    // test if implementation meets criteria from the description, like:
    check(part1(parseInput(readInput("day4/test"))) == 4512)
    check(part2(parseInput(readInput("day4/test"))) == 1924)

    println(part1(parseInput(readInput("day4/input"))))
    println(part2(parseInput(readInput("day4/input"))))
}

data class Bingo(val numbers: List<Int>, val boards: List<Board>)
data class Board(
    val numbers: List<List<Int>>,
    val colScore: MutableMap<Int, Int> = mutableMapOf(),
    val rowScore: MutableMap<Int, Int> = mutableMapOf()
) {

    fun markTaken(takenNbr: Number) {
        numbers.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIdx, value ->
                if (value == takenNbr) {
                    colScore[colIdx] = (colScore[colIdx] ?: 0) + 1
                    rowScore[rowIndex] = (rowScore[rowIndex] ?: 0) + 1
                }
            }
        }
    }

    fun calculateUnmarkedSum(drawnNbrs: List<Int>): Int {
        return numbers.sumOf { row ->
            row.sumOf { nbr -> if (drawnNbrs.contains(nbr)) 0 else nbr }
        }
    }

    fun isWinner() = isAnyColumnFull() || isAnyRowFull()

    private fun isAnyColumnFull(): Boolean {
        return colScore.any { it.value == 5 }
    }

    private fun isAnyRowFull(): Boolean {
        return rowScore.any { it.value == 5 }
    }

    override fun equals(other: Any?): Boolean {
        return numbers == (other as Board).numbers
    }

    override fun hashCode(): Int {
        return numbers.hashCode()
    }
}
