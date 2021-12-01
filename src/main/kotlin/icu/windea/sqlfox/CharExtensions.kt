@file:JvmName("CharExtensions")

package icu.windea.sqlfox

fun Char.isLineBreak():Boolean{
    return this == '\n' || this == '\r'
}

