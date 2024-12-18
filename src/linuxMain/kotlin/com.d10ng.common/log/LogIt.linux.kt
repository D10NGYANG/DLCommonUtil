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

        if (out) println(fullMessage)
        return fullMessage
    }
}