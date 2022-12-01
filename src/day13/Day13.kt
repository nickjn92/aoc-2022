package day13

import readInput
import java.awt.Point

fun main() {
    fun part1(input: List<String>): Int {
        val manual = input.toManual()
        return manual.fold(manual.instructions.first()).data.size
    }

    fun part2(input: List<String>): Int {
        val manual = input.toManual()
        manual.instructions.forEach(manual::fold)
        manual.printData()
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day13/test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 0)

    val input = readInput("day13/input")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toManual(): Manual {
    val foldAlongRegex = """.*(x|y)=(\d+)""".toRegex()
    val coordinateRegex = """(\d+),(\d+)""".toRegex()
    val instructions = this.filter { it.matches(foldAlongRegex) }
        .map {
            val res = foldAlongRegex.matchEntire(it)!!
            Instruction(res.groupValues[2].toInt(), Instruction.Axis.valueOf(res.groupValues[1].uppercase()))
        }
    val data = this.filter { it.matches(coordinateRegex) }
        .map {
            val res = coordinateRegex.matchEntire(it)!!
            Point(res.groupValues[1].toInt(), res.groupValues[2].toInt())
        }

    return Manual(data.toMutableSet(), instructions)
}

data class Instruction(val foldAlong: Int, val axis: Axis) {
    enum class Axis {
        X,
        Y
    }
}

data class Manual(var data: MutableSet<Point>, val instructions: List<Instruction>, var maxX: Int? = null, var maxY: Int? = null) {

    fun fold(instruction: Instruction): Manual {
        val currMaxX = maxX ?: data.maxOf { it.x }
        val currMaxY = maxY ?: data.maxOf { it.y }
        val pointsToBeFolded = data.filter {
            when (instruction.axis) {
                Instruction.Axis.X -> it.x >= instruction.foldAlong
                Instruction.Axis.Y -> it.y >= instruction.foldAlong
            }
        }

        data.removeAll(pointsToBeFolded.toSet())
        pointsToBeFolded.forEach {
            when (instruction.axis) {
                Instruction.Axis.X -> data.add(Point(currMaxX - it.x, it.y))
                Instruction.Axis.Y -> data.add(Point(it.x, currMaxY - it.y))
            }
        }

        when (instruction.axis) {
            Instruction.Axis.X -> maxX = instruction.foldAlong - 1
            Instruction.Axis.Y -> maxY = instruction.foldAlong - 1
        }
        return this
    }

    fun printData() {
        for (y in 0..maxY!!) {
            for (x in 0..maxX!!) {
                if (data.contains(Point(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}
