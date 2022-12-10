package day10

sealed interface Operation {
    val cycleCost: Int
    val value: Int

    data class Add(
        override val cycleCost: Int = 2,
        override val value: Int
    ) : Operation

    data class Noop(
        override val cycleCost: Int = 1,
        override val value: Int = 0
    ) : Operation
}

fun Map<Int, Int>.signalStrength(cycle: Int): Int {
    return cycle * (this[cycle] ?: this[cycle - 1] ?: 0)
}

fun List<String>.toRegistryValuesAtCycles(): Map<Int, Int> {
    var currentCycle = 1

    val registryValuesAtCycles = mutableMapOf(
        1 to 1
    )

    this.forEach {
        val op = when {
            it.startsWith("addx") -> Operation.Add(value = it.substring(5).toInt())
            else -> Operation.Noop()
        }
        val oldValue = registryValuesAtCycles[currentCycle]!!
        currentCycle += op.cycleCost
        registryValuesAtCycles[currentCycle] = oldValue + op.value
    }

    return registryValuesAtCycles
}
