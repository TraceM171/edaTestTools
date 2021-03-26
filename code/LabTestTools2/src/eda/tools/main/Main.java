package eda.tools.main;

import eda.testtools.decompiler.Decompiler;
import eda.testtools.resulteditor.ResultEditor;
import java.util.Arrays;

/**
 *
 * @author trace
 */
public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (args.length == 0) {
                invalidInput();
                System.exit(0);
            }

            String[] nArgs = Arrays.copyOfRange(args, 1, args.length);
            switch (args[0]) {
                case "edit-result":
                case "er":
                    ResultEditor.init(nArgs);
                    break;
                case "decompile":
                case "d":
                    Decompiler.init(nArgs);
                    break;
                case "help":
                case "h":
                    printHelp();
                    break;
                default:
                    invalidInput();
            }
            System.exit(0);
        });
    }

    private static void printHelp() {
        System.out.println("TODO");
    }

    private static void invalidInput() {
        System.out.println("You must specify an option");
        System.out.println("Use \"edatools help\" (or \"edatools h\") to show all avaliable options.");
    }
}
