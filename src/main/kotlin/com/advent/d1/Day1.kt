package com.advent.d1

import com.advent.readLinesFromFile
import java.util.*

/**
 * @author Mateusz Becker
 */
fun main() {
//    val input = Day1.exampleData()
    val input = readLinesFromFile("input_d1.txt")

    val sortedCals = PriorityQueue<Int>(4)

    fun addAndFixSize(acc: Int) {
        sortedCals += acc
        if (sortedCals.size > 3) {
            sortedCals.poll()
        }
    }

    var acc = 0
    for (i in input) {
        if (i.isEmpty()) {
            addAndFixSize(acc)
            acc = 0
        } else {
            acc += i.toInt()
        }
    }
    addAndFixSize(acc)

    println("Day1 part 1 answer: ${sortedCals.max()}")
    println("Day1 part 2 answer: ${sortedCals.sum()}")
}

object Day1 {
    fun exampleData() = """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
""".trimIndent()
        .split("\n")
//        .map(String::toInt)
}
