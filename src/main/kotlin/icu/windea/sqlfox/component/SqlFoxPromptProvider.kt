package icu.windea.sqlfox.component

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class SqlFoxPromptProvider : PromptProvider {
    override fun getPrompt(): AttributedString {
        return AttributedString("sql-fox>", AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN))
    }
}