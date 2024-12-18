package com.d10ng.common.log

internal actual object PlatformLogIt {
    actual fun log(
        l: LogLevel,
        t: String,
        m: String,
        tr: Throwable?,
        out: Boolean
    ): String {
        val fullMessage = if (tr != null) {
            "[$l] $t : $m\n${tr.message}"
        } else {
            "[$l] $t : $m"
        }

        if (out) {
            when (l) {
                LogLevel.VERBOSE -> console.log(fullMessage)
                LogLevel.DEBUG -> console.log(fullMessage)
                LogLevel.INFO -> console.info(fullMessage)
                LogLevel.WARNING -> console.warn(fullMessage)
                LogLevel.ERROR -> console.error(fullMessage)
                LogLevel.ASSERT -> console.error(fullMessage)
            }
        }

        return fullMessage
    }
}