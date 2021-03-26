package eda.testtools.resulteditor;

import eda.testtools.util.FileUtils;
import eda.tools.main.Constants;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author trace
 */
public class ResultEditor extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
        Parent root = loader.load();
        FXMLMainController cont = loader.getController();

        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();
        
        String filePath = parameters.size() > 0
                && Files.exists(Paths.get(parameters.get(0)))
                ? parameters.get(0) : "./";
        File inputFile = FileUtils.findFileWithSuffix(filePath,
                Constants.LAB_TESTS_RESULT_SUFFIX);
        if (inputFile == null) {
            System.out.println("Could not find a file to edit!");
            System.exit(0);
        }
        filePath = inputFile.getPath();
        System.out.println("Using " + filePath + " as input file.");
        
        
        cont.setFilePath(filePath);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void init(String[] args) {
        launch(args);
    }

}
