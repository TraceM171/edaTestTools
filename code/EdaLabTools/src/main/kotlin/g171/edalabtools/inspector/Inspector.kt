package g171.edalabtools.inspector

import g171.edalabtools.LAB_TESTS_GENERATED_JAVA_NAME
import g171.edalabtools.LAB_TESTS_JAVA_CLASS_NAME
import g171.edalabtools.decompiler.Decompiler
import g171.edalabtools.util.FileUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Inspector : Application() {
    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/inspector.fxml"))
        val root: Parent = loader.load()
        val params = parameters.raw
        val inspectorController = (loader.getController() as InspectorController)

        val decompiled = params.run { size > 0 && (first() == "-d" || first() == "-decompiled") }
        if (decompiled) params.removeFirst()

        var filePath = if (params.size > 0 && Files.exists(Paths.get(params[0])))
            params[0]
        else
            "./"

        val inputFile =
            if (decompiled) FileUtils.findFileWithSuffix(filePath, LAB_TESTS_GENERATED_JAVA_NAME)
            else FileUtils.findFileWithSuffix(filePath, LAB_TESTS_JAVA_CLASS_NAME)
        if (inputFile == null) {
            println("Could not find a file to edit!"); exitProcess(0)
        }

        filePath = inputFile.path
        println("Using $filePath as input file.")

        val force = params.getOrNull(1) == "-f" || params.getOrNull(1) == "-force"

        inspectorController.init(
            filePath,
            if (!decompiled) {
                val decompPath = Files.createTempFile(null, null).toString()
                Decompiler.decompile(filePath, decompPath)
                decompPath
            } else filePath,
            force
        )

        stage.scene = Scene(root)
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Inspector::class.java, *args)
        }
    }
}