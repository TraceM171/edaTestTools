package g171.edalabtools.decompiler

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import g171.edalabtools.util.FileUtils
import org.benf.cfr.reader.api.CfrDriver
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class Decompiler : CliktCommand(
    name = "decompile",
    help = "Decompile a .class file and save it to a .java file"
) {
    private val inFile: File by argument(
        name = "input-file",
        help = "File to decompile, should end with .class"
    ).file(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)

    private val outFile: File by argument(
        name = "output-file",
        help = "File where the decompiled code will be saved to, should end with .java"
    ).file(mustExist = false, canBeFile = true, canBeDir = false)

    init {
        context {
            helpFormatter = CliktHelpFormatter(
                showDefaultValues = true,
                showRequiredTag = true
            )
        }
    }

    override fun run() {
        decompile(inFile.path, outFile.path)
    }
}

fun decompile(inPath: String, outPath: String) {
    val tmp = Files.createTempDirectory(null).toString()

    CfrDriver.Builder()
        .withOptions(mapOf("outputpath" to tmp)).build()
        .analyse(listOf(inPath))

    val output = FileUtils.findFirstRegularFile(File(tmp))
    output?.toPath()?.let { Files.move(it, Paths.get(outPath), StandardCopyOption.REPLACE_EXISTING) }
}