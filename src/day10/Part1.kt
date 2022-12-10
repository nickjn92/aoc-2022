package day10

import PartSolver

class Part1 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val registryValues = input.toRegistryValuesAtCycles()

        val signalStrengths = (0..input.size * 2).mapNotNull {
            val cycle = 20 + (40 * it)
            registryValues.signalStrength(cycle)
        }
        return signalStrengths.sum()
    }
}
