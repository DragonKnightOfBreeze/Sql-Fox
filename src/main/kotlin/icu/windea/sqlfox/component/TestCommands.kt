package icu.windea.sqlfox.component

import org.springframework.shell.standard.*

@ShellComponent
class TestCommands {
    @ShellMethod("Hello word command.")
    fun hello(
        @ShellOption(defaultValue = "world", help = "Say hello to who?") who: String
    ): String {
        return "hello $who"
    }

    @ShellMethod("Print work directory.")
    fun pwd(): String {
        return System.getProperty("user.dir")
    }
}

