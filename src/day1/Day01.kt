package day1

import readInput

fun main() {
    fun getCalories(input: List<String>): List<Int> {
        return input.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n").map { nbr -> nbr.toInt() }.sum() }
    }

    fun part1(input: List<String>): Int {
        return getCalories(input).max()
    }

    fun part2(input: List<String>): Int {
        return getCalories(input).sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1/test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day1/input")
    println(part1(input))
    println(part2(input))
}

fun List<Int>.getIncreases() = this.zipWithNext().sumOf { if (it.first < it.second) 1.toInt() else 0 }
