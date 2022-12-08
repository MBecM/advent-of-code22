package com.advent.d7

import com.advent.d7.Day7.Operation
import com.advent.d7.Day7.toOperation
import com.advent.readLinesFromFile
import java.util.*

/**
 * @author Mateusz Becker
 */
fun main() {

//    val input = Day7.exampleData()
    val input = readLinesFromFile("input_d7.txt")

    data class File(val fileName: String, val size: Long)
    data class Dir(
        val name: String,
        val parent: Dir?,
        val directories: MutableList<Dir>,
        val files: MutableList<File>,
        var size: Long
    ) {
        override fun toString(): String {
            return "DIR: ${name},  ${size}, ${files}"
//            return "DIR: ${name},  ${size}, ${files},\n $directories"
        }
    }

    fun scanLinesForDirsAndFiles(): Dir {
        var operation: Operation = Operation.CD

        var currentDir = Dir("/", null, mutableListOf(), mutableListOf(), 0)
        val rootDir = currentDir

        for (line in input) {
            val content = line.split(" ")
            if (content[0] == "${'$'}") {
                operation = content[1].toOperation()
            }
            when (operation) {
                Operation.CD -> {
                    val name = content[2]
                    if (name == "..") {
                        currentDir = currentDir.parent!!
                    } else {
                        if (name != "/") {
                            currentDir = Dir(name, currentDir, mutableListOf(), mutableListOf(), 0)
                            currentDir.parent!!.directories += currentDir
                        }
                    }
                }
                Operation.LS -> {
                    content[0].toLongOrNull()?.let { size ->
                        currentDir.files += File(content[1], size)
                    }
                }
            }
        }
        return rootDir
    }

    val dirs = PriorityQueue<Dir>(Comparator.comparing { dir -> dir.size })

    fun computeSizeInDirsRecursive(currentDir: Dir) {
        for (dir in currentDir.directories) {
            computeSizeInDirsRecursive(dir)
        }
        val filesSize = currentDir.files.fold(0L) { acc, file -> file.size + acc }
        val dirsSize = currentDir.directories.fold(0L) { acc, dir -> dir.size + acc }
        currentDir.size = dirsSize + filesSize

        dirs += currentDir
    }

    val rootDir = scanLinesForDirsAndFiles()
    computeSizeInDirsRecursive(rootDir)
    val dirs2 = PriorityQueue(dirs)

    fun part1Answer(dirs: Queue<Dir>): Long {
        var size: Long = 0
        var acc: Long = 0
        while (dirs.isNotEmpty()) {
            size = dirs.poll().size
            if (size > 100_000) {
                break
            }
            acc += size
        }
        return acc
    }

    val answer = part1Answer(dirs)
    println("Day7 part 1 answer: $answer")

    val diskSpace: Long = 70000000
    val minDiscSpace: Long = 30000000
    val toDelete = minDiscSpace - (diskSpace - rootDir.size)

    fun part2Answer(dirs: Queue<Dir>): Long {
        var size: Long
        do {
            size = dirs.poll().size
        } while (size < toDelete)

        return size
    }

    val answer2 = part2Answer(dirs2)
    println("Day7 part 2 answer: $answer2")

}


object Day7 {
    fun exampleData() = """
${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""".trimIndent()
        .split("\n")

    fun String.toOperation(): Operation {
        return Operation.values().find { it.name.lowercase() == this }!!
    }

    enum class Operation {
        LS, CD
    }

}