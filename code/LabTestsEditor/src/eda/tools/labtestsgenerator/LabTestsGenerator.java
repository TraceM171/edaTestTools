package eda.tools.labtestsgenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.benf.cfr.reader.api.CfrDriver;

/**
 *
 * @author trace
 */
public class LabTestsGenerator {

    public static void init(String[] args) {
        decompile(args[0], args[1]);
    }

    private static void decompile(String inPath, String outPath) {
        String[] inPath_ = {inPath};
        Map<String, String> options = new HashMap<>();
        options.put("outputpath", outPath);
        options.put("clobber", "true");
        
        CfrDriver driver = new CfrDriver.Builder().withOptions(options).build();
        driver.analyse(Arrays.asList(inPath_));
    }

}
