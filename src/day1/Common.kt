package day1

fun getCalories(input: List<String>): List<Int> {
    return input.joinToString("\n")
        .split("\n\n")
        .map { it.split("\n").sumOf { nbr -> nbr.toInt() } }
}
