package com.advent.d8

import com.advent.readLinesFromFile

/**
 * @author Mateusz Becker
 */
fun main() {

//    val input = Day8.exampleData()
    val input = readLinesFromFile("input_d8.txt")

    var log = false
    fun log(s: Any) {
        if (log) {
            println(s)
        }
    }

    val rows = input.size
    val columns = input.first().length
    val trees = Array(rows) { IntArray(columns) }

    for (rowIndex in input.indices) {
        val row = input[rowIndex]
        for (colIndex in row.indices) {
            val number = row[colIndex].toString().toInt()
            trees[rowIndex][colIndex] = number
        }
    }

    val edgeTrees = 2 * rows + 2 * (columns - 2)
    log("edgeTrees = ${edgeTrees}")
    var visibleTreesInside = 0
    var maxScenicScore = 0

    fun scanVisibilityLeft(number: Int, i: Int, j: Int): Boolean {
        log("LEFT")
        for (x in j - 1 downTo 0) {
            val tree = trees[i][x]
            log(tree)
            if (tree >= number) {
                return false
            }
        }
        return true
    }

    fun scanScoreLeft(number: Int, i: Int, j: Int): Int {
        log("LEFT S")
        for (x in j - 1 downTo 0) {
            val tree = trees[i][x]
            log(tree)
            if (tree >= number) {
                log("score left: ${j - x}")
                return j - x
            }
        }
        log("score left d: ${j}")
        return j
    }

    fun scanVisibilityRight(number: Int, i: Int, j: Int): Boolean {
        log("RIGHT")
        for (x in j + 1 until columns) {
            val tree = trees[i][x]
            log(tree)
            if (tree >= number) {
                return false
            }
        }
        return true
    }

    fun scanScoreRight(number: Int, i: Int, j: Int): Int {
        log("RIGHT S")
        for (x in j + 1 until columns) {
            val tree = trees[i][x]
            log(tree)
            if (tree >= number) {
                log("score right: ${x - j}")
                return (x - j)
            }
        }
        log("score right d: ${columns - 1}")
        return columns - 1 - j
    }

    fun scanVisibilityUp(number: Int, i: Int, j: Int): Boolean {
        log("UP")
        for (y in i - 1 downTo 0) {
            val tree = trees[y][j]
            log(tree)
            if (tree >= number) {
                return false
            }
        }
        return true
    }

    fun scanScoreUp(number: Int, i: Int, j: Int): Int {
        log("UP S")
        for (y in i - 1 downTo 0) {
            val tree = trees[y][j]
            log(tree)
            if (tree >= number) {
                log("score top: ${(i - y)}")
                return (i - y)
            }
        }
        log("score top d: ${(i)}")
        return i
    }

    fun scanVisibilityDown(number: Int, i: Int, j: Int): Boolean {
        log("DOWN")
        for (y in i + 1 until rows) {
            val tree = trees[y][j]
            log(tree)
            if (tree >= number) {
                return false
            }
        }
        return true
    }

    fun scanScoreDown(number: Int, i: Int, j: Int): Int {
        log("DOWN S")
        for (y in i + 1 until rows) {
            val tree = trees[y][j]
            log(tree)
            if (tree >= number) {
                log("score down: ${(y - i)}")
                return (y - i)
            }
        }
        log("score down d: ${(rows - 1)}")
        return rows - 1 - i
    }

    for (i in 1..rows - 2) {
        for (j in 1..columns - 2) {
            val number = trees[i][j]
            // todo part1
            if (number > 0) {
                log("====== $number")
                if (scanVisibilityLeft(trees[i][j], i, j)
                    || scanVisibilityRight(trees[i][j], i, j)
                    || scanVisibilityUp(trees[i][j], i, j)
                    || scanVisibilityDown(trees[i][j], i, j)
                ) {
                    visibleTreesInside++
                }
            }
            //todo part2
            val currentScore =
                scanScoreLeft(trees[i][j], i, j) *
                        scanScoreRight(trees[i][j], i, j) *
                        scanScoreUp(trees[i][j], i, j) *
                        scanScoreDown(trees[i][j], i, j)
            log("Score: $currentScore")
            maxScenicScore = maxOf(maxScenicScore, currentScore)
            log("Max: $maxScenicScore")
        }
    }

    log("visibleTreesInside = ${visibleTreesInside}")
    val answer = edgeTrees + visibleTreesInside
    println("Day8 part 1 answer: $answer")


    val answer2 = maxScenicScore
    println("Day8 part 2 answer: $answer2")

}


object Day8 {
    fun exampleData() = """
30373
25512
65332
33549
35390
""".trimIndent()
        .split("\n")
}