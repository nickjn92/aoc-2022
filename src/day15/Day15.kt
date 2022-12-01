package day15

import readInputAsListOfInts
import java.util.PriorityQueue

fun main() {
    fun part1(map: List<List<Int>>): Int {
        return solve(map)
    }

    fun part2(map: List<List<Int>>, repeat: Int): Int {
        val bigMap =
            (0 until repeat * map.size).map { y ->
                (0 until repeat * map[0].size).map { x ->
                    var value = (map[y % map.size][x % map[0].size] + (y / map.size) + (x / map[0].size))
                    if (value > 9) {
                        value = (value % 10) + 1
                    }
                    value
                }
            }

        return solve(bigMap)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsListOfInts("day15/test")
    check(part1(testInput) == 40)
    check(part2(testInput, 5) == 315)

    val input = readInputAsListOfInts("day15/input")
    println(part1(input))
    println(part2(input, 5))
}

private fun solve(riskLevels: List<List<Int>>): Int {
    val distances = Array(riskLevels.size) {
        Array(riskLevels.first().size) { Int.MAX_VALUE }
    }.apply { get(0)[0] = 0 }

    val toVisit = PriorityQueue<Pair<Int, Int>> { (y1, x1), (y2, x2) -> distances[y1][x1].compareTo(distances[y2][x2]) }
        .apply { add(0 to 0) }
    val visited = mutableSetOf(0 to 0)

    while (toVisit.isNotEmpty()) {
        val (y, x) = toVisit.poll().also { visited.add(it) }

        getNeighbours(y, x, riskLevels).forEach { (nY, nX) ->
            if (!visited.contains(nY to nX)) {
                val newDistance = distances[y][x] + riskLevels[nY][nX]
                if (newDistance < distances[nY][nX]) {
                    distances[nY][nX] = newDistance
                    toVisit.add(nY to nX)
                }
            }
        }
    }

    return distances[distances.lastIndex][distances.last().lastIndex]
}

private fun getNeighbours(y: Int, x: Int, riskLevels: List<List<Int>>): List<Pair<Int, Int>> {
    return arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1))
        .map { (dy, dx) -> y + dy to x + dx }
        .filter { (y, x) -> y in riskLevels.indices && x in riskLevels.first().indices }
}
