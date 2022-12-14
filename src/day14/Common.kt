package day14

import common.Coordinate
import kotlin.math.max
import kotlin.math.min

data class Cave(
    val rocks: Set<Coordinate>,
    val settledSand: MutableSet<Coordinate> = mutableSetOf()
) {

    private val startingCoordinate = Coordinate(500, 0)
    private val minY = 0
    private val maxY = rocks.maxOf { it.y }
    private val minX = rocks.minOf { it.x } - 8
    private val maxX = rocks.maxOf { it.x } + 8

    fun hasObstacle(x: Int, y: Int) =
        Coordinate(x, y).run { rocks.contains(this) || settledSand.contains(this) || y >= maxY + 2 }

    fun simulateUntilEternalFlow() {
        do {
            var oldCoord: Coordinate? = null
            var currentCoord = startingCoordinate
            while (oldCoord != currentCoord && currentCoord.y <= maxY) {
                oldCoord = currentCoord
                currentCoord = currentCoord.move(this)
            }
            settledSand.add(currentCoord)
        } while (currentCoord.y <= maxY)
    }

    fun simulateUntilBlockedFlow() {
        do {
            var oldCoord: Coordinate? = null
            var currentCoord = startingCoordinate
            while (oldCoord != currentCoord) {
                oldCoord = currentCoord
                currentCoord = currentCoord.move(this)
                if (currentCoord == startingCoordinate) {
                    break
                }
            }
            settledSand.add(currentCoord)
        } while (currentCoord != startingCoordinate)
    }

    fun draw() {
        (minY..maxY + 2).forEach { y ->
            (minX..maxX).forEach { x ->
                val coordinate = Coordinate(x, y)
                val isRock = rocks.contains(coordinate)
                val isSettledSand = settledSand.contains(coordinate)
                val sign = when {
                    coordinate == startingCoordinate -> "+"
                    isRock || coordinate.y == maxY + 2 -> "#"
                    isSettledSand -> "o"
                    else -> "."
                }
                print(sign)
            }
            println()
        }
    }
}

fun Coordinate.move(cave: Cave): Coordinate {
    return when {
        !cave.hasObstacle(x, y + 1) -> copy(x = x, y = y + 1)
        !cave.hasObstacle(x - 1, y + 1) -> copy(x = x - 1, y = y + 1)
        !cave.hasObstacle(x + 1, y + 1) -> copy(x = x + 1, y = y + 1)
        else -> this
    }
}

fun List<String>.toCave(): Cave {
    val rocks = map { it.split(" -> ") }
        .flatMap {
            it.map { coord ->
                val (x, y) = coord.split(",")
                Coordinate(x.toInt(), y.toInt())
            }.windowed(2, 1)
                .flatMap { (first, second) ->
                    val fromX = min(first.x, second.x)
                    val fromY = min(first.y, second.y)
                    val toX = max(first.x, second.x)
                    val toY = max(first.y, second.y)

                    (fromX..toX).flatMap { x ->
                        (fromY..toY).map { y -> Coordinate(x, y) }
                    }
                }
        }
    return Cave(rocks.toSet())
}
