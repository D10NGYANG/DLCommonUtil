package com.d10ng.common.log

import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern

internal actual object PlatformLogIt {
    private const val CALL_STACK_INDEX = 5

    private val anonymousClass = Pattern.compile("(\\$\\d+)+$")

    actual fun log(
        l: LogLevel,
        t: String,
        m: String,
        tr: Throwable?,
        out: Boolean
    ): String {
        val debugTag = performTag(t)

        val fullMessage = if (tr != null) {
            "$m\n${tr.stackTraceString}"
        } else {
            m
        }

        val logText = buildLog(l, debugTag, fullMessage)
        if (out) {
            println(logText)
        }
        return logText
    }

    private fun buildLog(priority: LogLevel, tag: String, message: String?): String {
        return "[${priority}] ${performTag(tag)} - $message"
    }

    private fun performTag(defaultTag: String): String {
        val thread = Thread.currentThread().stackTrace

        return if (thread.size >= CALL_STACK_INDEX) {
            thread[CALL_STACK_INDEX].run {
                "${createStackElementTag(className)}\$$methodName"
            }
        } else {
            defaultTag
        }
    }

    private fun createStackElementTag(className: String): String {
        var tag = className
        val m = anonymousClass.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag.substring(tag.lastIndexOf('.') + 1)
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
}