package g171.edatesttools.decompiler

import g171.edatesttools.main.LAB_TESTS_CLASS_NAME
import g171.edatesttools.main.LAB_TESTS_GENERATED_JAVA_NAME
import g171.edatesttools.util.FileUtils
import g171.edatesttools.util.FileUtils.findFileWithSuffix
import org.benf.cfr.reader.api.CfrDriver
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.system.exitProcess

object Decompiler {
    fun decompile(inPath: String, outPath: String) {
        val tmp = Files.createTempDirectory(null).toString()
        CfrDriver.Builder()
            .withOptions(mapOf("outputpath" to tmp)).build()
            .analyse(listOf(inPath))
        val output = FileUtils.findFirstRegularFile(File(tmp))
        output?.toPath()?.let { Files.move(it, Paths.get(outPath), StandardCopyOption.REPLACE_EXISTING) }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var inPath = if (args.isNotEmpty() && Files.exists(Paths.get(args[0]))) args[0] else "./"
        val inputFile = findFileWithSuffix(inPath, LAB_TESTS_CLASS_NAME)
        if (inputFile == null) {
            println("Could not find a file to decompile!"); exitProcess(0)
        }
        inPath = inputFile.path
        println("Using $inPath as input file.")
        val outPath = if (args.size > 1) args[1] else LAB_TESTS_GENERATED_JAVA_NAME
        println("Using $outPath as output file.")
        try {
            decompile(inPath, outPath)
        } catch (e: IOException) {
            println("Error writing to file!")
            exitProcess(1)
        }
    }
}