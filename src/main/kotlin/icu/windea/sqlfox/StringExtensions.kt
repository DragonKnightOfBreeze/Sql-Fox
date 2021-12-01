@file:JvmName("StringExtensions")

package icu.windea.sqlfox

/**
 * Returns `true` if this char sequence surrounds with the specified characters.
 */
fun CharSequence.surroundsWith(prefix: Char, suffix: Char, ignoreCase: Boolean = false): Boolean {
    return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified character.
 */
fun CharSequence.surroundsWith(delimiter: Char, ignoreCase: Boolean = false): Boolean {
    return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified prefix and suffix.
 */
fun CharSequence.surroundsWith(prefix: CharSequence, suffix: CharSequence, ignoreCase: Boolean = false): Boolean {
    return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified delimiter.
 */
fun CharSequence.surroundsWith(delimiter: CharSequence, ignoreCase: Boolean = false): Boolean {
    return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}


/**
 * If this char sequence don't starts with the given [prefix],
 * returns a new char sequence with the prefix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addPrefix(prefix: CharSequence): CharSequence {
    if(this.startsWith(prefix)) return subSequence(0, length)
    return "$prefix$this"
}

/**
 * If this char sequence don't starts with the given [prefix],
 * returns a new char sequence with the prefix added.
 * Otherwise, returns this string.
 */
fun String.addPrefix(prefix: CharSequence): String {
    if(this.startsWith(prefix)) return this
    return "$prefix$this"
}

/**
 * If this char sequence don't ends with the given [suffix],
 * returns a new char sequence with the suffix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addSuffix(suffix: CharSequence): CharSequence {
    if(this.endsWith(suffix)) return subSequence(0, length)
    return "$this$suffix"
}

/**
 * If this char sequence don't ends with the given [suffix],
 * returns a new char sequence with the suffix added.
 * Otherwise, returns this string.
 */
fun String.addSuffix(suffix: CharSequence): String {
    if(this.endsWith(suffix)) return this
    return "$this$suffix"
}

/**
 * If this char sequence don't surrounds with the given [delimiter],
 * returns a new char sequence with the delimiter added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addSurrounding(delimiter: CharSequence): CharSequence {
    return this.addSurrounding(delimiter, delimiter)
}

/**
 * If this char sequence don't surrounds with the given [delimiter],
 * returns a new char sequence with the delimiter added.
 * Otherwise, returns this string.
 */
fun String.addSurrounding(delimiter: CharSequence): String {
    return this.addSurrounding(delimiter, delimiter)
}

/**
 * If this char sequence don't surrounds with the given [prefix] and [suffix],
 * returns a new char sequence with the prefix and suffix added.
 * Otherwise, returns this string.
 */
fun CharSequence.addSurrounding(prefix: CharSequence, suffix: CharSequence): CharSequence {
    if(this.startsWith(prefix) && this.endsWith(suffix)) return subSequence(0, length)
    return "$prefix$this$suffix"
}

/**
 * If this char sequence don't surrounds with the given [prefix] and [suffix],
 * returns a new char sequence with the prefix and suffix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun String.addSurrounding(prefix: CharSequence, suffix: CharSequence): String {
    if(this.startsWith(prefix) && this.endsWith(suffix)) return this
    return "$prefix$this$suffix"
}


private val quotes = charArrayOf('\'', '\"', '`')

/**
 * 尝试使用指定的引号包围当前字符串。
 * 适用于单引号、双引号、反引号。
 * 默认不转义其中的对应的引号。
 */
fun String.quote(quote: Char, escapeQuotes: Boolean = false): String {
    if(quote !in quotes) throw IllegalArgumentException("Invalid quote: $quote.")
    return when {
        this.surroundsWith(quote) -> this
        escapeQuotes -> this.replace(quote.toString(), "\\$quote").addSurrounding(quote.toString())
        else -> this.addSurrounding(quote.toString())
    }
}

/**
 * 尝试去除当前字符串两侧的引号。如果没有，则返回自身。
 * 适用于单引号、双引号、反引号。
 * 默认不指定引号，不反转义其中的对应的引号。
 */
fun String.unquote(quote: Char? = null, unescapeQuotes: Boolean = false): String {
    if(quote != null && quote !in quotes) throw IllegalArgumentException("Invalid quote: $quote.")
    val usedQuote = quote ?: this.firstOrNull() ?: return this
    if(quote == null && usedQuote !in quotes) return this
    return when {
        !this.surroundsWith(usedQuote) -> this
        unescapeQuotes -> this.removeSurrounding(usedQuote.toString()).replace("\\$usedQuote", usedQuote.toString())
        else -> this.removeSurrounding(usedQuote.toString())
    }
}


fun String.toCsvColumns(): List<String> {
    val result = mutableListOf<String>()
    var pc = '\u0000'
    var isInQuote = false
    val builder = StringBuilder()
    for (c in this) {
        when {
            c.isWhitespace() && !isInQuote -> continue
            c == '"' && pc != '\\' -> {
                isInQuote = !isInQuote
            }
            c == ',' && !isInQuote -> {
                result.add(builder.toString())
                builder.clear()
            }
            else -> {
                builder.append(c)
            }
        }
        pc = c
    }
    result.add(builder.toString())
    return result
}


/**
 * Returns index of the first character matching the given [predicate], or -1 if the char sequence does not contain such character.
 */
inline fun CharSequence.indexOfFirst(startIndex: Int, predicate: (Char) -> Boolean): Int {
    for (index in startIndex..lastIndex) {
        if (predicate(this[index])) {
            return index
        }
    }
    return -1
}

/**
 * Returns index of the last character matching the given [predicate], or -1 if the char sequence does not contain such character.
 */
inline fun CharSequence.indexOfLast(startIndex: Int, predicate: (Char) -> Boolean): Int {
    for (index in lastIndex..startIndex) {
        if (predicate(this[index])) {
            return index
        }
    }
    return -1
}
