package g171.edalabtools.inspector

import kotlin.test.Test


internal class InspectorTest {

    @Test
    fun `Main with an specified class file`() {
        val testFilePath = javaClass.getResource("/LabTests/class/LabTests.class").path
        Inspector.main(arrayOf(testFilePath))
    }
}