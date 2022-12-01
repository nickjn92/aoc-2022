package day5

import readInput
import java.awt.Point

fun main() {
    fun parseInput(input: List<String>): List<Pair<Point, Point>> {
        return input.asSequence().map { it.split(" -> ") }
            .map { it.map { it.split(",") } }
            .flatten()
            .map { it.map { pos -> pos.toInt() } }
            .windowed(2, 2)
            .map {
                val (pos1, pos2) = it
                Pair(Point(pos1[0], pos1[1]), Point(pos2[0], pos2[1]))
            }.toList()
    }

    fun getStraightLinePoints(points: Pair<Point, Point>): List<Point> {
        val x1 = minOf(points.first.x, points.second.x)
        val x2 = maxOf(points.first.x, points.second.x)
        val y1 = minOf(points.first.y, points.second.y)
        val y2 = maxOf(points.first.y, points.second.y)
        return (x1..x2).map { x ->
            (y1..y2).map { y ->
                Point(x, y)
            }
        }.flatten()
    }

    fun getDiagonalPoints(points: Pair<Point, Point>): List<Point> {
        val minX = minOf(points.first.x, points.second.x)
        val maxX = maxOf(points.first.x, points.second.x)
        val startY = if (minX == points.first.x) points.first.y else points.second.y
        val endY = if (minX == points.first.x) points.second.y else points.first.y
        val dYFactor = if (startY > endY) -1 else 1
        return (0..(maxX - minX)).map { dX ->
            Point(minX + dX, startY + (dX * dYFactor))
        }
    }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .asSequence()
            .filter { it.first.x == it.second.x || it.first.y == it.second.y }
            .map { getStraightLinePoints(it) }
            .flatten()
            .groupBy { it }
            .mapValues { it.value.size }
            .count { it.value >= 2 }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .asSequence()
            .map {
                when (it.first.x == it.second.x || it.first.y == it.second.y) {
                    true -> getStraightLinePoints(it)
                    false -> getDiagonalPoints(it)
                }
            }
            .flatten()
            .groupBy { it }
            .mapValues { it.value.size }
            .count { it.value >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day5/input")
    println(part1(input))
    println(part2(input))
}
