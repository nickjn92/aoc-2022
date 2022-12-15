package day8

import PartSolver

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        val grid = input.toGrid()

        return grid.mapIndexed { x, cols ->
            cols.mapIndexed { y, treeHeight ->
                grid.scenicScore(x, y, treeHeight)
            }.max()
        }.max()
    }

    private fun List<List<Int>>.scenicScore(x: Int, y: Int, treeHeight: Int): Int {
        return listOf(
            viewingDistance(x, y, treeHeight, -1, 0),
            viewingDistance(x, y, treeHeight, 1, 0),
            viewingDistance(x, y, treeHeight, 0, -1),
            viewingDistance(x, y, treeHeight, 0, 1)
        ).reduce { scenicScore, dist -> scenicScore * dist }
    }

    private fun List<List<Int>>.viewingDistance(x: Int, y: Int, treeHeight: Int, dX: Int, dY: Int): Int {
        var viewingDistance = 0
        var currX = x + dX
        var currY = y + dY

        while (currX >= 0 && currX <= this.lastIndex && currY >= 0 && currY <= this[x].lastIndex) {
            viewingDistance++
            if (this[currX][currY] >= treeHeight) {
                return viewingDistance
            }
            currX += dX
            currY += dY
        }

        return viewingDistance
    }
}
