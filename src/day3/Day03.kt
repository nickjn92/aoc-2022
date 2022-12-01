package day3

import readInput

fun main() {
    fun List<String>.nbrOfOnes(): List<Int> {
        return this.joinToString("").trim()
            .split("").asSequence()
            .filter { it.isNotEmpty() }
            .withIndex()
            .groupBy { it.index % this[0].length }
            .map { it.value.sumOf { idxPair -> idxPair.value.toInt() } }
    }

    fun part1(input: List<String>): Int {
        val fullBinary = "1".repeat(input[0].length).toInt(2)

        val gammaBinaryString = input.nbrOfOnes()
            .map { it / (input.size / 2) } // clamps to 0/1
            .joinToString("") { it.toString() }

        val gamma = gammaBinaryString.toInt(2)
        val epsilon = fullBinary - gamma
        return gamma * epsilon
    }

    fun findRating(fullInput: List<String>, shouldStartWith: String, substringIdx: Int, findOxygenRating: Boolean): String {
        val filteredInput = fullInput.filter { it.startsWith(shouldStartWith) }
        val nbrOfOnes = filteredInput.nbrOfOnes()

        val condition = when (findOxygenRating) {
            true -> nbrOfOnes[substringIdx] >= (filteredInput.size.toDouble() / 2)
            false -> nbrOfOnes[substringIdx] < (filteredInput.size.toDouble() / 2)
        }

        val newShouldStartWith = shouldStartWith + if (condition) "1" else "0"

        val intersection = filteredInput.filter { it.startsWith(newShouldStartWith) }
        return when (intersection.size) {
            1 -> intersection.first()
            0 -> error("uh oh")
            else -> findRating(intersection, newShouldStartWith, substringIdx + 1, findOxygenRating)
        }
    }

    fun part2(input: List<String>): Int {
        val oxygenRating = findRating(input, "", 0, true)
        val co2Rating = findRating(input, "", 0, false)
        return oxygenRating.toInt(2) * co2Rating.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day3/test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day3/input")
    println(part1(input))
    println(part2(input))
}
