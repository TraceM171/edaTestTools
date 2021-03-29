package g171.edatesttools.resulteditor

import g171.edatesttools.main.LAB_TESTS_RESULT_SUFFIX
import g171.edatesttools.util.FileUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class ResultEditor : Application() {
    override fun start(stage: Stage) {
        val aaa = javaClass.getResource("/result_editor.fxml")
        val loader = FXMLLoader(aaa)
        val root: Parent = loader.load()
        val params = parameters
        val parameters = params.raw
        var filePath = if (parameters.size > 0 && Files.exists(Paths.get(parameters[0])))
            parameters[0]
        else
            "./"
        val inputFile = FileUtils.findFileWithSuffix(filePath, LAB_TESTS_RESULT_SUFFIX)
        if (inputFile == null) {
            println("Could not find a file to edit!")
            exitProcess(0)
        }
        filePath = inputFile.path
        println("Using $filePath as input file.")
        (loader.getController() as FXMLMainController).setFilePath(filePath)
        stage.scene = Scene(root)
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ResultEditor::class.java, *args)
        }
    }

}