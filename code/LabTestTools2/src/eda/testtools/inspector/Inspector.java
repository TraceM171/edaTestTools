package eda.testtools.inspector;

import eda.testtools.decompiler.Decompiler;
import eda.testtools.util.FileUtils;
import eda.tools.main.Constants;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class Inspector extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
        Parent root = loader.load();
        FXMLMainController cont = loader.getController();

        final Parameters params = getParameters();
        final List<String> parameters = params.getRaw();

        boolean decompiled = parameters.size() > 0
                && (parameters.get(0).equals("-d")
                || parameters.get(0).equals("-decompiled"));

        String filePath = parameters.size() > 0
                && Files.exists(Paths.get(parameters.get(decompiled ? 1 : 0)))
                ? parameters.get(decompiled ? 1 : 0) : "./";

        File inputFile = null;
        if (decompiled) {
            inputFile = FileUtils.findFileWithSuffix(filePath,
                    Constants.LAB_TESTS_GENERATED_JAVA_NAME);
        } else {
            inputFile = FileUtils.findFileWithSuffix(filePath,
                    Constants.LAB_TESTS_CLASS_NAME);
        }

        if (inputFile == null) {
            System.out.println("Could not find a file to inspect!");
            System.exit(0);
        }
        filePath = inputFile.getPath();
        System.out.println("Using " + filePath + " as input file.");

        if (!decompiled) {
            Path decompPath = Files.createTempFile(null, null);
            Decompiler.decompile(filePath, decompPath.toString());
            cont.setFilePath(filePath, decompPath.toString());
        } else {
            cont.setFilePath(filePath, filePath);
        }

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void init(String[] args) {
        launch(args);
    }

}
