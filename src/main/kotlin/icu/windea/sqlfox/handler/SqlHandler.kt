package icu.windea.sqlfox.handler

import com.alibaba.druid.DbType
import com.alibaba.druid.sql.SQLUtils
import com.alibaba.druid.sql.ast.SQLStatement
import com.alibaba.druid.sql.ast.statement.*
import icu.windea.sqlfox.defaultDbType
import icu.windea.sqlfox.isLineBreak

class SqlHandler(
    val sql: String, val dbType: DbType = defaultDbType
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
            var inStringLiteral = false
            var inBlockComment = false
            var inComment = false
            var pc: Char?
            for ((i, c) in sql.withIndex()) {
                pc = sql.getOrNull(i - 1)
                if (!inBlockComment) {
                    if (!inComment) {
                        if(!inStringLiteral){
                            if (c == '\'') {
                                inStringLiteral = true
                            }else{
                                if (c == '#' || (c == '-' && pc == '-')) {
                                    inComment = true
                                } else if (c == '*' && pc == '/') {
                                    inBlockComment = true
                                }
                            }
                        }else{
                            if (c == '\'' && pc != '\\') {
                                inStringLiteral = false
                            }
                        }
                    } else {
                        if (c.isLineBreak()) {
                            inComment = false
                        }
                    }
                }else {
                    if (c == '/' && pc == '*') {
                        inBlockComment = false
                    }
                }

                //    if (c == '\'' && pc != '\\') {
                //        inStringLiteral = !inStringLiteral
                //    } else if (c == '*' && pc == '/') {
                //        inBlockComment = true
                //    } else {
                //
                //    }
                //}
                when {
                    inStringLiteral || inBlockComment || inComment -> append(c)
                    else -> append(transform(c))
                }
            }
        }
    }
}

