package day9

import java.awt.Point
import kotlin.math.abs

class Knot {
    var x: Int = 0
    var y: Int = 0

    val visited = mutableSetOf(
        Point(0, 0)
    )

    fun adjacent(other: Knot) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

    fun move(dX: Int, dY: Int) {
        x += dX
        y += dY
        visited += Point(x, y)
    }

    fun move(other: Knot) {
        val dX = (other.x - x).coerceIn(-1..1)
        val dY = (other.y - y).coerceIn(-1..1)
        move(dX, dY)
    }
}
