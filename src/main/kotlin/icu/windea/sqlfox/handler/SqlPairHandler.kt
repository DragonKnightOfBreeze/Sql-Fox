package icu.windea.sqlfox.handler

import com.alibaba.druid.DbType
import icu.windea.sqlfox.defaultDbType

class SqlPairHandler(
    val sourceSql: String,
    val targetSql: String,
    val sourceDbType: DbType = defaultDbType,
    val targetDbType: DbType = defaultDbType
){
    val sourceHandler: SqlHandler = getSqlHandler(sourceSql, sourceDbType)
    val targetHandler: SqlHandler = getSqlHandler(targetSql, targetDbType)
}