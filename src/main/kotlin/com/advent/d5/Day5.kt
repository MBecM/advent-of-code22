package com.advent.d5

import com.advent.readLinesFromFile
import java.util.*

/**
 * @author Mateusz Becker
 */
fun main() {

//    val input = Day5.exampleData()
    val input = readLinesFromFile("input_d5.txt")

    val cargoInit = input.takeWhile { it.isNotEmpty() }
    val procInit = input.dropWhile { it.isNotEmpty() }.drop(1)

    val stackAmount = cargoInit.last().replace(" ", "").length
    val cargoStacksPart1 = (1..stackAmount).map<Int, Deque<String>> { LinkedList() }

    val maxCargoIndex = 4 * (cargoStacksPart1.size - 1)
    for (line in cargoInit.dropLast(1)) {
        for (index in 0..maxCargoIndex step 4) {
            if (line[index] == '[') {
                cargoStacksPart1[index / 4].offerLast(line.substring(index..index + 2))
            }
        }
    }
    val cargoStacksPart2 = cargoStacksPart1.map<Deque<String>, Deque<String>> { LinkedList(it) }

    for (proc in procInit) {
        val procParams = proc.split(" ")
            .filter { it.matches("\\d+".toRegex()) }

        val move = procParams[0].toInt()
        val from = procParams[1].toInt() - 1
        val to = procParams[2].toInt() - 1

        //part 1
        for (i in 0 until move) {
            cargoStacksPart1[to].addFirst(cargoStacksPart1[from].removeFirst())
        }

        //part 2
        if (move > 1) {
            val tempStack = LinkedList<String>()
            for (i in 0 until move) {
                tempStack.offerLast(cargoStacksPart2[from].removeFirst())
            }
            for (i in 0 until move) {
                cargoStacksPart2[to].addFirst(tempStack.removeLast())
            }
        } else {
            cargoStacksPart2[to].addFirst(cargoStacksPart2[from].removeFirst())
        }
    }

    fun answer(cargoStacks: List<Deque<String>>, part: String) {
        val answer = cargoStacks.fold("") { acc, stack ->
            acc + stack.peekFirst()[1]
        }
        println("Day5 part $part answer: $answer")
    }

    answer(cargoStacksPart1,"1")
    answer(cargoStacksPart2,"2")

}


object Day5 {
    fun exampleData() = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""".trimIndent()
        .split("\n")
}