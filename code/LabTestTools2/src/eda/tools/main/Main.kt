package eda.tools.main

import eda.testtools.decompiler.Decompiler
import eda.testtools.inspector.Inspector
import eda.testtools.resulteditor.ResultEditor
import java.util.Arrays

/**
 *
 * @author trace
 */
object Main {
    fun main(args: Array<String>) {
        javax.swing.SwingUtilities.invokeLater({
            if (args.size == 0) {
                invalidInput()
                System.exit(0)
            }
            val nArgs = Arrays.copyOfRange(args, 1, args!!.size)
            when (args!![0]) {
                "edit-result", "er" -> ResultEditor.init(nArgs)
                "decompile", "d" -> Decompiler.init(nArgs)
                "inspect", "i" -> Inspector.init(nArgs)
                "help", "h" -> printHelp()
                else -> invalidInput()
            }
            System.exit(0)
        })
    }

    private fun printHelp() {
        System.out.println("TODO")
    }

    private fun invalidInput() {
        System.out.println("You must specify an option")
        System.out.println("Use \"edatools help\" (or \"edatools h\") to show all avaliable options.")
    }
}