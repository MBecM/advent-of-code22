package com.advent.d4

import com.advent.readLinesFromFile

/**
 * @author Mateusz Becker
 */
fun main() {

//    val input = Day4.exampleData()
    val input = readLinesFromFile("input_d4.txt")

    fun String.toRange(): IntRange {
        val rangeList = this.split("-")
            .map(String::toInt)
        return rangeList.first()..rangeList.last()
    }

    val ranges = input
        .map {
            it.split(",").map(String::toRange)
        }
    val answer1 = ranges
        .fold(0) { acc, l ->
            val sortedRanges = l.sortedBy { r -> r.last - r.first }
            val shorterRange = sortedRanges.first()
            val widerRange = sortedRanges.last()

            val i = if (shorterRange.first >= widerRange.first && shorterRange.last <= widerRange.last)
                1 else 0
            acc + i
        }

    println("Day3 part 1 answer: $answer1")

    val answer2 = ranges
        .fold(0) { acc, l ->
            val i = if (l.first().intersect(l.last()).isNotEmpty())
                1 else 0
            acc + i
        }

    println("Day3 part 2 answer: $answer2")

}


object Day4 {
    fun exampleData() = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
""".trimIndent()
        .split("\n")
}