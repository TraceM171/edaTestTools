package eda.testtools.inspector;

import eda.testtools.decompiler.Decompiler;
import eda.testtools.util.SysUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author trace
 */
public class FXMLMainController implements Initializable {

    private String INPUT_FILE_PATH = null;

    @FXML
    private Text text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            try {
                Path decompPath = Files.createTempFile(null, null);
                Decompiler.decompile(INPUT_FILE_PATH, decompPath.toString());
            } catch (IOException e) {
                System.out.println("Error writing to file!");
                System.exit(1);
            }
        });

    }

    void setFilePath(String filePath) {
        INPUT_FILE_PATH = filePath;
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        SysUtils.exitFX(0);
    }

}
