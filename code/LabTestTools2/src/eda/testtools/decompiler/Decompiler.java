package eda.testtools.decompiler;

import eda.testtools.util.FileUtils;
import eda.tools.main.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.benf.cfr.reader.api.CfrDriver;

/**
 *
 * @author trace
 */
public class Decompiler {

    private static void decompile(String inPath, String outPath)
            throws IOException {
        String tmp = Files.createTempDirectory(null).toString();

        String[] inPath_ = {inPath};
        Map<String, String> options = new HashMap<>();
        options.put("outputpath", tmp);

        CfrDriver driver = new CfrDriver.Builder().withOptions(options).build();
        driver.analyse(Arrays.asList(inPath_));

        File output = FileUtils.findFirstRegularFile(new File(tmp));
        Files.move(output.toPath(), Paths.get(outPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void init(String[] args) {
        String inPath = args.length > 0 && Files.exists(Paths.get(args[0]))
                ? args[0] : "./";
        String outPath = args.length > 1
                ? args[1] : Constants.LAB_TESTS_GENERATED_JAVA_NAME;
        inPath = FileUtils.findFileWithSuffix(inPath,
                Constants.LAB_TESTS_CLASS_NAME).getPath();

        try {
            decompile(inPath, outPath);
        } catch (IOException e) {
            System.out.println("Error creating a temporal file!");
            System.exit(1);
        }
    }

}
