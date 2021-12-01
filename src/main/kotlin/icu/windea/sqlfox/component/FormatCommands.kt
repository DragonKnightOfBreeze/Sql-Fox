package icu.windea.sqlfox.component

import com.alibaba.druid.DbType
import icu.windea.sqlfox.*
import org.springframework.shell.standard.*
import java.nio.charset.Charset

@ShellComponent
class FormatCommands {
    /**
     * Example: `switch-case -O l -i input.sql -o output.sql`
     */
    @ShellMethod(value = "Switch to specified case (excluding comments and literals).")
    fun switchCase(
        @ShellOption(value = ["-O", "--option"], help = """Option. (l - lower case, u - upper case)""") option: String,
        @ShellOption(value = ["-i", "--input"], help = "Input sql file path.") input: String,
        @ShellOption(value = ["-o", "--output"], help = "Output Sql file path.") output: String,
        @ShellOption(value = ["-d", "--db"], help = "Database type.", defaultValue = "mysql") db: String,
        @ShellOption(value = ["-c", "--charset"], help = "File charset", defaultValue = "utf8") charset: Charset
    ): String {
        val inputFile = input.toFile()
        val outputFile = output.toFile()
        val sql = inputFile.readText(charset)
        val dbType = DbType.of(db)
        val handler = getSqlHandler(sql, dbType)
        val result = when (option) {
            "l" -> handler.formatToLowerCase()
            "u" -> handler.formatToUpperCase()
            else -> throw UnsupportedOperationException("Unsupported option for command 'format'.")
        }
        outputFile.writeText(result, charset)
        return doneMessage
    }
}