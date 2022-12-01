package day7

import readInput
import kotlin.math.abs

fun main() {
    fun positions(input: List<String>): List<Int> {
        return input.map { it.split(",") }
            .flatten()
            .map { it.toInt() }
    }

    fun sortedPositions(input: List<String>): List<Int> {
        return positions(input).sorted()
    }

    fun part1(input: List<String>): Int {
        val sortedPositions = sortedPositions(input)

        val median = when (sortedPositions.size % 2) {
            0 -> (sortedPositions[sortedPositions.size / 2 - 1] + sortedPositions[sortedPositions.size / 2]) / 2
            else -> sortedPositions[sortedPositions.size / 2]
        }

        return sortedPositions.sumOf { abs(it - median) }
    }

    fun calculateCost(fromPos: Int, positions: List<Int>): Int {
        return positions
            .map { abs(it - fromPos) }
            .sumOf { steps -> (1..steps).sumOf { stepCost -> stepCost } }
    }

    fun part2(input: List<String>): Int {
        val positions = positions(input)
        val min = positions.minOf { it }
        val max = positions.maxOf { it }

        return (min..max).map { calculateCost(it, positions) }
            .minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("day7/input")
    println(part1(input))
    println(part2(input))
}
