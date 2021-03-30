package g171.edalabtools.resulteditor

import org.junit.Test

internal class ResultEditorTest {

    @Test
    fun `Main with an specified result file`() {
        val testFilePath = javaClass.getResource("/LabTests/results/Practica.LabTests").path
        ResultEditor.main(arrayOf(testFilePath))
    }
}