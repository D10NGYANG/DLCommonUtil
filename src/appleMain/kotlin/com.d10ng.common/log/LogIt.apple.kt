package com.d10ng.common.log

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLog
import platform.Foundation.NSThread
import kotlin.experimental.ExperimentalNativeApi

internal actual object PlatformLogIt {

    private const val CALL_STACK_INDEX = 8

    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "MM-dd HH:mm:ss.SSS"
    }

    private val tagMap: HashMap<LogLevel, String> = hashMapOf(
        LogLevel.VERBOSE to "💜 VERBOSE",
        LogLevel.DEBUG to "💚 DEBUG",
        LogLevel.INFO to "💙 INFO",
        LogLevel.WARNING to "💛 WARN",
        LogLevel.ERROR to "❤️ ERROR",
        LogLevel.ASSERT to "💞 ASSERT"
    )

    @OptIn(ExperimentalNativeApi::class)
    actual fun log(
        l: LogLevel,
        t: String,
        m: String,
        tr: Throwable?,
        out: Boolean
    ): String {
        val fullMessage = buildLog(l, t, tr, m)
        if (out) {
            if (l == LogLevel.ASSERT) {
                assert(false) { fullMessage }
            } else {
                NSLog(fullMessage)
                //println(fullMessage)
            }
        }

        return fullMessage
    }

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())

    private fun buildLog(priority: LogLevel, tag: String, throwable: Throwable?, message: String?): String {
        val baseLogString = "${getCurrentTime()} ${tagMap[priority]} [$tag] $message"
        return if (throwable != null) {
            "$baseLogString\n${throwable.stackTraceToString()}"
        } else {
            baseLogString
        }
    }

    private fun createStackElementTag(string: String): String {
        var tag = string
        tag = tag.substringBeforeLast('$')
        tag = tag.substringBeforeLast('(')
        if (tag.contains("$")) {
            // coroutines
            tag = tag.substring(tag.lastIndexOf(".", tag.lastIndexOf(".") - 1) + 1)
            tag = tag.replace("$", "")
            tag = tag.replace("COROUTINE", "[async]")
        } else {
            // others
            tag = tag.substringAfterLast(".")
            tag = tag.replace("#", ".")
        }
        return tag
    }
}