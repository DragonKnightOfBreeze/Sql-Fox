package icu.windea.sqlfox.component

import org.springframework.shell.standard.*

@ShellComponent
class HelloWorldComponent {
    @ShellMethod("Test command.")
    fun hello(
        @ShellOption(defaultValue = "world", help = "Say hello to who?") who: String
    ): String {
        return "hello $who"
    }
}

