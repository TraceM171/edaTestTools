package g171.edalabtools.resulteditor

import g171.edalabtools.INSPECTOR_DELIMITER
import g171.edalabtools.util.SysUtils.exitFX
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextArea
import java.io.*
import java.net.URL
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class ResultEditorController : Initializable {
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
        objs.forEach { obj -> builder.apply { append(obj); append(INSPECTOR_DELIMITER) } }
        return builder.toString()
    }

    private fun updateObjsFromString(ref: MutableList<Any>, text: String) =
        text.split(INSPECTOR_DELIMITER).let {
            ref.forEachIndexed { i, _ ->
                ref[i] =
                    when (ref[i].javaClass.name) {
                        "java.lang.String" -> it[i]
                        "java.lang.Double" -> it[i].toDouble()
                        "java.lang.Integer" -> Integer.valueOf(it[i])
                        "java.time.Instant" -> Instant.parse(it[i])
                        else -> println("ERROR!")
                    }
            }
        }

    private fun writeObjsToFile(objs: List<Any>, f: File) =
        ObjectOutputStream(FileOutputStream(f)).use { oos ->
            objs.forEach { obj -> oos.writeObject(obj) }
        }

    private lateinit var inputFilePath: String
    private lateinit var objs: MutableList<Any>
    private lateinit var inputFile: File

    @FXML
    private lateinit var textBox: TextArea

    override fun initialize(url: URL?, rb: ResourceBundle?) = Platform.runLater {
        inputFile = File(inputFilePath)
        loadFileToTextArea()
    }

    internal fun setFilePath(filePath: String) = filePath.also { inputFilePath = it }

    @FXML
    private fun saveAction() {
        updateObjsFromString(objs, textBox.text)
        try {
            writeObjsToFile(objs, inputFile)
            println("File saved to $inputFilePath")
        } catch (e: IOException) {
            System.err.println("Cannot write file at specified path!")
            exitFX(1)
        }
    }

    @FXML
    private fun cancelAction() = exitFX(0)

    @FXML
    private fun restoreAction() = loadFileToTextArea()

    private fun loadFileToTextArea() =
        try {
            getObjsFromFile(inputFile).also {
                objs = it
                textBox.text = objsToString(it)
            }
        } catch (e: IOException) {
            System.err.println("Cannot read file at specified path!")
            exitFX(1)
        } catch (e: ClassNotFoundException) {
            System.err.println("Specified file is not valid!")
            exitFX(1)
        }
}