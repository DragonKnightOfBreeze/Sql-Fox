package icu.windea.sqlfox

import com.alibaba.druid.DbType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqlFoxApplication

fun main(args: Array<String>) {
    runApplication<SqlFoxApplication>(*args)
}

val defaultDbType = DbType.mysql

const val doneMessage = "Done."