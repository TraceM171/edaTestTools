package eda.testtools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public static String fileToString(File inputFile)
            throws FileNotFoundException, IOException {
        final StringBuilder contents;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            contents = new StringBuilder();
            while (reader.ready()) {
                contents.append(reader.readLine());
            }
        }
        return contents.toString();
    }

    public static IndexedResult<String> getStrBwDelimsFromFile(
            File inputFile, String delim1, String delim2, int fromIndex) throws IOException {

        String sContents = fileToString(inputFile);

        int index = sContents.indexOf(delim1, fromIndex);
        if (index == -1) {
            return null;
        }
        String s1 = sContents.substring(index + delim1.length());
        if (!s1.contains(delim2)) {
            return null;
        }
        String res = s1.substring(0, s1.indexOf(delim2));
        return new IndexedResult(res, index);

//        int delim2FO = 0;
//        int delim1LOBefDelim2 = -1;
//        while (delim2FO != -1 && (delim1LOBefDelim2 == -1 || delim1LOBefDelim2 < fromIndex)) {
//            delim2FO = sContents.indexOf(delim2, delim2FO);
//            if (delim2FO == -1) {
//                return null;
//            }
//            delim1LOBefDelim2 = sContents.lastIndexOf(delim1, delim2FO);
//        }
//
//        return delim1LOBefDelim2 == -1
//                ? null
//                : new IndexedResult(sContents.substring(
//                        delim1LOBefDelim2 + delim1.length(), delim2FO),
//                        delim1LOBefDelim2);
    }

    public static <T> IndexedResult<T[]> getArrayByNameFromFile(
            File inputFile, String name, int fromIndex,
             ArrayParser<T> parser, Class<T> clazz) throws IOException {

        String del1 = name + " =";
        String del2 = "};";
        IndexedResult<String> ir
                = getStrBwDelimsFromFile(inputFile, del1, del2, fromIndex);
        if (ir == null) {
            return null;
        }

        String rA = ir.result;
        String[] raws = rA.substring(rA.indexOf("{") + 1).split(",");
        T[] res = (T[]) Array.newInstance(clazz, raws.length);
        for (int i = 0; i < res.length; i++) {
            res[i] = parser.parse(raws[i]);
        }

        return new IndexedResult(res, ir.index);
    }

    public interface ArrayParser<T> {

        T parse(String raw);
    }

    public static class IndexedResult<T> {

        public final T result;
        public final int index;

        public IndexedResult(T result, int index) {
            this.result = result;
            this.index = index;
        }

    }
}
