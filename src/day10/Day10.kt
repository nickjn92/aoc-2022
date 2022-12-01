package day10

import readInput
import java.util.Stack

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { it.toNavigationSubSystem().illegalPoints }
    }

    fun part2(input: List<String>): Long {
        val autocompleteScores = input.map { it.toNavigationSubSystem() }
            .filter { it.illegalPoints == 0 }
            .map { it.autocompleteScore() }
            .sorted()

        return autocompleteScores[autocompleteScores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10/test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10/input")
    println(part1(input))
    println(part2(input))
}

private fun String.toNavigationSubSystem(): NavigationSubSystem {
    val stack = Stack<String>()

    val validChunks = mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">"
    )

    val illegalEndingsToPoints = mapOf(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137
    )

    val beginnings = validChunks.map { it.key }.toSet()

    this.split("").filter { it.isNotBlank() }
        .forEach { chunk ->
            when (beginnings.contains(chunk)) {
                true -> stack.push(chunk)
                false -> {
                    val popped = stack.pop()
                    val correspondingEnding = validChunks[popped]!!
                    if (chunk != correspondingEnding) {
                        return NavigationSubSystem(stack, illegalEndingsToPoints[chunk]!!)
                    }
                }
            }
        }
    return NavigationSubSystem(stack, 0)
}

data class NavigationSubSystem(val stack: Stack<String>, val illegalPoints: Int) {

    private val autocompletePoints = mapOf(
        "(" to 1,
        "[" to 2,
        "{" to 3,
        "<" to 4
    )

    fun autocompleteScore(): Long {
        var score = 0L
        while (stack.isNotEmpty()) {
            score = (score * 5) + autocompletePoints[stack.pop()]!!
        }
        return score
    }
}
