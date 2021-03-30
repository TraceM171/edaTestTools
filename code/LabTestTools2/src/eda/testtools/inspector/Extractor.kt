package eda.testtools.inspector
import eda.testtools.model.LabTestInfo
import eda.testtools.util.FileUtils
import eda.testtools.util.SysUtils
import java.io.File
import java.io.IOException
/**
*
* @author trace
*/
class Extractor(inputPath:String?) {
private val inputPath:String? = null
init{
this.inputPath = inputPath
}
@Throws(IOException::class)
fun extract():LabTestInfo? {
val lti = LabTestInfo()
val inputFile = File(inputPath)
lti!!.alumno = SysUtils.getUserName()
lti!!.pc = SysUtils.getHostName()
lti!!.path = FileUtils.getStrBwDelimsFromFile(inputFile,
PATH_DELIMS!![0], PATH_DELIMS!![1], 0).result
lti!!.absPath = (File(lti!!.path)).getAbsolutePath()
val parser = { raw-> Integer.parseInt(raw!!.replace(" ", "")) }
val epI = FileUtils.getArrayByNameFromFile<Integer>(inputFile, EJER_PRACT_NAME, 0, parser, Integer::class.java).result
val ep = IntArray(epI!!.size)
for (i in ep!!.indices) ep[i] = epI!![i].toInt()
lti!!.ejer_pract = ep
return lti
}
companion object {
private val PATH_DELIMS = arrayOf<String>("File eixida = new File(\"", "\");")
private val EJER_PRACT_NAME = "EJER_PRACT"
}
}