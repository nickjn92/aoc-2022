package day8

enum class Visibility {
    NOT_VISIBLE,
    VISIBLE
}

fun List<String>.toGrid() = map { line -> line.map { it.digitToInt() } }
