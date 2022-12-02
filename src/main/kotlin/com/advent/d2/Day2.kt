package com.advent.d2

import com.advent.readLinesFromFile

/**
 * @author Mateusz Becker
 */
fun main() {
//    val input = Day2.exampleData()
    val input = readLinesFromFile("input_d2.txt")

    val answer = input.sumOf {
        val game = it.split(" ")
            .map(Day2.RPS::fromString)
        game[1].calculateScore(game[0])
    }

    println("Day2 part 1 answer: $answer")

    val answer2 = input.sumOf {
        val split = it.split(" ")
        val oponentChoice = Day2.RPS.fromString(split[0])
        val game = listOf(oponentChoice, Day2.RPS.fromExpectedResult(split[1], oponentChoice))
        game[1].calculateScore(game[0])
    }

    println("Day2 part 2 answer: $answer2")

}


object Day2 {
    fun exampleData() = """
A Y
B X
C Z
""".trimIndent()
        .split("\n")

    enum class RPS(private val matchScore: (RPS: RPS) -> Int) {
        ROCK({
            when (it) {
                ROCK -> 3 + 1
                PAPER -> 0 + 1
                SCISSORS -> 6 + 1
            }
        }),
        PAPER({
            when (it) {
                ROCK -> 6 + 2
                PAPER -> 3 + 2
                SCISSORS -> 0 + 2
            }
        }),
        SCISSORS({
            when (it) {
                ROCK -> 0 + 3
                PAPER -> 6 + 3
                SCISSORS -> 3 + 3
            }
        });

        fun calculateScore(RPS: RPS): Int = matchScore(RPS)

        companion object {

            fun fromString(s: String): RPS {
                return when (s) {
                    "A", "X" -> ROCK
                    "B", "Y" -> PAPER
                    "C", "Z" -> SCISSORS
                    else -> throw IllegalArgumentException("Bad RPC arg")
                }
            }

            fun fromExpectedResult(s: String, oponentChoice: RPS): RPS {
                return when (s) {
                    "X" -> when (oponentChoice) {
                        ROCK -> SCISSORS
                        PAPER -> ROCK
                        SCISSORS -> PAPER
                    }
                    "Y" -> when (oponentChoice) {
                        ROCK -> ROCK
                        PAPER -> PAPER
                        SCISSORS -> SCISSORS
                    }
                    "Z" -> when (oponentChoice) {
                        ROCK -> PAPER
                        PAPER -> SCISSORS
                        SCISSORS -> ROCK
                    }
                    else -> throw IllegalArgumentException("Bad match result arg")
                }
            }
        }
    }
}
