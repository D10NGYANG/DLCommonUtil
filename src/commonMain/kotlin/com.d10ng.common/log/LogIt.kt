package com.d10ng.common.log

/**
 * 日志工具类
 * @property tag String
 * @constructor
 */
abstract class LogIt(private val tag: String) {

    private val log = PlatformLogIt

    /**
     * 日志控制开关
     */
    var debug: Boolean = false

    open fun v(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.VERBOSE, t, m, tr, debug))
    }
    open fun v(t: String, m: String) { v(t, m, null) }
    open fun v(m: String, tr: Throwable?) { v(tag, m, tr) }
    open fun v(m: String) { v(tag, m, null) }

    open fun d(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.DEBUG, t, m, tr, debug))
    }
    open fun d(t: String, m: String) { d(t, m, null) }
    open fun d(m: String, tr: Throwable?) { d(tag, m, tr) }
    open fun d(m: String) { d(tag, m, null) }

    open fun i(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.INFO, t, m, tr, debug))
    }
    open fun i(t: String, m: String) { i(t, m, null) }
    open fun i(m: String, tr: Throwable?) { i(tag, m, tr) }
    open fun i(m: String) { i(tag, m, null) }

    open fun w(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.WARNING, t, m, tr, debug))
    }
    open fun w(t: String, m: String) { w(t, m, null) }
    open fun w(m: String, tr: Throwable?) { w(tag, m, tr) }
    open fun w(m: String) { w(tag, m, null) }

    open fun e(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.ERROR, t, m, tr, debug))
    }
    open fun e(t: String, m: String) { e(t, m, null) }
    open fun e(m: String, tr: Throwable?) { e(tag, m, tr) }
    open fun e(m: String) { e(tag, m, null) }

    open fun wtf(t: String, m: String, tr: Throwable?) {
        LogManager.emit(log.log(LogLevel.ASSERT, t, m, tr, debug))
    }
    open fun wtf(t: String, m: String) { wtf(t, m, null) }
    open fun wtf(m: String, tr: Throwable?) { wtf(tag, m, tr) }
    open fun wtf(m: String) { wtf(tag, m, null) }
}

internal enum class LogLevel {
    VERBOSE,
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    ASSERT,
}

internal expect object PlatformLogIt {
    fun log(l: LogLevel, t: String, m: String, tr: Throwable? = null, out: Boolean): String
}