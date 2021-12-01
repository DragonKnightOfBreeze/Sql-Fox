package icu.windea.sqlfox.handler

import com.alibaba.druid.DbType
import com.alibaba.druid.sql.SQLUtils
import com.alibaba.druid.sql.ast.SQLStatement
import com.alibaba.druid.sql.ast.statement.*
import icu.windea.sqlfox.defaultDbType

class SqlHandler(
    val sql: String,
    val dbType: DbType = defaultDbType
) {
    val statements: List<SQLStatement> by lazy { SQLUtils.parseStatements(sql, dbType) }

    val ddlStatements by lazy { statements.filterIsInstance<SQLDDLStatement>() }
    val createTableStatements by lazy { ddlStatements.filterIsInstance<SQLCreateTableStatement>() }
    val alterTableStatements by lazy { ddlStatements.filterIsInstance<SQLAlterTableStatement>() }

    val selectStatements by lazy { statements.filterIsInstance<SQLSelectStatement>() }
    val insertStatements by lazy { statements.filterIsInstance<SQLInsertStatement>() }
    val updateStatements by lazy { statements.filterIsInstance<SQLUpdateStatement>() }
    val deleteStatements by lazy { statements.filterIsInstance<SQLDeleteStatement>() }

    fun formatToLowerCase(): String {
        return doSwitchCase { it.lowercaseChar() }
    }

    fun formatToUpperCase(): String {
        return doSwitchCase { it.uppercaseChar() }
    }

    private inline fun doSwitchCase(transform: (Char) -> Char): String {
        return buildString {
            var isComment = false
            var isStringLiteral = false
            val length = sql.length
            for ((i, c) in sql.withIndex()) {
                when {
                    !isComment && (i == 0 || sql[i - 1].let { it == '\r' || it == '\n' }) && (c == '#' || (c == '-' && i != length - 1 && sql[i + 1] == '-')) -> isComment = true
                    isComment && (c == '\r' || c == '\n') -> isComment = false
                    !isComment && c == '\'' -> isStringLiteral = !isStringLiteral
                }
                when {
                    isComment || isStringLiteral -> append(c)
                    else -> append(transform(c))
                }
            }
        }
    }
}

