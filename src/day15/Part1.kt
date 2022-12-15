package day15

import PartSolver

class Part1 : PartSolver<Long> {

    override fun solve(input: List<String>): Long {
        val grid = input.toSensorGrid()
        println("Actual solution part 1: ${grid.impossibleCoordinates(2000000)}")
        return grid.impossibleCoordinates(10)
    }

    private fun SensorGrid.impossibleCoordinates(y: Int) = noBeaconsAtY(y) - beaconsAtY(y)
}
