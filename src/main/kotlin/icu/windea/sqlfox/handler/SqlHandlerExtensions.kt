@file:JvmName("SqlHandlerExtensions")

package icu.windea.sqlfox.handler

import com.alibaba.druid.DbType
import icu.windea.sqlfox.defaultDbType
import java.util.concurrent.ConcurrentHashMap

private val cache: MutableMap<String, SqlHandler> = ConcurrentHashMap(object : LinkedHashMap<String, SqlHandler>() {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, SqlHandler>?): Boolean {
        return super.removeEldestEntry(eldest)
    }
})

fun getSqlHandler(sql: String, dbType: DbType = defaultDbType): SqlHandler {
    val key = "$dbType:$sql"
    return cache.getOrPut(key) { SqlHandler(sql, dbType) }
}