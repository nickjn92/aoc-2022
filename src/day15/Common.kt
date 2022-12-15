package day15

import common.Coordinate
import kotlin.math.abs

fun List<String>.toSensorGrid(): SensorGrid {
    val sensors = this.map {
        val parts = it.replace(",", "").replace(":", "").split(" ")
        Sensor(
            Coordinate(parts[2].toCoordinatePart(), parts[3].toCoordinatePart()),
            Coordinate(parts[8].toCoordinatePart(), parts[9].toCoordinatePart())
        )
    }
    return SensorGrid(sensors)
}

data class SensorGrid(
    private val sensors: List<Sensor>
) {

    fun beaconsAtY(y: Int): Long = sensors.allBeacons().filter { it.y == y }.size.toLong()
    fun noBeaconsAtY(y: Int): Long {
        val minX = sensors.minOf { it.coordinate.x - it.manhattanDistance }
        val maxX = sensors.maxOf { it.coordinate.x + it.manhattanDistance }

        return (minX..maxX).sumOf { x ->
            val coord = Coordinate(x, y)
            val withinManhattanDistance = sensors.any { coord.manhattanDistance(it.coordinate) <= it.manhattanDistance }
            if (withinManhattanDistance) 1L else 0L
        }
    }

    fun getPossibleBeaconCoordinates(max: Int): List<Coordinate> {
        return emptyList<Coordinate>()
    }
}

data class Sensor(
    val coordinate: Coordinate,
    val closestBeacon: Coordinate
) {
    val manhattanDistance = coordinate.manhattanDistance(closestBeacon)
}

private fun String.toCoordinatePart() = this.substring(2).toInt()

private fun Coordinate.manhattanDistance(other: Coordinate) = manhattanDistance(x, other.x, y, other.y)
private fun manhattanDistance(firstX: Int, otherX: Int, firstY: Int, otherY: Int) =
    abs(firstX - otherX) + abs(firstY - otherY)
private fun List<Sensor>.allBeacons() = this.map { it.closestBeacon }.toHashSet()
private fun List<Sensor>.allSensors() = this.map { it.coordinate }.toHashSet()
