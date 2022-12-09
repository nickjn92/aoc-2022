package day9

import PartSolver

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val head = Knot()
        val tail = Knot()

        input.forEach {
            val (dir, steps) = it.split(" ")
            repeat(steps.toInt()) {
                when (dir) {
                    "L" -> head.move(-1, 0)
                    "R" -> head.move(1, 0)
                    "U" -> head.move(0, 1)
                    "D" -> head.move(0, -1)
                }
                if (!tail.adjacent(head)) {
                    tail.move(head)
                }
            }
        }

        return tail.visited.size
    }
}
