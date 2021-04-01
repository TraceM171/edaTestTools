package g171.edalabtools.util

import java.io.File

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

}