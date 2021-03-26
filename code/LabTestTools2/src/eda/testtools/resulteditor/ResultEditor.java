package eda.testtools.resulteditor;


import eda.testtools.util.FileUtils;
import java.io.File;
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
        final String filePath = !parameters.isEmpty() ? parameters.get(0) : null;

        if (filePath == null) {
            System.out.println("You must specify a file path!");
            System.exit(0);
        }
        if (!FileUtils.isValidFile(filePath)) {
            System.err.println("Specified file is not valid!");
            System.exit(0);
        }
        cont.setFilePath(filePath);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void init(String[] args) {
        launch(args);
    }

}
