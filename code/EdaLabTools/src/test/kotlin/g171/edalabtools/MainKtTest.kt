package g171.edalabtools

import com.github.ajalt.clikt.output.TermUi.echo
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

internal class MainKtTest {

    @Test
    fun `Decompile with correct arguments`() {
        main(
            arrayOf(
                "decompile",
                javaClass.getResource("/LabTests/class/LabTests.class").path,
                "${javaClass.getResource("/LabTests/java").path}/LabTests.java"
            )
        )
        echo("${javaClass.getResource("/LabTests/java").path}/LabTests.java")
    }

    @Test
    fun `Inspect with correct arguments`() {
        main(
            arrayOf(
                "inspect",
                "-dr",
                javaClass.getResource("/LabTests/java/LabTests.java").path
            )
        )
    }

    @Test
    fun `Generate with correct arguments`() {
        val expected = File(javaClass.getResource("/LabTests/results/Practica.LabTests").path).readText()
        val outputPath = javaClass.getResource("/generated").path
        main(
            arrayOf(
                "generate",
                javaClass.getResource("/LabTests/class/LabTests.class").path,
                outputPath
            )
        )
        assertEquals(expected, File("$outputPath/Practica1.LabTests").readText())
    }
}