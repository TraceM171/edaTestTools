package eda.testtools.util;

import javafx.application.Platform;

/**
 *
 * @author trace
 */
public class SysUtils {
    public static void exitFX(int code) {
        Platform.exit();
        System.exit(code);
    }
}
