package icu.windea.sqlfox.component

import com.alibaba.druid.DbType
import icu.windea.sqlfox.getBackupFilePath
import icu.windea.sqlfox.getSqlHandler
import icu.windea.sqlfox.toFile
import org.springframework.shell.standard.*
import java.nio.charset.Charset

@ShellComponent
class FormatComponent {
    @ShellMethod(value = "Format sql.")
    fun format(
        @ShellOption(help = "Sql file path.") path: String,
        @ShellOption(help = "Database type.", defaultValue = "mysql") db: String,
        @ShellOption(help = "Format option.") option: String,
        @ShellOption(help = "File charset",defaultValue = "utf8") charset: Charset,
        @ShellOption(help = "Whether to backup original file.",defaultValue = "true") backup: Boolean
    ): String {
        val file = path.toFile()
        val sql = file.readText()
        val dbType = DbType.of(db)
        if (backup) {
            val backupFile = getBackupFilePath(path).toFile()
            file.copyTo(backupFile, overwrite = true)
        }
        val handler = getSqlHandler(sql, dbType)
        return when (option) {
            "l", "lc", "lower-case", "lowerCase" -> handler.formatToLowerCase()
            "u", "uc", "upper-case", "upperCase" -> handler.formatToUpperCase()
            else -> throw UnsupportedOperationException("Unsupported option for command 'format'.")
        }
    }
}