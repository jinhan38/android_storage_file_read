package com.example.android_storage_file_read

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object ReadDeviceFile {

    /**
     * Loads files in a specific path.
     */
    fun getFiles(path: String): ArrayList<File> {

        val files = File(path).listFiles()

        if (files.isNullOrEmpty()) return arrayListOf()

        return files.toCollection(ArrayList())
    }

    /**
     * Loads a file in a specific path.
     */
    fun getFileOne(path: String, fileName: String): File {
        val files = File(path).listFiles()

        if (files.isNullOrEmpty()) throw Exception("The files do not exist in the $path")

        var filePath = ""

        for (f in files) {
            if (f.name.contains(fileName)) {
                filePath = f.path
                break
            }
        }

        if (filePath.isEmpty()) throw Exception("$fileName does not exist in $path.")

        val file = File(filePath)

        if (!file.exists()) throw  Exception("File is not exists")

        return file
    }

    /**
     * Read the contents of a text file
     */
    fun readTextFile(file: File): StringBuilder {
        val fileType = file.name.toString().substring(file.name.length - 3, file.name.length)
        if (fileType != "txt") {
            throw Exception("${file.name} is not a text file")
        }
        val reader = BufferedReader(FileReader(file))
        val textBuilder = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            textBuilder.append(line)

            textBuilder.append("\n")
        }
        return textBuilder
    }
}