package g171.edalabtools.util

import g171.edalabtools.model.FormatException
import g171.edalabtools.model.IndexedResult
import g171.edalabtools.model.LabTestInfo
import org.apache.commons.lang.StringEscapeUtils
import java.io.File

class Extractor(private val inputPath: String, private val force: Boolean = false) {

    // Here comes the pain
    internal fun extract(): LabTestInfo =
        File(inputPath).run {
            val path = getStrBwDelimsNonNull("File eixida = new File(\"", "\");", "eixida")
            LabTestInfo(
                verb = getVarByNameNonNull("verb"),
                CAS = getVarByNameNonNull("CAS"),
                ENG = getVarByNameNonNull("ENG"),
                turno = getVarByNameNonNull("turno"),
                lang = getVarByNameNonNull("lang"),
                TIME_OUT = getVarByNameNonNull("TIME_OUT"),
                MIN_NOTA = getVarByNameNonNull("MIN_NOTA"),
                CAP = getArrayByNameNonNull("CAP"),
                LIN = getVarByNameNonNull("LIN"),
                ALUM = getArrayByNameNonNull("ALUM"),
                ENTREGA = getArrayByNameNonNull("ENTREGA"),
                PRUEBA = getArrayByNameNonNull("PRUEBA"),
                NO_AUT = getArrayByNameNonNull("NO_AUT"),
                EXC_TM = getArrayByNameNonNull("EXC_TM"),
                EXC = getArrayByNameNonNull("EXC"),
                ERR = getArrayByNameNonNull("ERR"),
                NO_METHOD = getArrayByNameNonNull("NO_METHOD"),
                NOM_PRACT = getArrayByNameNonNull("NOM_PRACT"),
                EJER_PRACT = getArrayByNameNonNull("EJER_PRACT"),
                EJER_PUNTOS = getArrayByNameNonNull("EJER_PUNTOS"),
                path = path,
                absPath = File(path).absolutePath
            )
        }


    private fun File.getStrBwDelims(delim1: String, delim2: String, fromIndex: Int)
            : IndexedResult<String>? =
        readText().let {
            val index = it.indexOf(delim1, fromIndex)
            if (index == -1) return null
            val s1 = it.substring(index + delim1.length)
            val index2 = s1.indexOf(delim2)
            if (index2 == -1) return null
            IndexedResult(s1.substring(0, index2), index)
        }

    private fun File.getStrBwDelimsNonNull(delim1: String, delim2: String, parsing: String): String =
        getStrBwDelims(delim1, delim2, 0)?.result
            ?: if (force) "" else throw FormatException(parsing, path)

    private fun <T> File.getListByName(arrayName: String, parser: (raw: String) -> T)
            : List<T>? {
        val del1 = "$arrayName ="
        val del2 = "};"
        var index = 0
        while (true) {
            val ir = getStrBwDelims(del1, del2, index)
            try {
                return ir?.run {
                    result.substring(result.indexOf("{") + 1).split(", ").map(parser)
                }
            } finally {
                index++
            }
        }
    }

    private inline fun <reified T> File.getArrayByNameNonNull(arrayName: String): Array<T> =
        (getListByName(arrayName, getParserForType<T>())
            ?: if (force) emptyList() else throw FormatException(arrayName, path))
            .toTypedArray()


    private fun <T> File.getVarByName(varName: String, parser: (raw: String) -> T): T? {
        val del1 = "$varName = "
        val del2 = ";"
        var index = 0
        while (true) {
            val ir = getStrBwDelims(del1, del2, index)
            try {
                return ir?.run { result.run(parser) }
            } finally {
                index++
            }
        }
    }

    private inline fun <reified T> File.getVarByNameNonNull(varName: String): T =
        getVarByName(varName, getParserForType())
            ?: if (force) getDefForType() else throw FormatException(varName, path)

    private inline fun <reified T> getParserForType(): (String) -> T =
        when (T::class) {
            Int::class -> { raw: String -> raw.toInt() as T }
            Double::class -> { raw: String -> raw.toDouble() as T }
            Boolean::class -> { raw: String -> raw.toBoolean() as T }
            String::class -> { raw: String -> StringEscapeUtils.unescapeJava(raw.substring(1, raw.length - 1)) as T }
            else -> { raw: String -> raw as T }
        }

    private inline fun <reified T> getDefForType(): T =
        T::class.java.getDeclaredConstructor().newInstance()

}