package day11

import readInputAsListOfInts
import java.awt.Point

fun main() {
    fun step(mutableInput: MutableList<MutableList<Int>>): Int {
        val flashIndices = mutableListOf<Point>()

        (0 until mutableInput.size).map { y ->
            (0 until mutableInput[y].size).map { x ->
                mutableInput[y][x]++
                if (mutableInput[y][x] > 9) {
                    flashIndices += Point(x, y)
                }
            }
        }

        var currentLastFlashIdx = 0
        while (currentLastFlashIdx < flashIndices.size) {
            val flashPoint = flashIndices[currentLastFlashIdx]
            val neighbourIndices = getNeighbourIndices(flashPoint.x, flashPoint.y, mutableInput[0].size, mutableInput.size)
            neighbourIndices.forEach {
                mutableInput[it.y][it.x]++
                if (mutableInput[it.y][it.x] > 9 && !flashIndices.contains(it)) {
                    flashIndices += Point(it.x, it.y)
                }
            }
            currentLastFlashIdx++
        }

        flashIndices.forEach {
            mutableInput[it.y][it.x] = 0
        }

        return flashIndices.size
    }

    fun part1(input: List<List<Int>>, simSteps: Int): Int {
        val mutableInput = input.map { it.toMutableList() }
            .toMutableList()

        return (1..simSteps).sumOf { step(mutableInput) }
    }

    fun part2(input: List<List<Int>>): Int {
        val mutableInput = input.map { it.toMutableList() }
            .toMutableList()

        val expectedFlashes = mutableInput.size * mutableInput[0].size // assuming square
        var steps = 0
        do {
            steps++
        } while (step(mutableInput) != expectedFlashes)
        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsListOfInts("day11/test")
    check(part1(testInput, 100) == 1656)
    check(part2(testInput) == 195)

    val input = readInputAsListOfInts("day11/input")
    println(part1(input, 100))
    println(part2(input))
}

private fun getNeighbourIndices(idxX: Int, idxY: Int, maxX: Int, maxY: Int): List<Point> {
    return (idxX - 1..idxX + 1).map { x ->
        (idxY - 1..idxY + 1).map { y ->
            if (x == idxX && y == idxY) {
                null
            } else if (x < 0 || y < 0 || x >= maxX || y >= maxY) {
                null
            } else {
                Point(x, y)
            }
        }
    }
        .flatten().filterNotNull()
}
