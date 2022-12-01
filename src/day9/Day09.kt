package day9

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.lowPointCalculations(LowPointCalculation.RISK_LEVEL)
    }

    fun part2(input: List<String>): Int {
        return input.lowPointCalculations(LowPointCalculation.BASIN_AREA)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day9/test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("day9/input")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.lowPointCalculations(calc: LowPointCalculation): Int {
    val rowLength = this[0].length
    val flattenedArray = this.map { it.split("") }
        .flatten()
        .filter { it.isNotBlank() }
        .map { it.toInt() }

    val lowPoints = flattenedArray.mapIndexed { idx, value -> getPoint(idx, value, flattenedArray, rowLength) }
        .filter { it.isLowPoint }

    return when (calc) {
        LowPointCalculation.RISK_LEVEL -> lowPoints.sumOf { it.value + 1 }
        LowPointCalculation.BASIN_AREA -> lowPoints.map { getBasinArea(it, flattenedArray, rowLength) }
            .sortedDescending()
            .subList(0, 3)
            .reduce(Int::times)
    }
}

private fun getPoint(idx: Int, value: Int, flattenedArray: List<Int>, rowLength: Int): Point {
    val neighbours = getNeighbourIndices(idx, flattenedArray, rowLength)
        .map { flattenedArray[it] }
    return if (neighbours.all { it > value }) Point(idx, value, true) else Point(idx, value, false)
}

private fun getNeighbourIndices(idx: Int, flattenedArray: List<Int>, rowLength: Int): List<Int> {
    val neighbourIndexes: MutableList<Int> = mutableListOf()
    if (idx - rowLength >= 0) neighbourIndexes.add(-rowLength)
    if (idx + rowLength < flattenedArray.size) neighbourIndexes.add(rowLength)
    if (idx % rowLength != rowLength - 1) neighbourIndexes.add(1)
    if (idx % rowLength - 1 >= 0) neighbourIndexes.add(-1)
    return neighbourIndexes.map { it + idx }
}

private fun getBasinArea(lowPoint: Point, flattenedArray: List<Int>, rowLength: Int): Int {
    val connectedIndices = mutableListOf<Int>()
    var previousSize = 0

    val eligbleNeighbouringIndices = getNeighbourIndices(lowPoint.idx, flattenedArray, rowLength)
        .filter { flattenedArray[it] < 9 }
    connectedIndices.addAll(eligbleNeighbouringIndices)
    connectedIndices.add(lowPoint.idx)

    while (previousSize != connectedIndices.size) {
        val possibleIndices = connectedIndices.subList(previousSize, connectedIndices.size)
            .map { getNeighbourIndices(it, flattenedArray, rowLength) }
            .flatten()
            .filter { flattenedArray[it] < 9 }

        previousSize = connectedIndices.size
        val indicesToAdd = possibleIndices.filter { !connectedIndices.contains(it) }
        connectedIndices.addAll(indicesToAdd)
    }

    return connectedIndices.toSet().size
}

data class Point(val idx: Int, val value: Int, val isLowPoint: Boolean)

enum class LowPointCalculation {
    RISK_LEVEL,
    BASIN_AREA
}
