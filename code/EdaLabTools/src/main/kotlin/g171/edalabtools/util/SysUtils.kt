package g171.edalabtools.util

import java.net.InetAddress
import java.net.UnknownHostException

object SysUtils {
    val userName: String
        get() = System.getProperty("user.name")
    val hostName: String
        get() = try {InetAddress.getLocalHost().hostName } catch (e: UnknownHostException) { "" }
}