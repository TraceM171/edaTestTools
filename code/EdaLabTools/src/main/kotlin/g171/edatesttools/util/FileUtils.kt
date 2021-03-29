package g171.edatesttools.util

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object FileUtils {

    /*fun findFirstRegularFile(node: File): File? =
        if (node.isFile) {
            node
        } else {
            node.listFiles()?.let { findFirstRegularFile(it[0]) }
        }*/

    fun findFileWithSuffix(where: String, suffix: String): File? =
        File(where).run {
            if (isFile) this
            else listFiles()?.firstOrNull { file -> file.name.endsWith(suffix) }
        }

    fun fileToString(inputFile: File): String =
        StringBuilder().apply {
            BufferedReader(FileReader(inputFile)).use { reader ->
                while (reader.ready()) {
                    append(reader.readLine())
                }
            }
        }.toString()

    fun getStrBwDelimsFromFile(inputFile: File, delim1: String, delim2: String, fromIndex: Int)
            : IndexedResult<String>? =
        fileToString(inputFile).let {
            val index = it.indexOf(delim1, fromIndex)
            if (index == -1) return null
            val s1 = it.substring(index + delim1.length)
            val index2 = s1.indexOf(delim2)
            if (index2 == -1) return null
            IndexedResult(s1.substring(0, index2), index)
        }

    fun <T> getArrayByNameFromFile(
        inputFile: File, name: String, fromIndex: Int,
        parser: (raw: String) -> T
    ): IndexedResult<List<T>>? {
        val del1 = "$name ="
        val del2 = "};"
        val ir = getStrBwDelimsFromFile(inputFile, del1, del2, fromIndex) ?: return null
        val rA = ir.result
        val raws = rA.substring(rA.indexOf("{") + 1)
            .split(",")
            .map(parser)
        return IndexedResult(raws)
    }

    data class IndexedResult<T>(
        val result: T,
        val index: Int = 0
    )
}