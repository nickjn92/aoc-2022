package day15

import PartSolver
import common.Coordinate

class Part2 : PartSolver<Long> {

    override fun solve(input: List<String>): Long {
        val grid = input.toSensorGrid()
        val b = grid.getPossibleBeaconCoordinates(4000000)
        println("Actual solution: ${b.first().tuningFrequency()}")
        return grid.getPossibleBeaconCoordinates(20).first().tuningFrequency()
    }

    private fun Coordinate.tuningFrequency() = (x * 4000000L) + y
}
