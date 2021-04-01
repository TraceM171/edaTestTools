package g171.edalabtools.util

import g171.edalabtools.model.LabTestInfo
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ExtractorTest {

    private val testFilePath = javaClass.getResource("/LabTests/java/LabTests.java").path

    @Test
    fun `Extract info from a LabTests class file and return the corresponding LabTestsInfo Object`() {
        val extractor = Extractor(testFilePath, false)
        val expectedLabTestInfo = LabTestInfo(
            verb = false,
            CAS = 0,
            ENG = 1,
            turno = 0,
            lang = 0,
            TIME_OUT = 10,
            MIN_NOTA = 0.0,
            CAP = arrayOf("EDA GIA. Examen Práctica 1. Curso 2020-21.", "EDA GII - First Lab Exam. Academic Year 2019-20."),
            LIN = "========================================================",
            ALUM = arrayOf("Alumno: ", "Student: "),
            ENTREGA = arrayOf("Calificado.", "Submitted."),
            PRUEBA = arrayOf("PRUEBA ", "TEST "),
            NO_AUT = arrayOf("*** Examen fuera de plazo o no autorizado.", "*** Unauthorized access or lab exam out of time."),
            EXC_TM = arrayOf("TIEMPO TEST EXCEDIDO: ¿Bucle infinito? ", "Test Run Time Limit Exceeded: probable infinite loop. "),
            EXC = arrayOf("EXCEPCION: ", "EXCEPTION: "),
            ERR = arrayOf("ERROR: ", "ERROR: "),
            NO_METHOD = arrayOf("No existe el metodo que se quiere ejecutar.", "The method to be tested does not exist."),
            NOM_PRACT = arrayOf("Práctica ", "Lab "),
            EJER_PRACT = arrayOf(1, 1, 1),
            EJER_PUNTOS = arrayOf(5.0E-4, 0.1, 0.212),
            path = "./aplicaciones/primitiva/Practica1.LabTests",
            absPath = File("./aplicaciones/primitiva/Practica1.LabTests").absolutePath
        )
        assertEquals(expectedLabTestInfo, extractor.extract())
    }
}