package day19

import readInput
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    fun part1(input: List<String>): Int {
        val scanners = input.toScanners()
        val overlaps = scanners[0].overlaps(scanners[1])
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day19/test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("day19/input")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toScanners(): List<Scanner> {
    val scanners = mutableListOf<Scanner>()
    var data: MutableList<DataPoint> = mutableListOf()
    var lastScannerName = ""
    val itr = this.iterator()
    while (itr.hasNext()) {
        val line = itr.next()
        if (line.isNotBlank()) {
            if (line.startsWith("---")) {
                lastScannerName = line
            } else {
                val dataPoint = line.split(",").map { it.toDouble() }
                // Triple (X, Y, Z)
                data.add(DataPoint(Triple(dataPoint[0], dataPoint[1], dataPoint[2])))
            }
        } else {
            if (data.isNotEmpty()) {
                scanners.add(Scanner(lastScannerName, data))
                data = mutableListOf()
            }
        }
    }
    scanners.add(Scanner(lastScannerName, data))
    return scanners
}

data class Scanner(val name: String, val data: MutableList<DataPoint> = mutableListOf()) {

    fun overlaps(other: Scanner): Int {
        val relativeDistances = data.map { it.relativeDistances(data) }
        val otherRelativeDistances = other.data.map { it.relativeDistances(other.data) }

        val overlaps = relativeDistances.map { firstDistances ->
            otherRelativeDistances.map { otherDistances ->
                firstDistances.sumOf { if (otherDistances.contains(it)) 1 else 0.toInt() }
            }
        }.flatten()

        return overlaps.maxOf { it }
    }
}

data class DataPoint(val coordinates: Triple<Double, Double, Double>) {
    fun relativeDistances(others: List<DataPoint>): List<Double> {
        return others
            .map { it.coordinates }
            .filter { it != this.coordinates }
            .map { this.coordinates.distance(it) }
    }
}

private fun Triple<Double, Double, Double>.distance(other: Triple<Double, Double, Double>): Double {
    return sqrt(
        listOf(
            this.first - other.first,
            this.second - other.second,
            this.third - other.third
        ).sumOf { it.pow(2) }
    )
}
