package day5

import java.util.Stack

fun List<String>.getInitialCrateState(): CrateState {
    val lines = this.takeWhile { it.isNotBlank() }
        .reversed()
    val stacks = (0..lines.maxOf { it.length } / 4)
        .map { Stack<Char>() }
        .toList()
    val state = CrateState(stacks)

    lines.subList(1, lines.size)
        .forEach {
            it.windowed(4, 4, true)
                .forEachIndexed { idx, boxStr ->
                    if (boxStr.contains("[")) {
                        state.stacks[idx].push(boxStr[1])
                    }
                }
        }

    return state
}

data class CrateState(
    val stacks: List<Stack<Char>>
)
