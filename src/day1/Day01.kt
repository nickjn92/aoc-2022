package day1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }
            .getIncreases()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(3, 1)
            .map { it.sum() }
            .getIncreases()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1/test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day1/input")
    println(part1(input))
    println(part2(input))
}

fun List<Int>.getIncreases() = this.zipWithNext().sumOf { if (it.first < it.second) 1.toInt() else 0 }
