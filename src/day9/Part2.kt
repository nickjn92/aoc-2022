package day9

import PartSolver

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        val knots = (1..10).map { Knot() }

        input.forEach {
            val (dir, steps) = it.split(" ")
            repeat(steps.toInt()) {
                when (dir) {
                    "L" -> knots[0].move(-1, 0)
                    "R" -> knots[0].move(1, 0)
                    "U" -> knots[0].move(0, 1)
                    "D" -> knots[0].move(0, -1)
                }
                knots.windowed(2, 1)
                    .forEach { (head, tail) ->
                        if (!tail.adjacent(head)) {
                            tail.move(head)
                        }
                    }
            }
        }

        return knots.last().visited.size
    }
}
