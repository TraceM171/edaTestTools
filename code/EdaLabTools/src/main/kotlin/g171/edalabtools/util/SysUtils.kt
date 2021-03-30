package g171.edalabtools.util

import javafx.application.Platform
import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.system.exitProcess

object SysUtils {
    fun exitFX(code: Int) {
        Platform.exit()
        exitProcess(code)
    }

    val userName: String
        get() = System.getProperty("user.name")
    val hostName: String
        get() = try {InetAddress.getLocalHost().hostName } catch (e: UnknownHostException) { "" }
}