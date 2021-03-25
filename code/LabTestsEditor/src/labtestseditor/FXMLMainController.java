package labtestseditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author trace
 */
public class FXMLMainController implements Initializable {

    private static final int MAX_OBJ_LENGTH = 100;
    private static final String DELIMITER = "‖";

    private static Object[] getObjsFromFile(File f)
            throws IOException, ClassNotFoundException {

        Object[] objs = new Object[MAX_OBJ_LENGTH];
        FileInputStream fis = new FileInputStream(f);
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            for (int i = 0; fis.available() > 0; i++) {
                objs[i] = ois.readObject();
            }
        }
        return objs;

    }

    private static String objsToString(Object[] objs) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < objs.length && objs[i] != null; i++) {
            builder.append(objs[i]);
            builder.append(DELIMITER);
        }
        return builder.toString();

    }

    private static void updateObjsFromString(Object[] ref, String text) {
        String[] splitted = text.split(DELIMITER);
        for (int i = 0; i < ref.length && ref[i] != null; i++) {
            Object nO = null;
            switch (ref[i].getClass().getName()) {
                case "java.lang.String":
                    nO = splitted[i];
                    break;
                case "java.lang.Double":
                    nO = Double.valueOf(splitted[i]);
                    break;
                case "java.lang.Integer":
                    nO = Integer.valueOf(splitted[i]);
                    break;
                case "java.time.Instant":
                    nO = Instant.parse(splitted[i]);
                    break;
                default:
                    System.out.println("ERROR!");
            }
            if (nO != null && !ref[i].equals(nO)) {
                ref[i] = nO;
            }
        }
    }

    private static void writeObjsToFile(Object[] objs, File f)
            throws IOException, FileNotFoundException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));) {
            for (int i = 0; i < objs.length && objs[i] != null; i++) {
                oos.writeObject(objs[i]);
            }
        }
    }

    private String INPUT_FILE_PATH = null;

    private Object[] objs;
    private File inputFile;

    @FXML
    private TextArea text_box;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            inputFile = new File(INPUT_FILE_PATH);
            loadFileToTextArea();
        });

    }

    void setFilePath(String filePath) {
        INPUT_FILE_PATH = filePath;
    }

    @FXML
    private void saveAction(ActionEvent event) {
        updateObjsFromString(objs, text_box.getText());
        try {
            writeObjsToFile(objs, inputFile);
        } catch (IOException ex) {
             System.err.println("Cannot write file at specified path!");
            exit();
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        exit();
    }

    @FXML
    private void restoreAction(ActionEvent event) {
        loadFileToTextArea();
    }
    
    private void loadFileToTextArea() {
        try {
            objs = getObjsFromFile(inputFile);
            text_box.setText(objsToString(objs));
        } catch (IOException e) {
            System.err.println("Cannot read file at specified path!");
            exit();
        } catch (ClassNotFoundException e) {
            System.err.println("Specified file is not valid!");
            exit();
        }
    }
    
    private static void exit() {
        Platform.exit();
        System.exit(0);
    }

}
