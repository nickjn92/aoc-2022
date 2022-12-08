package day8

import PartSolver
import day8.Visibility.NOT_VISIBLE
import day8.Visibility.VISIBLE

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val grid = input.toGrid()

        return grid.flatMapIndexed { x, cols ->
            cols.mapIndexed { y, treeHeight ->
                grid.isVisible(x, y, treeHeight)
            }
        }.sumOf { it.ordinal }
    }

    private fun List<List<Int>>.isVisible(x: Int, y: Int, treeHeight: Int): Visibility {
        return when {
            x == 0 || x == this.lastIndex -> VISIBLE
            y == 0 || y == this[x].lastIndex -> VISIBLE
            else -> {
                val visible = listOf(
                    scan(x, y, treeHeight, -1, 0),
                    scan(x, y, treeHeight, 1, 0),
                    scan(x, y, treeHeight, 0, -1),
                    scan(x, y, treeHeight, 0, 1)
                ).any { it == VISIBLE }
                return when (visible) {
                    true -> VISIBLE
                    false -> NOT_VISIBLE
                }
            }
        }
    }

    private fun List<List<Int>>.scan(x: Int, y: Int, treeHeight: Int, dX: Int, dY: Int): Visibility {
        var currX = x + dX
        var currY = y + dY

        do {
            if (this[currX][currY] >= treeHeight) {
                return NOT_VISIBLE
            }
            currX += dX
            currY += dY
        } while (currX >= 0 && currX <= this.lastIndex && currY >= 0 && currY <= this[x].lastIndex)

        return VISIBLE
    }
}
