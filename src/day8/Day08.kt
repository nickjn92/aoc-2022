package day8

import readInput
import java.util.stream.Collectors

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split(" | ")[1] }
            .map { it.split(" ") }
            .flatten()
            .count { it.hasLength(2, 3, 4, 7) }
    }

    fun part2(input: List<String>): Int {
        return input.stream()
            .map { line -> line.split(" | ").map { it.split(" ") } }
            .map { createMappings(it[0]) to it[1] }
            .map { it.first to it.second.map { scrambledNbr -> scrambledNbr.split("").sorted().joinToString("") } }
            .map {
                it.second.map { scrambledNbr ->
                    it.first[scrambledNbr]
                }
            }
            .map { it.joinToString("") }
            .collect(Collectors.toList())
            .sumOf { it.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day8/test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day8/input")
    println(part1(input))
    println(part2(input))
}

private fun String.hasLength(vararg lengths: Int): Boolean {
    return lengths.any { this.length == it }
}

private fun createMappings(input: List<String>): Map<String, String> {
    /*
     *    AAAA
     *   B    C
     *   B    C
     *    DDDD
     *   E    F
     *   E    F
     *    GGGG
     */

    val one = input.first { it.length == 2 }
    val four = input.first { it.length == 4 }
    val seven = input.first { it.length == 3 }
    val eight = input.first { it.length == 7 }

    val segA = (seven.toSet() - one.toSet()).first()
    val segC = input.filter { it.length == 6 }
        .map { seven.toSet() - it.toSet() }
        .flatten()
        .first { it.isLetter() }
    val segF = (seven.toSet() - segA - segC).first()
    val segG = input.filter { it.length == 6 }
        .map { it.toSet() - (four.toSet() + segA) }
        .first { it.size == 1 }
        .first()
    val segD = input.filter { it.length == 5 }
        .map { it.toSet() - segA - segC - segF - segG }
        .first { it.size == 1 }
        .first()
    val segE = (eight.toSet() - four.toSet() - segA - segG).first()
    val segB = (eight.toSet() - segA - segC - segD - segE - segF - segG).first()

    val scrambled0 = String(charArrayOf(segA, segB, segC, segE, segF, segG).sortedArray())
    val scrambled1 = String(charArrayOf(segC, segF).sortedArray())
    val scrambled2 = String(charArrayOf(segA, segC, segD, segE, segG).sortedArray())
    val scrambled3 = String(charArrayOf(segA, segC, segD, segF, segG).sortedArray())
    val scrambled4 = String(charArrayOf(segB, segC, segD, segF).sortedArray())
    val scrambled5 = String(charArrayOf(segA, segB, segD, segF, segG).sortedArray())
    val scrambled6 = String(charArrayOf(segA, segB, segD, segE, segF, segG).sortedArray())
    val scrambled7 = String(charArrayOf(segA, segC, segF).sortedArray())
    val scrambled8 = String(charArrayOf(segA, segB, segC, segD, segE, segF, segG).sortedArray())
    val scrambled9 = String(charArrayOf(segA, segB, segC, segD, segF, segG).sortedArray())

    return mapOf(
        scrambled0 to "0",
        scrambled1 to "1",
        scrambled2 to "2",
        scrambled3 to "3",
        scrambled4 to "4",
        scrambled5 to "5",
        scrambled6 to "6",
        scrambled7 to "7",
        scrambled8 to "8",
        scrambled9 to "9"
    )
}
