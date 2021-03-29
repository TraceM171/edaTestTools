package eda.testtools.resulteditor

import eda.testtools.util.SysUtils
import eda.tools.main.Constants
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.URL
import java.time.Instant
import java.util.ResourceBundle
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextArea

/**
 *
 * @author trace
 */
class FXMLMainController : Initializable {
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun getObjsFromFile(f: File?): Array<Object>? {
        val objs = arrayOfNulls<Object>(Constants.MAX_RESULT_OBJ_LENGTH)
        val fis = FileInputStream(f)
        ObjectInputStream(fis).use({ ois ->
            val i = 0
            while (fis!!.available() > 0) {
                objs[i] = ois!!.readObject()
                i++
            }
        })
        return objs
    }

    private fun objsToString(objs: Array<Object>?): String? {
        val builder = StringBuilder()
        val i = 0
        while (i < objs!!.size && objs!![i] != null) {
            builder!!.append(objs!![i])
            builder!!.append(Constants.INSPECTOR_DELIMITER)
            i++
        }
        return builder!!.toString()
    }

    private fun updateObjsFromString(ref: Array<Object>?, text: String?) {
        val splitted = text!!.split(Constants.INSPECTOR_DELIMITER)
        val i = 0
        while (i < ref!!.size && ref!![i] != null) {
            val nO: Object? = null
            when (ref!![i].getClass().getName()) {
                "java.lang.String" -> nO = splitted!![i]
                "java.lang.Double" -> nO = Double.valueOf(splitted!![i])
                "java.lang.Integer" -> nO = Integer.valueOf(splitted!![i])
                "java.time.Instant" -> nO = Instant.parse(splitted!![i])
                else -> System.out.println("ERROR!")
            }
            if (nO != null && !ref!![i].equals(nO)) {
                ref[i] = nO
            }
            i++
        }
    }

    @Throws(IOException::class, FileNotFoundException::class)
    private fun writeObjsToFile(objs: Array<Object>?, f: File?) {
        ObjectOutputStream(FileOutputStream(f)).use({ oos ->
            val i = 0
            while (i < objs!!.size && objs!![i] != null) {
                oos!!.writeObject(objs!![i])
                i++
            }
        })
    }

    private val INPUT_FILE_PATH: String? = null
    private val objs: Array<Object>? = null
    private val inputFile: File? = null
    @FXML
    private val text_box: TextArea? = null

    @Override
    fun initialize(url: URL?, rb: ResourceBundle?) {
        Platform.runLater({
            inputFile = File(INPUT_FILE_PATH)
            loadFileToTextArea()
        })
    }

    internal fun setFilePath(filePath: String?) {
        INPUT_FILE_PATH = filePath
    }

    @FXML
    private fun saveAction(event: ActionEvent?) {
        updateObjsFromString(objs, text_box!!.getText())
        try {
            writeObjsToFile(objs, inputFile)
        } catch (e: IOException) {
            System.err.println("Cannot write file at specified path!")
            SysUtils.exitFX(1)
        }
    }

    @FXML
    private fun cancelAction(event: ActionEvent?) {
        SysUtils.exitFX(0)
    }

    @FXML
    private fun restoreAction(event: ActionEvent?) {
        loadFileToTextArea()
    }

    private fun loadFileToTextArea() {
        try {
            objs = getObjsFromFile(inputFile)
            text_box!!.setText(objsToString(objs))
        } catch (e: IOException) {
            System.err.println("Cannot read file at specified path!")
            SysUtils.exitFX(1)
        } catch (e: ClassNotFoundException) {
            System.err.println("Specified file is not valid!")
            SysUtils.exitFX(1)
        }
    }
}