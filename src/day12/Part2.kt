package day12

import PartSolver

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val heightMap = input.toHeightMap()
        val end = input.getEndPosition()

        val grid = Grid(heightMap)

        val costsByStartingPosition = (heightMap[0].indices).flatMap { x ->
            (heightMap.indices).map { y ->
                when (heightMap[y][x] == 'a'.code) {
                    true -> x to y
                    false -> null
                }
            }
        }
            .filterNotNull()
            .map { start ->
                val (path, cost) = aStarSearch(start, end, grid)
                start to cost
            }

        return costsByStartingPosition.minByOrNull { it.second }!!.second
    }
}
