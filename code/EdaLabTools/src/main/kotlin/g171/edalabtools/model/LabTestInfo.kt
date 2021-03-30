package g171.edalabtools.model

import g171.edalabtools.util.SysUtils
import java.time.Instant

data class LabTestInfo(
    // Extracted
    val verb: Boolean,
    val CAS: Int,
    val ENG: Int,
    val turno: Int,
    val lang: Int,
    val TIME_OUT: Int,
    val MIN_NOTA: Double,
    val CAP: Array<String>,
    val LIN: String,
    val ALUM: Array<String>,
    val ENTREGA: Array<String>,
    val PRUEBA: Array<String>,
    val NO_AUT: Array<String>,
    val EXC_TM: Array<String>,
    val EXC: Array<String>,
    val ERR: Array<String>,
    val NO_METHOD: Array<String>,
    val NOM_PRACT: Array<String>,
    val EJER_PRACT: Array<Int>,
    val EJER_PUNTOS: Array<Double>,
    val path: String,
    val absPath: String,

    // Derived
    val alumno: String = SysUtils.userName,
    val pc: String = SysUtils.hostName,
    val notaLabTests: Double = EJER_PUNTOS.sum(),
    val ahora: Instant = Instant.ofEpochMilli(System.currentTimeMillis() + 3600000L),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LabTestInfo

        if (verb != other.verb) return false
        if (CAS != other.CAS) return false
        if (ENG != other.ENG) return false
        if (turno != other.turno) return false
        if (lang != other.lang) return false
        if (TIME_OUT != other.TIME_OUT) return false
        if (MIN_NOTA != other.MIN_NOTA) return false
        if (!CAP.contentEquals(other.CAP)) return false
        if (LIN != other.LIN) return false
        if (!ALUM.contentEquals(other.ALUM)) return false
        if (!ENTREGA.contentEquals(other.ENTREGA)) return false
        if (!PRUEBA.contentEquals(other.PRUEBA)) return false
        if (!NO_AUT.contentEquals(other.NO_AUT)) return false
        if (!EXC_TM.contentEquals(other.EXC_TM)) return false
        if (!EXC.contentEquals(other.EXC)) return false
        if (!ERR.contentEquals(other.ERR)) return false
        if (!NO_METHOD.contentEquals(other.NO_METHOD)) return false
        if (!NOM_PRACT.contentEquals(other.NOM_PRACT)) return false
        if (!EJER_PRACT.contentEquals(other.EJER_PRACT)) return false
        if (!EJER_PUNTOS.contentEquals(other.EJER_PUNTOS)) return false
        if (path != other.path) return false
        if (absPath != other.absPath) return false
        if (alumno != other.alumno) return false
        if (pc != other.pc) return false

        return true
    }

    override fun hashCode(): Int {
        var result = verb.hashCode()
        result = 31 * result + CAS
        result = 31 * result + ENG
        result = 31 * result + turno
        result = 31 * result + lang
        result = 31 * result + TIME_OUT
        result = 31 * result + MIN_NOTA.hashCode()
        result = 31 * result + CAP.contentHashCode()
        result = 31 * result + LIN.hashCode()
        result = 31 * result + ALUM.contentHashCode()
        result = 31 * result + ENTREGA.contentHashCode()
        result = 31 * result + PRUEBA.contentHashCode()
        result = 31 * result + NO_AUT.contentHashCode()
        result = 31 * result + EXC_TM.contentHashCode()
        result = 31 * result + EXC.contentHashCode()
        result = 31 * result + ERR.contentHashCode()
        result = 31 * result + NO_METHOD.contentHashCode()
        result = 31 * result + NOM_PRACT.contentHashCode()
        result = 31 * result + EJER_PRACT.contentHashCode()
        result = 31 * result + EJER_PUNTOS.contentHashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + absPath.hashCode()
        result = 31 * result + alumno.hashCode()
        result = 31 * result + pc.hashCode()
        return result
    }

    fun toFormattedString(): String =
        """
            verb=$verb,
            CAS=$CAS,
            ENG=$ENG,
            turno=$turno,
            lang=$lang,
            TIME_OUT=$TIME_OUT,
            MIN_NOTA=$MIN_NOTA,
            CAP=${CAP.contentToString()},
            LIN='$LIN',
            ALUM=${ALUM.contentToString()},
            ENTREGA=${ENTREGA.contentToString()},
            PRUEBA=${PRUEBA.contentToString()},
            NO_AUT=${NO_AUT.contentToString()},
            EXC_TM=${EXC_TM.contentToString()},
            EXC=${EXC.contentToString()},
            ERR=${ERR.contentToString()},
            NO_METHOD=${NO_METHOD.contentToString()},
            NOM_PRACT=${NOM_PRACT.contentToString()},
            EJER_PRACT=${EJER_PRACT.contentToString()},
            EJER_PUNTOS=${EJER_PUNTOS.contentToString()},
            path='$path',
            absPath='$absPath',
            alumno='$alumno',
            pc='$pc')
        """

}