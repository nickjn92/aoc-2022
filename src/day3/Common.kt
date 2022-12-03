package day3

fun Char.toPriority() = when (this.isUpperCase()) {
    true -> this - 'A' + 27
    false -> this - 'a' + 1
}
