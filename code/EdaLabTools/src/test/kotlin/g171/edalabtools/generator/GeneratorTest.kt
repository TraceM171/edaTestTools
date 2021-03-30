package g171.edalabtools.generator

import g171.edalabtools.model.LabTestInfo
import g171.edalabtools.resulteditor.ResultEditor
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GeneratorTest {

    lateinit var generator: Generator
    lateinit var outputFile: File

    @BeforeTest
    fun setUp() {
        outputFile = File.createTempFile("labTest", null)
        generator = Generator(
            LabTestInfo(
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
                path = "./aplicaciones/primitiva/Practica.LabTests",
                absPath = File("./aplicaciones/primitiva/Practica.LabTests").absolutePath
            )
        )
    }

    @Test
    fun `is Generator output correct`() {
        val expected = File(javaClass.getResource("/LabTests/results/Practica.LabTests").path).readText()
        generator.generate(outputFile)
        //ResultEditor.main(arrayOf(outputFile.path))
        assertEquals(expected, outputFile.readText())
    }
}