package g171.edalabtools.resulteditor

import g171.edalabtools.LAB_TESTS_RESULT_SUFFIX
import g171.edalabtools.util.FileUtils
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
        val loader = FXMLLoader(javaClass.getResource("/result_editor.fxml"))
        val root: Parent = loader.load()
        val params = parameters.raw
        var filePath = if (params.size > 0 && Files.exists(Paths.get(params[0])))
            params[0]
        else
            "./"
        val inputFile = FileUtils.findFileWithSuffix(filePath, LAB_TESTS_RESULT_SUFFIX)
        if (inputFile == null) {
            println("Could not find a file to edit!")
            exitProcess(0)
        }
        filePath = inputFile.path
        println("Using $filePath as input file.")
        (loader.getController() as ResultEditorController).setFilePath(filePath)
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