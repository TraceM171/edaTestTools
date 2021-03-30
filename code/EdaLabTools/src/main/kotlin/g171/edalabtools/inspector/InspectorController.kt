package g171.edalabtools.inspector

import g171.edalabtools.util.Extractor
import g171.edalabtools.util.SysUtils
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.text.Text
import java.io.File
import java.net.URL
import java.util.ResourceBundle
import kotlin.properties.Delegates

class InspectorController : Initializable {
    private lateinit var originalFilePath: String
    private lateinit var inputFilePath: String
    private var force by Delegates.notNull<Boolean>()


    @FXML private lateinit var infoText: Text
    @FXML private lateinit var pathLabel: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) = Platform.runLater {
        val labTestInfo = Extractor(inputFilePath, force).extract()

        infoText.text = labTestInfo.toFormattedString()
        pathLabel.text = originalFilePath
    }

    @FXML
    private fun cancelAction() = SysUtils.exitFX(0)

    internal fun init(original: String, input: String, force: Boolean) {
        originalFilePath = original
        inputFilePath = input
        this.force = force
    }
}