package icu.windea.sqlfox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqlFoxApplication

fun main(args: Array<String>) {
    runApplication<SqlFoxApplication>(*args)
}
