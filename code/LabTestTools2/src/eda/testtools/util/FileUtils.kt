package eda.testtools.util

import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.lang.reflect.Array
import java.util.ArrayList
import java.util.LinkedList

/**
 *
 * @author trace
 */
object FileUtils {
    fun isValidFile(filePath: String?): Boolean {
        val f = File(filePath)
        return f!!.exists() && !f!!.isDirectory()
    }

    fun findFirstRegularFile(node: File?): File? {
        if (node!!.isFile()) {
            return node
        } else {
            val subNodes = node!!.listFiles()
            return if (subNodes!!.size > 0)
                findFirstRegularFile(subNodes!![0])
            else
                null
        }
    }

    fun findFileWithSuffix(where: String?, name: String?): File? {
        val res: File? = null
        val whereFile = File(where)
        if (whereFile!!.isFile()) {
            res = whereFile
        } else {
            val subNodes = whereFile!!.list()
            val i = 0
            while (i < subNodes!!.size && res == null) {
                val af = File(where, subNodes!![i])
                if (af!!.getName().endsWith(name)) {
                    res = af
                }
                i++
            }
        }
        return res
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun fileToString(inputFile: File?): String? {
        val contents: StringBuilder?
        BufferedReader(FileReader(inputFile)).use({ reader ->
            contents = StringBuilder()
            while (reader!!.ready()) {
                contents!!.append(reader!!.readLine())
            }
        })
        return contents!!.toString()
    }

    @Throws(IOException::class)
    fun getStrBwDelimsFromFile(
            inputFile: File?, delim1: String?, delim2: String?, fromIndex: Int): IndexedResult<String>? {
        val sContents = fileToString(inputFile)
        val index = sContents!!.indexOf(delim1, fromIndex)
        if (index == -1) {
            return null
        }
        val s1 = sContents!!.substring(index + delim1!!.length())
        if (!s1!!.contains(delim2)) {
            return null
        }
        val res = s1!!.substring(0, s1!!.indexOf(delim2))
        return IndexedResult(res, index)
// int delim2FO = 0;
// int delim1LOBefDelim2 = -1;
// while (delim2FO != -1 && (delim1LOBefDelim2 == -1 || delim1LOBefDelim2 < fromIndex)) {
// delim2FO = sContents.indexOf(delim2, delim2FO);
// if (delim2FO == -1) {
// return null;
// }
// delim1LOBefDelim2 = sContents.lastIndexOf(delim1, delim2FO);
// }
//
// return delim1LOBefDelim2 == -1
// ? null
// : new IndexedResult(sContents.substring(
// delim1LOBefDelim2 + delim1.length(), delim2FO),
// delim1LOBefDelim2);
    }

    @Throws(IOException::class)
    fun <T> getArrayByNameFromFile(
            inputFile: File?, name: String?, fromIndex: Int,
            parser: ArrayParser<T>?, clazz: Class<T>?): IndexedResult<Array<T>>? {
        val del1 = name!! + " ="
        val del2 = "};"
        val ir = getStrBwDelimsFromFile(inputFile, del1, del2, fromIndex)
        if (ir == null) {
            return null
        }
        val rA = ir!!.result
        val raws = rA!!.substring(rA!!.indexOf("{") + 1).split(",")
        val res = Array.newInstance(clazz, raws!!.size) as Array<T>
        for (i in res!!.indices) {
            res[i] = parser!!.parse(raws!![i])
        }
        return IndexedResult(res, ir!!.index)
    }

    interface ArrayParser<T> {
        fun parse(raw: String?): T?
    }

    class IndexedResult<T>(result: T?, index: Int) {
        val result: T? = null
        val index: Int = 0

        init {
            this.result = result
            this.index = index
        }
    }
}