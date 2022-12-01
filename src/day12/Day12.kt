package day12

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val graphNodes = input.buildGraph()
        return buildPathsPart1(graphNodes["start"]!!, graphNodes["end"]!!, mutableListOf())
            .size
    }

    fun part2(input: List<String>): Int {
        val graphNodes = input.buildGraph()
        return buildPathsPart2(graphNodes["start"]!!, graphNodes["end"]!!, mutableListOf())
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day12/test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("day12/input")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.buildGraph(): Map<String, Node> {
    val nodesList = this.map { it.split("-").toList() }
    val nodes = nodesList
        .flatten()
        .associateWith { Node(it) }
    nodesList.forEach { (from, to) ->
        nodes[from]!!.addConnection(nodes[to]!!)
        nodes[to]!!.addConnection(nodes[from]!!)
    }
    return nodes
}

private fun buildPathsPart1(start: Node, end: Node, visitedSmallNodes: MutableList<Node>): List<String> {
    if (start.name[0].isLowerCase() && start.name != "end") visitedSmallNodes.add(start)
    if (start == end) return listOf(start.name)
    val connectedNodesFiltered = start.connectedNodes
        .filter { it.name[0].isUpperCase() || !visitedSmallNodes.contains(it) }

    return connectedNodesFiltered.map { buildPathsPart1(it, end, visitedSmallNodes.toMutableList()) }
        .flatten()
        .map { start.name + "," + it }
}

private fun buildPathsPart2(start: Node, end: Node, visitedSmallNodes: MutableList<Node>): List<String> {
    if (end.name == "start") return emptyList()
    if (start.name[0].isLowerCase() && start.name != "end") visitedSmallNodes.add(start)
    if (start == end) return listOf(start.name)
    val connectedNodesFiltered = start.connectedNodes
        .filter { it.name[0].isUpperCase() || !visitedSmallNodes.contains(it) || visitedSmallNodes.noDuplicates() }
        .filter { it.name != "start" }

    return connectedNodesFiltered.map { buildPathsPart2(it, end, visitedSmallNodes.toMutableList()) }
        .flatten()
        .map { start.name + "," + it }
}

private fun List<Node>.noDuplicates(): Boolean {
    return this.toSet().size == this.size
}

data class Node(val name: String, val connectedNodes: MutableList<Node> = mutableListOf()) {
    fun addConnection(node: Node) {
        connectedNodes.add(node)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
