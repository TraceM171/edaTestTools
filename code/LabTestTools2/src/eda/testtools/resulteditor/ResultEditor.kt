package eda.testtools.resulteditor

import eda.testtools.util.FileUtils
import eda.tools.main.Constants
import java.nio.file.Files
import java.nio.file.Paths
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 *
 * @author trace
 */
class ResultEditor : Application() {
    override fun start(stage: Stage) {
        val loader = FXMLLoader(this::class.getResource("FXMLMain.fxml"))
        val root = loader!!.load()
        val cont = loader!!.getController()
        val params = getParameters()
        val parameters = params!!.getRaw()
        var filePath = if (parameters.size() > 0 && Files.exists(Paths.get(parameters!!.get(0))))
            parameters!!.get(0)
        else
            "./"
        val inputFile = FileUtils.findFileWithSuffix(filePath,
                Constants.LAB_TESTS_RESULT_SUFFIX)
        if (inputFile == null) {
            System.out.println("Could not find a file to edit!")
            System.exit(0)
        }
        filePath = inputFile!!.getPath()
        System.out.println("Using $filePath as input file.")
        cont!!.setFilePath(filePath)
        val scene = Scene(root)
        stage.setScene(scene)
        stage.show()
    }

    companion object {
        fun init(args: Array<String>?) {
            launch(args)
        }
    }
}