package g171.edalabtools.generator

import g171.edalabtools.model.LabTestInfo
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class Generator(private val lti: LabTestInfo) {

    internal fun generate(outputFile: File): Unit =
        lti.run {
            ObjectOutputStream(FileOutputStream(outputFile)).run {
                writeObject("$alumno $pc\n")
                writeObject("$path\n\n")
                generateAllEjerMsg().forEach {
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

    private fun generateAllEjerMsg(): ArrayList<Any> =
        lti.run {
            ArrayList<Any>().also { mensaje ->
                EJER_PRACT.forEachIndexed { index, _ ->
                    generateEjerMsg(index, mensaje)
                }
            }
        }

    private fun generateEjerMsg(ejer: Int, mensaje: ArrayList<Any>): Unit =
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