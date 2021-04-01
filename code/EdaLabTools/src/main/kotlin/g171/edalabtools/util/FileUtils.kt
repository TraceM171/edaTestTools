package g171.edalabtools.util

import java.io.File

object FileUtils {

    fun findFirstRegularFile(node: File): File? =
        if (node.isFile) {
            node
        } else {
            node.listFiles()?.let { findFirstRegularFile(it[0]) }
        }

}