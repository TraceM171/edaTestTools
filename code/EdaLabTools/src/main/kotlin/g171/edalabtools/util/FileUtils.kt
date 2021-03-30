package g171.edalabtools.util

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object FileUtils {

    fun findFirstRegularFile(node: File): File? =
        if (node.isFile) {
            node
        } else {
            node.listFiles()?.let { findFirstRegularFile(it[0]) }
        }

    fun findFileWithSuffix(where: String, suffix: String): File? =
        File(where).run {
            if (isFile) this
            else listFiles()?.firstOrNull { file -> file.name.endsWith(suffix) }
        }

    // TODO: Look for a better way of implementing this (.readText())
    fun File.cToString(): String =
        StringBuilder().also {
            BufferedReader(FileReader(this)).use { reader ->
                while (reader.ready()) {
                    it.append(reader.readLine())
                }
            }
        }.toString()
}