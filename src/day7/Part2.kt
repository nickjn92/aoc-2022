package day7

import PartSolver

class Part2 : PartSolver<Int> {

    override fun solve(input: List<String>): Int {
        val directories = parseDirectories(input)

        val totalSize = 70000000
        val neededFreeSpace = 30000000
        val rootSize = directories["/"]!!.size()
        val spaceToBeFreed = neededFreeSpace - (totalSize - rootSize)

        return directories.values
            .map { it.size() }
            .filter { it > spaceToBeFreed }
            .min()
    }
}
