package day6

import readInput
import java.util.Collections

fun main() {
    fun simulatePopulationGrowth(input: List<String>, days: Int): Long {
        val reproducingIn = longArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

        input.map { it.split(',') }
            .flatten()
            .forEach { reproducingIn[it.toInt()]++ }

        repeat(days) {
            val currentValues = reproducingIn.toMutableList()
            Collections.rotate(currentValues, -1)
            currentValues.forEachIndexed { idx, value -> reproducingIn[idx] = value }
            reproducingIn[6] += currentValues[8]
        }

        return reproducingIn.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day6/test")

    check(simulatePopulationGrowth(testInput, 80) == 5934L)
    check(simulatePopulationGrowth(testInput, 256) == 26984457539L)

    val input = readInput("day6/input")
    println(simulatePopulationGrowth(input, 80))
    println(simulatePopulationGrowth(input, 256))
}
