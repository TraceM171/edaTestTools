package eda.testtools.inspector;

import eda.testtools.decompiler.Decompiler;
import eda.testtools.util.SysUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
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

    private String originalFilePath = null;
    private String inputFilePath = null;

    @FXML
    private Text text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            try {
                
                Extractor extr = new Extractor(inputFilePath);
                extr.extract();
            } catch (IOException e) {
                System.out.println("Error writing to file!");
                System.exit(1);
            }
        });

    }

    void setFilePath(String originalFilePath, String inputFilePath) {
        this.originalFilePath = originalFilePath;
        this.inputFilePath = inputFilePath;
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        SysUtils.exitFX(0);
    }

}
