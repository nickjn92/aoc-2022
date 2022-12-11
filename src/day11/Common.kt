package day11

data class Monkey(
    val idx: Int,
    val itemsWorryLevel: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisibleBy: Int,
    val testTrueDestinationIdx: Int,
    val testFalseDestinationIdx: Int
)

fun List<String>.toMonkeys(): Map<Int, Monkey> {
    return joinToString("\n")
        .split("\n\n")
        .map {
            val lines = it.split("\n")
            val idx = lines.first().substring(7, 8).toInt()
            val (startingItemsStr, operationStr, testStr, trueTestStr, falseTestStr) = lines.takeLast(5)

            val items = startingItemsStr.toItemsWorryLevelStack()
            val op = operationStr.toOperation()
            val divisibleBy = testStr.toDivisibleBy()
            val trueDestinationIdx = trueTestStr.toDestinationIdx()
            val falseDestinationIdx = falseTestStr.toDestinationIdx()

            Monkey(idx, items, op, divisibleBy, trueDestinationIdx, falseDestinationIdx)
        }.associateBy { it.idx }
}

private fun String.toItemsWorryLevelStack(): MutableList<Long> {
    return substring(this.indexOf(":") + 1).split(",")
        .map { it.trim().toLong() }
        .toMutableList()
}

private fun String.toOperationNbr(): Int? {
    return when (val ref = substring(indexOf("=") + 7).trim()) {
        "old" -> null
        else -> ref.trim().toInt()
    }
}

private fun String.toOperation(): (Long) -> Long {
    val otherValue = toOperationNbr()
    return when {
        contains("+") -> { old: Long -> old + (otherValue?.toLong() ?: old) }
        contains("*") -> { old: Long -> old * (otherValue?.toLong() ?: old) }
        else -> error("unsupported operation")
    }
}

private fun String.toDivisibleBy(): Int {
    return substring(indexOf("by") + 3).trim().toInt()
}

private fun String.toDestinationIdx() = substring(indexOf("monkey") + 7).trim().toInt()

fun playRound(monkeys: List<Monkey>, inspections: MutableMap<Int, Long>, worryLevelModifier: (Long) -> Long) {
    monkeys.forEach { monkey ->
        monkey.itemsWorryLevel.forEach { itemWorryLevel ->
            val newWorryLevel = monkey.operation.invoke(itemWorryLevel).run { worryLevelModifier.invoke(this) }

            val moveTo = when (newWorryLevel % monkey.divisibleBy == 0L) {
                true -> monkey.testTrueDestinationIdx
                false -> monkey.testFalseDestinationIdx
            }
            monkeys[moveTo].itemsWorryLevel.add(newWorryLevel)
            inspections[monkey.idx] = (inspections[monkey.idx] ?: 0) + 1
        }
        monkey.itemsWorryLevel.clear()
    }
}
