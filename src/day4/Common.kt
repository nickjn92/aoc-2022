package day4

fun Char.toPriority() = when (this.isUpperCase()) {
    true -> this - 'A' + 27
    false -> this - 'a' + 1
}

infix fun String.intersect(other: String) = toSet() intersect other.toSet()
