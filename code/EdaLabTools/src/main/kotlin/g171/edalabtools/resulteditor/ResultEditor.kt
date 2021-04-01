package g171.edalabtools.resulteditor

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.defaultLazy
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import g171.edalabtools.RESULT_EDITOR_DELIMITER
import java.io.*
import java.time.Instant


class ResultEditor : CliktCommand(
    name = "edit-result",
    help = """
        View and edit the values stored inside a test results file, .LabTests files.
        You can then save the changes or exit without saving.
        
        If you want to learn about .LabTests file format it is recommended to decompile
        or inspect a LabTests.class file. 
        
        IMPORTANT: Making changes will also update the \"last modification\" date and time of the file.
        To preserve result validity, you must be careful and only edit values between the delimiters,
        NEVER delete or add any delimiter apart from those already included
    """.trimIndent()
) {

    private val inFile: File by argument(
        name = "input-file",
        help = "Test results file you want to edit, should end with .LabTests"
    ).file(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)

    private val outFile: File by argument(
        name = "output-file",
        help = "File to save the edited test results file, if not specified will be the same as the input file, overriding it"
    ).file(mustExist = true, canBeFile = true, canBeDir = false, mustBeWritable = true)
        .defaultLazy { inFile }

    private val delimiter: String by option(
        "-d", "--delimiter",
        help = """
            Change the default delimiter used to divide test results values, to a custom one.
            Delimiter MUST be unique, if the test results file already contains the delimiter, editing it will fail,
            use this option only if the default delimiter is not working for an specific case
        """.trimIndent()
    ).default(RESULT_EDITOR_DELIMITER)
        .validate { if (it != RESULT_EDITOR_DELIMITER) issueMessage("Default delimiter changed, use only if necessary") }

    override fun run(): Unit =
        getObjsFromFile(inFile).let { objs ->
            TermUi.editText(objsToString(objs), requireSave = true)?.let { edited ->
                updateObjsFromString(objs, edited)
                writeObjsToFile(objs, outFile)
                echo("Changes saved")
            } ?: echo("Exiting without saving")
        }

    private fun getObjsFromFile(f: File): MutableList<Any> {
        val objs = ArrayList<Any>()
        val fis = FileInputStream(f)
        ObjectInputStream(fis).use { ois ->
            var i = 0
            while (fis.available() > 0) {
                objs.add(ois.readObject())
                i++
            }
        }
        return objs
    }

    private fun objsToString(objs: List<Any>): String {
        val builder = StringBuilder()
        objs.forEach { obj -> builder.apply { append(obj); append(delimiter) } }
        return builder.toString()
    }

    private fun updateObjsFromString(ref: MutableList<Any>, text: String): Unit =
        text.split(delimiter).let {
            ref.forEachIndexed { i, _ ->
                ref[i] =
                    when (ref[i]) {
                        is String -> it[i]
                        is Double -> it[i].toDouble()
                        is Int -> Integer.valueOf(it[i])
                        is Instant -> Instant.parse(it[i])
                        else -> println("ERROR!")
                    }
            }
        }

    private fun writeObjsToFile(objs: List<Any>, f: File): Unit =
        ObjectOutputStream(FileOutputStream(f)).use { oos ->
            objs.forEach { obj -> oos.writeObject(obj) }
        }
}
