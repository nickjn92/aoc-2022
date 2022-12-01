package day17

import readInput
import java.awt.Point
import java.awt.Rectangle
import kotlin.math.absoluteValue
import kotlin.math.max

val TARGET_REGEX = """.*x=(\d+)..(\d+), y=-(\d+)..-(\d+)""".toRegex()

fun main() {
    fun solve(input: String): List<Probe> {
        val mr = TARGET_REGEX.matchEntire(input)!!
        val x = mr.groupValues[1].toInt()
        val y = mr.groupValues[4].toInt()
        val width = mr.groupValues[2].toInt() - x
        val height = mr.groupValues[3].toInt() - y
        val trench = Rectangle(x, y, width, height)

        val probes = (1..1000).map { vX ->
            (-1000..1000).map { vY ->
                Probe(0, 0, vX, vY)
            }
        }.flatten()

        val stepsToSim = 1000
        return probes.filter { probe ->
            for (i in 1..stepsToSim) {
                probe.simulate()
                if (probe.intersects(trench)) {
                    return@filter true
                }
            }
            return@filter false
        }
    }

    fun part1(input: String): Int {
        return solve(input)
            .sortedBy { -it.highestY() }
            .map { it.highestY() }
            .first()
    }

    fun part2(input: String): Int {
        return solve(input)
            .map { it.initialVelocity }
            .toSet()
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day17/test")
    check(part1(testInput.first()) == 45) { "Expected 45 but got ${part1(testInput.first())}" }
    check(part2(testInput.first()) == 112) { "Expected 112 but got ${part2(testInput.first())}" }

    val input = readInput("day17/input")
    println(part1(input.first()))
    println(part2(input.first()))
}

data class Probe(var x: Int, var y: Int, var vX: Int, var vY: Int) {

    val initialVelocity = Point(vX, vY)

    fun simulate() {
        x += vX
        y += vY
        vX = max(vX - 1, 0)
        vY += 1 // Gravity always pulls you down
    }

    fun highestY(): Int {
        return (initialVelocity.y.absoluteValue * (initialVelocity.y.absoluteValue - 1) / 2)
    }

    fun intersects(trench: Rectangle) =
        x >= trench.x && x <= trench.x + trench.width &&
            y >= trench.y && y <= trench.y + trench.height
}
