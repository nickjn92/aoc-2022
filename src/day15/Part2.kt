package day15

import PartSolver
import common.Coordinate

class Part2 : PartSolver<Long> {

    override fun solve(input: List<String>, isTest: Boolean): Long {
        val grid = input.toSensorGrid()
        val max = if (isTest) 20 else 4000000
        return grid.getPossibleBeaconCoordinates(max).first().tuningFrequency()
    }

    private fun Coordinate.tuningFrequency() = (x * 4000000L) + y
}
