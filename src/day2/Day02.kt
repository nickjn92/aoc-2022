package day2

import readInput
import java.awt.Point

fun main() {
    fun part1(input: List<String>): Int {
        val movement = mapInput(input)
            .fold(Point(0, 0)) { mv, currMv -> Point(mv.x + currMv.first, mv.y + currMv.second) }

        return movement.x * movement.y
    }

    fun part2(input: List<String>): Int {
        val movement = mapInput(input)
            .fold(Triple(0, 0, 0)) { acc, currMv ->
                val accAim = acc.third + currMv.second
                Triple(acc.first + currMv.first, acc.second + (accAim * currMv.first), accAim)
            }

        return movement.first * movement.second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day2/input")
    println(part1(input))
    println(part2(input))
}

fun mapInput(input: List<String>): List<Pair<Int, Int>> {
    return input.map { it.split(" ") }
        .map {
            val value = if (it[0] == "up") -it[1].toInt() else it[1].toInt()
            when (it[0]) {
                "forward" -> Pair(value, 0)
                else -> Pair(0, value)
            }
        }
}
