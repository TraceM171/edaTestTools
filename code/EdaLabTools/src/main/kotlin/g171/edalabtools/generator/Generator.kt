package g171.edalabtools.generator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import g171.edalabtools.decompiler.decompile
import g171.edalabtools.model.LabTestInfo
import g171.edalabtools.util.Extractor
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.file.Files

class Generator : CliktCommand(
    name = "generate",
    help = """
        Generate a result file based on the values stored inside a LabTests file
        
        IMPORTANT: In the results file, the location where it was generated is saved,
        so you should be careful and choose a reasonable folder.
        NEVER generate a file from inside a BlueJ package, otherwise path will
        probably not be coherent, if you do it, edit the result afterwards.
        
        For more information about the generated file, use \"elt edit-result\"
    """.trimIndent()
) {
    private val inFile: File by argument(
        name = "input-file",
        help = "File to generate a result of, should end with .class (or a .java file if -d is activated)"
    ).file(mustExist = true, canBeFile = true, canBeDir = false, mustBeReadable = true)

    private val decompiled: Boolean by option(
        "-d", "--decompiled",
        help = "Use an already decompiled LabTests.java file instead of a LabTests.class"
    ).flag("--compiled", default = false)

    private val force: Boolean by option(
        "-f", "--force",
        help = "Always try to finish the generation, even when some values can't be found in the file, will ignore parse exceptions"
    ).flag("--no-force", default = false)
        .validate { if (it) issueMessage("Force level is activated, expect errors and a non valid result file if critical values are missing.") }

    private val outDir: File by argument(
        name = "output-dir",
        help = "Directory where the result file will be saved to, inside this directory, will be saved with the name specified in the source file. Default is working directory"
    ).file(mustExist = true, canBeFile = false, canBeDir = true)
        .default(File("./"))

    init {
        context {
            helpFormatter = CliktHelpFormatter(
                showDefaultValues = true,
                showRequiredTag = true
            )
        }
    }

    override fun run() =
        Extractor(
            if (decompiled) inFile.path
            else {
                (Files.createTempFile("generator", null).apply {
                    decompile(inFile.path, toString())
                }).toString()
            }, force
        ).extract().let {
            generate(
                it,
                File("${outDir.path}/${it.fileName}").path
            )
        }

    internal fun generate(lti: LabTestInfo, outFile: String) {
        lti.run {
            ObjectOutputStream(FileOutputStream(outFile)).run {
                writeObject("$alumno $pc\n")
                writeObject("$absPath\n\n")
                generateAllEjerMsg(lti).forEach {
                    writeObject(it)
                }
                writeObject("\n")
                writeObject("->")
                writeObject(notaLabTests)
                writeObject("<-\n")
                writeObject("\n")
                writeObject(ahora)
                writeObject("\n")
            }
        }
    }

    private fun generateAllEjerMsg(lti: LabTestInfo): ArrayList<Any> =
        lti.run {
            ArrayList<Any>().also { mensaje ->
                EJER_PRACT.forEachIndexed { index, _ ->
                    generateEjerMsg(lti, index, mensaje)
                }
            }
        }

    private fun generateEjerMsg(lti: LabTestInfo, ejer: Int, mensaje: ArrayList<Any>): Unit =
        lti.run {
            mensaje.run {
                add("${PRUEBA[lang]} ")
                add(ejer + 1)
                add(": ")
                add(EJER_PUNTOS[ejer])
                add("\n")
            }
        }
}