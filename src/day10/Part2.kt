package day10

import PartSolver
import kotlin.math.abs

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>, isTest: Boolean): Int {
        val pixels = 40 * 6

        val spritePositionAtCycles = input.toRegistryValuesAtCycles()

        val image = (0..pixels).map { cycle ->
            val pixel = cycle % 40

            val spritePositionAtCycle = spritePositionAtCycles[cycle + 1] ?: spritePositionAtCycles[cycle]!!
            when (abs(pixel - spritePositionAtCycle) <= 1) {
                true -> "#"
                false -> "."
            }
        }

        image.windowed(40, 40).forEach {
            println(it.joinToString(""))
        }

        return 0
    }
}
