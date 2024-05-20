package com.d10ng.common.log

/**
 * 日志工具类
 * @property tag String
 * @constructor
 */
abstract class LogIt(private val tag: String) {

    /**
     * 日志控制开关
     */
    var debug: Boolean = false

    open fun i(t: String, m: String) {
        LogManager.emit("[$t] $m")
        if (debug) println("[$t] $m")
    }


    open fun i(m: String) { i(tag, m) }
}