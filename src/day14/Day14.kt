package day14

import readInput

fun main() {
    fun solve(input: List<String>, repeat: Int): Long {
        val polymerTemplate = input.toPolymerTemplate()

        repeat(repeat) { polymerTemplate.iteration() }

        return polymerTemplate.getCharFrequency()
            .values.sorted()
            .let { it.last() - it.first() }
    }

    fun part1(input: List<String>): Long {
        return solve(input, 10)
    }

    fun part2(input: List<String>): Long {
        return solve(input, 40)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day14/test")
    check(part1(testInput) == 1588L)
    // check(part2(testInput) == 0L)

    val input = readInput("day14/input")
    println(part1(input))
    println(part2(input))
}

class PolymerTemplate(
    private var polymerTemplate: Map<String, Long>,
    private val mappings: Map<String, Char>,
    private val lastChar: Char
) {

    fun iteration() {
        // We only care about the frequencies, so we can keep count of the new frequency of each pair of chars each iteration
        polymerTemplate = buildMap {
            polymerTemplate.forEach { (pair, freq) ->
                val newChar = mappings[pair]!!
                listOf(
                    "${pair.first()}$newChar" to freq,
                    "$newChar${pair.last()}" to freq
                ).forEach { (chars, freq) ->
                    this[chars] = this.getOrDefault(chars, 0L) + freq
                }
            }
        }
    }

    fun getCharFrequency(): Map<Char, Long> {
        return polymerTemplate
            .map { it.key.first() to it.value }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() + if (it.key == lastChar) 1 else 0 }
    }
}

private fun List<String>.toPolymerTemplate(): PolymerTemplate {
    val initialTemplate = this.first().windowed(2)
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }

    val mappings = this.drop(2)
        .map { it.split(" -> ") }
        .associate { it[0] to it[1][0] }

    return PolymerTemplate(initialTemplate, mappings, this.first().last())
}
