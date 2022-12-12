package day12

import kotlin.math.abs
import kotlin.streams.toList

typealias GridPosition = Pair<Int, Int>
typealias Height = Int
typealias Path = List<GridPosition>

const val MAX_SCORE = 99999999

fun List<String>.toHeightMap() = joinToString("\n")
    .replace('S', 'a')
    .replace('E', 'z')
    .split("\n")
    .map { it.chars().toList() }

fun List<String>.getStartingPosition(): GridPosition = map { it.chars().toList() }
    .getGridPosition('S')

fun List<String>.getEndPosition(): GridPosition = map { it.chars().toList() }
    .getGridPosition('E')

fun List<List<Int>>.getGridPosition(target: Char): GridPosition {
    this.indices.forEach { y ->
        this[y].indices.forEach { x ->
            if (this[y][x] == target.code) {
                return x to y
            }
        }
    }
    error("could not find coordinate for $target")
}

open class Grid(
    private val grid: List<List<Height>>
) {

    private val validMoves = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0
    )
    private val yRange = grid.indices
    private val xRange = grid[0].indices

    open fun heuristicDistance(start: GridPosition, end: GridPosition): Int {
        val dx = abs(start.first - end.first)
        val dy = abs(start.second - end.second)
        return (dx + dy) + (-2) * minOf(dx, dy)
    }

    fun getNeighbours(position: GridPosition): List<GridPosition> {
        val height = grid[position.second][position.first]
        return validMoves.asSequence()
            .map { (dX, dY) -> (position.first + dX) to (position.second + dY) }
            .filter(::inGrid)
            .map { it to grid[it.second][it.first] }
            .filter { (_, newHeight) -> newHeight - height <= 1 }
            .map { it.first }
            .toList()
    }

    private fun inGrid(gridPosition: GridPosition): Boolean {
        return gridPosition.first in xRange && gridPosition.second in yRange
    }

    fun moveCost(currentPos: GridPosition, targetPos: GridPosition) = 1
}

fun aStarSearch(start: GridPosition, end: GridPosition, grid: Grid): Pair<Path, Int> {
    fun generatePath(currentPos: GridPosition, cameFrom: Map<GridPosition, GridPosition>): List<GridPosition> {
        val path = mutableListOf(currentPos)
        var current = currentPos
        while (cameFrom.containsKey(current)) {
            current = cameFrom.getValue(current)
            path.add(0, current)
        }
        return path.toList()
    }

    val openVertices = mutableSetOf(start)
    val closedVertices = mutableSetOf<GridPosition>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to grid.heuristicDistance(start, end))

    val cameFrom = mutableMapOf<GridPosition, GridPosition>() // Used to generate path by back tracking

    while (openVertices.size > 0) {
        val currentPos = openVertices.minBy { estimatedTotalCost.getValue(it) }

        // Check if we have reached the finish
        if (currentPos == end) {
            // Backtrack to generate the most efficient path
            val path = generatePath(currentPos, cameFrom)
            return path to estimatedTotalCost.getValue(end) // First Route to finish will be optimum route
        }

        // Mark the current vertex as closed
        openVertices.remove(currentPos)
        closedVertices.add(currentPos)

        grid.getNeighbours(currentPos)
            .filterNot { closedVertices.contains(it) } // Exclude previous visited vertices
            .forEach { neighbour ->
                val score = costFromStart.getValue(currentPos) + grid.moveCost(currentPos, neighbour)
                if (score < costFromStart.getOrDefault(neighbour, MAX_SCORE)) {
                    if (!openVertices.contains(neighbour)) {
                        openVertices.add(neighbour)
                    }
                    cameFrom[neighbour] = currentPos
                    costFromStart[neighbour] = score
                    estimatedTotalCost[neighbour] = score + grid.heuristicDistance(neighbour, end)
                }
            }
    }

    return emptyList<GridPosition>() to MAX_SCORE
}
