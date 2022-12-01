package day18

import readInput
import java.util.Stack

fun main() {
    fun part1(input: List<String>): Int {
        val snailfishNumber = input.map { it.toSnailfishNumber() }
            .reduce(SnailfishNumber::plus)
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day18/test")
    check(part1(testInput) == 4140)
    check(part2(testInput) == 0)

    val input = readInput("day18/input")
    println(part1(input))
    println(part2(input))
}

sealed class SnailfishNumber(
    var parent: SnailfishNumber? = null
) {

    abstract fun magnitude(): Int
    abstract fun reduce(): Boolean
    abstract fun addToClosestUnaryOnLeft(value: Int, comingFrom: SnailfishNumber)
    abstract fun addToClosestUnaryOnTheRight(value: Int, comingFrom: SnailfishNumber)

    fun getDepth(): Int {
        var depth = 1
        var current = this
        while (current.parent != null) {
            depth++
            current = current.parent!!
        }
        return depth
    }

    operator fun plus(other: SnailfishNumber): SnailfishNumber {
        val result = Pair(this, other)
        this.parent = result
        other.parent = result
        result.reduce()
        return result
    }

    data class Unary(var value: Int) : SnailfishNumber() {
        override fun magnitude() = value
        override fun reduce() = false

        override fun addToClosestUnaryOnLeft(value: Int, comingFrom: SnailfishNumber) {
            this.value += value
        }

        override fun addToClosestUnaryOnTheRight(value: Int, comingFrom: SnailfishNumber) {
            this.value += value
        }

        override fun toString(): String {
            return "$value"
        }

        fun split(): Pair {
            val flooredHalf = value / 2
            val newValue = Pair(Unary(flooredHalf), Unary(value - flooredHalf))
            newValue.parent = this.parent
            return newValue
        }
    }

    data class Pair(var left: SnailfishNumber, var right: SnailfishNumber) : SnailfishNumber() {

        override fun addToClosestUnaryOnLeft(value: Int, comingFrom: SnailfishNumber) {
            if (left is Unary || left !== comingFrom) {
                left.addToClosestUnaryOnLeft(value, this)
            } else {
                parent?.addToClosestUnaryOnLeft(value, this)
            }
        }

        override fun addToClosestUnaryOnTheRight(value: Int, comingFrom: SnailfishNumber) {
            if (right is Unary || right !== comingFrom) {
                right.addToClosestUnaryOnLeft(value, this)
            } else {
                parent?.addToClosestUnaryOnTheRight(value, this)
            }
        }

        override fun toString(): String {
            return "[$left,$right]"
        }

        override fun magnitude() = 3 * left.magnitude() + 2 * right.magnitude()

        override fun reduce(): Boolean {
            while (left is Pair && left.reduce()) { }

            val depth = getDepth()

            if (depth >= 4) {
                if (left is Pair) {
                    left = (left as Pair).explode()
                    return true
                }
                if (right is Pair) {
                    right = (right as Pair).explode()
                    return true
                }
            }
            if (left is Unary && (left as Unary).value >= 10) {
                left = (left as Unary).split()
                return true
            }
            if (right is Unary && (right as Unary).value >= 10) {
                right = (right as Unary).split()
                return true
            }

            while (right is Pair && right.reduce()) { }

            return false
        }

        private fun explode(): Unary {
            val childLeftValue = (left as Unary).value
            val childRightValue = (right as Unary).value
            parent?.addToClosestUnaryOnTheRight(childRightValue, this)
            parent?.addToClosestUnaryOnLeft(childLeftValue, this)
            val newValue = Unary(0)
            newValue.parent = this.parent
            return newValue
        }
    }
}

fun String.toSnailfishNumber(): SnailfishNumber {
    val stack = Stack<Char>()
    val snailfishNumber = Stack<SnailfishNumber>()
    for (c in this) {
        when (c) {
            '[' -> {
                stack.push(c)
            }
            ']' -> {
                val right = snailfishNumber.pop()
                val left = snailfishNumber.pop()
                val parent = SnailfishNumber.Pair(left, right)
                left.parent = parent
                right.parent = parent
                snailfishNumber.add(parent)
            }
            ',' -> {}
            else -> snailfishNumber.push(SnailfishNumber.Unary(c.digitToInt()))
        }
    }
    return snailfishNumber.pop()
}
