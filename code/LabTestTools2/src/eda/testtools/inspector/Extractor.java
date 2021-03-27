package eda.testtools.inspector;

import eda.testtools.model.LabTestInfo;
import eda.testtools.util.FileUtils;
import eda.testtools.util.SysUtils;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author trace
 */
public class Extractor {
    
    private static final String[]
            PATH_DELIMS = {"File eixida = new File(\"", "\");"};
    private static final String EJER_PRACT_NAME = "EJER_PRACT";

    private final String inputPath;

    public Extractor(String inputPath) {
        this.inputPath = inputPath;
    }

    public LabTestInfo extract() throws IOException {
        LabTestInfo lti = new LabTestInfo();
        File inputFile = new File(inputPath);
        
        lti.alumno = SysUtils.getUserName();
        lti.pc = SysUtils.getHostName();
        lti.path = FileUtils.getStrBwDelimsFromFile(inputFile,
                 PATH_DELIMS[0], PATH_DELIMS[1], 0).result;
        lti.absPath = (new File(lti.path)).getAbsolutePath(); 
        FileUtils.ArrayParser<Integer> parser = (raw) -> Integer.parseInt(raw.replace(" ", ""));
        Integer[] epI = FileUtils.<Integer>getArrayByNameFromFile(inputFile, EJER_PRACT_NAME, 0, parser, Integer.class).result;
        int[] ep = new int[epI.length];
        for(int i = 0; i < ep.length; i++) ep[i] = epI[i];
        lti.ejer_pract = ep;
        

        return lti;
    }
    
}
