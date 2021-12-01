@file:JvmName("SpringExtensions")

package icu.windea.sqlfox

import org.springframework.util.ResourceUtils
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toFile(): File {
    return ResourceUtils.getFile(this)
}

fun String.createFile(): File {
    return toFile().also { it.createFile() }
}

fun File.createFile() {
    runCatching {
        if (isDirectory) {
            mkdirs()
        } else {
            parentFile.mkdirs()
            createNewFile()
        }
    }
}

private val timeStampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

private fun currentTimestamp(): String = LocalDateTime.now().format(timeStampFormatter)

fun getBackupFilePath(path:String): String{
    return path + ".bak" + currentTimestamp()
}