class Solver<R>(
    private val part1: PartSolver<R>,
    private val part2: PartSolver<R>
) {
    fun verify(day: Int, expectedPart1: R, expectedPart2: R? = null) {
        val testInput = readInput("day$day/test")
        val input = readInput("day$day/input")

        expectedPart1?.let { check(part1.solve(testInput) == it) }
        expectedPart2?.let { check(part2.solve(testInput) == it) }

        println("Solution:")
        expectedPart1?.let { println("Part 1: " + part1.solve(input)) }
        expectedPart2?.let { println("Part 2: " + part2.solve(input)) }
    }
}

interface PartSolver<R> {
    fun solve(input: List<String>): R
}
