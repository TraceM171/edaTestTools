package eda.testtools.util;

import java.io.File;

/**
 *
 * @author trace
 */
public class FileUtils {

    public static boolean isValidFile(String filePath) {
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }

    public static File findFirstRegularFile(File node) {
        if (node.isFile()) {
            return node;
        } else {
            File[] subNodes = node.listFiles();
            return subNodes.length > 0
                    ? findFirstRegularFile(subNodes[0]) : null;
        }
    }

    public static File findFileWithSuffix(String where, String name) {
        File res = null;
        File whereFile = new File(where);
        if (whereFile.isFile()) {
            res = whereFile;
        } else {
            String[] subNodes = whereFile.list();
            for (int i = 0; i < subNodes.length && res == null; i++) {
                File af = new File(where, subNodes[i]);
                if (af.getName().endsWith(name)) {
                    res = af;
                }
            }
        }
        return res;
    }
}
