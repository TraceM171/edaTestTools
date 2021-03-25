package ipclabtestseditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final int SECRET_OBJ_LENGTH = 19;

    private static Object[] getObjsFromFile(File f)
            throws IOException, ClassNotFoundException {

        Object[] objs = new Object[SECRET_OBJ_LENGTH];
        FileInputStream fis = new FileInputStream(f);
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            for (int i = 0; i < SECRET_OBJ_LENGTH; i++) {
                objs[i] = ois.readObject();
            }
        }
        return objs;

    }

    private static String objsToString(Object[] objs) {

        StringBuilder builder = new StringBuilder();
        for (Object o : objs) {
            builder.append(o);
            builder.append("_");
        }
        return builder.toString();

    }

    private static void updateObjsFromString(Object[] ref, String text) {
        String[] splitted = text.split("_");
        for (int i = 0; i < ref.length; i++) {
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
                    System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            if (nO != null && !ref[i].equals(nO)) {
                ref[i] = nO;
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

            try {

                objs = getObjsFromFile(inputFile);
                text_box.setText(objsToString(objs));

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private void writeObjs() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(inputFile));) {
            for (Object o : objs) {
                oos.writeObject(o);
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setFilePath(String filePath) {
        INPUT_FILE_PATH = filePath;
    }

    @FXML
    private void saveAction(ActionEvent event) {
        updateObjsFromString(objs, text_box.getText());
        writeObjs();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
    }

}