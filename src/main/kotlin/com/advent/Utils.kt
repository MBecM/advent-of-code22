package com.advent

import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files

/**
 * @author Mateusz Becker
 */
fun readLinesFromFile(filename: String) : List<String> {
    val uri = String::javaClass.javaClass.classLoader.getResource(filename) ?: throw FileNotFoundException(filename)
    return File(uri.toURI()).readLines()
}