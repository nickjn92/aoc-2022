package day15

import PartSolver

class Part1 : PartSolver<Long> {

    override fun solve(input: List<String>, isTest: Boolean): Long {
        val grid = input.toSensorGrid()
        val y = if (isTest) 10 else 2000000
        return grid.impossibleCoordinates(y)
    }

    private fun SensorGrid.impossibleCoordinates(y: Int) = noBeaconsAtY(y) - beaconsAtY(y)
}
