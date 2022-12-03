package com.advent.d3

import com.advent.readLinesFromFile

/**
 * @author Mateusz Becker
 */
fun main() {
//    val input = Day3.exampleData()
    val input = readLinesFromFile("input_d3.txt")

    fun commonChars(left: String, right: String) = left.asIterable().intersect(right.asIterable())
    fun findCommonCharInStrings(strings: List<String>) = strings.reduce { acc, s ->
        commonChars(acc, s).joinToString("")
    }.first()
    fun charToPriority(c: Char) = if (c.code < 97) c.code - 38 else c.code - 96

    val answer1 = input
        .map {
            it.chunked(it.length / 2)
        }
        .map(::findCommonCharInStrings)
        .sumOf(::charToPriority)

    println("Day3 part 1 answer: $answer1")

    val answer2 = input
        .chunked(3)
        .map(::findCommonCharInStrings)
        .sumOf(::charToPriority)

    println("Day3 part 2 answer: $answer2")
}


object Day3 {
    fun exampleData() = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()
        .split("\n")
}
