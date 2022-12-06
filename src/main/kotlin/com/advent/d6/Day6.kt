package com.advent.d6

import com.advent.readLinesFromFile

/**
 * @author Mateusz Becker
 */
fun main() {

//    val input = Day6.exampleData()
    val input = readLinesFromFile("input_d6.txt")
        .first()

    fun firstMarkerAfterCharacter(input: String, distinctCharsAmount: Int): Int {
        for (i in 0..input.length - distinctCharsAmount) {
            val chars = input.substring(i until i + distinctCharsAmount).toSet()
            if (chars.size == distinctCharsAmount) {
                return i + distinctCharsAmount;
            }
        }
        return -1
    }

    val answer = firstMarkerAfterCharacter(input, 4)
    val answer2 = firstMarkerAfterCharacter(input, 14)

    println("Day6 part 1 answer: $answer")
    println("Day6 part 2 answer: $answer2")
}


object Day6 {
    fun exampleData() = """
mjqjpqmgbljsphdztnvjfqwrcgsmlb
""".trimIndent()
}