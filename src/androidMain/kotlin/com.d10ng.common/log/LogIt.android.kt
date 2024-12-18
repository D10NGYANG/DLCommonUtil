package com.d10ng.common.log

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

internal actual object PlatformLogIt {

    actual fun log(
        l: LogLevel,
        t: String,
        m: String,
        tr: Throwable?,
        out: Boolean
    ): String {

        val fullMessage = if (tr != null) {
            "$m\n${tr.stackTraceString}"
        } else {
            m
        }

        if (out) {
            // Fast path for small messages which can fit in a single call.
            if (l == LogLevel.ASSERT) {
                Log.wtf(t, fullMessage)
            } else {
                Log.println(l.toValue(), t, fullMessage)
            }
        }
        return "[$l] $t : $fullMessage"
    }

    private val Throwable.stackTraceString
        get(): String {
            // DO NOT replace this with Log.getStackTraceString() - it hides UnknownHostException, which is
            // not what we want.
            val sw = StringWriter(256)
            val pw = PrintWriter(sw, false)
            printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }

    private fun LogLevel.toValue() = when (this) {
        LogLevel.VERBOSE -> Log.VERBOSE
        LogLevel.DEBUG -> Log.DEBUG
        LogLevel.INFO -> Log.INFO
        LogLevel.WARNING -> Log.WARN
        LogLevel.ERROR -> Log.ERROR
        LogLevel.ASSERT -> Log.ASSERT
    }
}