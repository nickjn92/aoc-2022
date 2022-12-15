package day12

import PartSolver

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        val heightMap = input.toHeightMap()
        val start = input.getStartingPosition()
        val end = input.getEndPosition()

        val grid = Grid(heightMap)

        val (_, cost) = aStarSearch(start, end, grid)

        return cost
    }
}
