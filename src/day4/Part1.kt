package day4

import PartSolver

class Part1 : PartSolver<Int> {
    override fun solve(input: List<String>, isTest: Boolean): Int {
        return input.sumOf {
            val (firstRange, secondRange) = it.split(',')
                .map { rangeStr -> rangeStr.split('-') }
                .map { rangeList -> rangeList[0].toInt() until rangeList[1].toInt() + 1 }
            when {
                firstRange.all { nbr -> nbr >= secondRange.first && nbr <= secondRange.last } -> 1
                secondRange.all { nbr -> nbr >= firstRange.first && nbr <= firstRange.last } -> 1
                else -> 0
            }.toInt()
        }
    }
}
