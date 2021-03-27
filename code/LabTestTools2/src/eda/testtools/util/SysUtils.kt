package eda.testtools.util
import eda.testtools.model.LabTestInfo
import java.net.InetAddress
import java.net.UnknownHostException
import javafx.application.Platform
/**
*
* @author trace
*/
object SysUtils {
fun exitFX(code:Int) {
Platform.exit()
System.exit(code)
}
val userName:String?
get() {
return System.getProperty("user.name")
}
val hostName:String?
get() {
try
{
val localMachine = InetAddress.getLocalHost()
return localMachine!!.getHostName()
}
catch (e:UnknownHostException) {
return ""
}
}
}