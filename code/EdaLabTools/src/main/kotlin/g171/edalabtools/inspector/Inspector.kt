package g171.edalabtools.inspector

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import g171.edalabtools.decompiler.decompile
import g171.edalabtools.util.Extractor
import java.io.File
import java.nio.file.Files

class Inspector : CliktCommand(
    name = "inspect",
    help = "Inspect the values stored inside a LabTests file, and show them"
) {

    private val inFile: File by argument(
        name = "input-file",
        help = "File to inspect, should end with .class (or a .java file id -d is activated)"
    ).file(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)

    private val decompiled: Boolean by option(
        "-d", "--decompiled",
        help = "Use an already decompiled LabTests.java file instead of a LabTests.class"
    ).flag("--compiled", default = false)

    private val force: Boolean by option(
        "-f", "--force",
        help = "Always try to finish the extraction, even when some values can't be found in the file, will ignore parse exceptions"
    ).flag("--no-force", default = false)
        .validate { if (it) issueMessage("Force flag is activated, use with caution, expect errors") }

    private val raw: Boolean by option(
        "-r", "--raw",
        help = "Show extracted information in a raw format, will show everything found without any explanation"
    ).flag("--no-force", default = false)

    init {
        context {
            helpFormatter = CliktHelpFormatter(
                showDefaultValues = true,
                showRequiredTag = true
            )
        }
    }

    override fun run() {
        val decompiledPath: String =
            if (decompiled) inFile.path
            else {
                (Files.createTempFile("inspector", null).apply {
                    decompile(inFile.path, toString())
                }).toString()
            }

        Extractor(decompiledPath, force).extract().run {
            echo(
                if (raw) toRawString()
                else toDataString()
            )
        }
    }

}