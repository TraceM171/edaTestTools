package g171.edatesttools.main

import g171.edatesttools.decompiler.Decompiler
import g171.edatesttools.resulteditor.ResultEditor
import kotlin.system.exitProcess


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        javax.swing.SwingUtilities.invokeLater {
            if (args.isEmpty()) {
                invalidInput()
                exitProcess(0)
            }
            val nArgs = args.copyOfRange(1, args.size)
            when (args[0]) {
                "edit-result", "er" -> ResultEditor.main(nArgs)
                "decompile", "d" -> Decompiler.main(nArgs)
                //"inspect", "i" -> Inspector.init(nArgs)
                "help", "h" -> printHelp()
                else -> invalidInput()
            }
            exitProcess(0)
        }
    }


    private fun printHelp() {
        println("TODO")
    }

    private fun invalidInput() {
        println("You must specify an option")
        println("Use \"edatools help\" (or \"edatools h\") to show all available options.")
    }
}