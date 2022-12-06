package day6

fun solve(message: String, stepSize: Int) = message.windowedSequence(stepSize, 1)
    .indexOfFirst { it.toHashSet().size == stepSize } + stepSize
