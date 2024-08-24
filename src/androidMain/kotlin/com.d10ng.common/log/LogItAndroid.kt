package com.d10ng.common.log

import android.util.Log

abstract class LogItAndroid(private val tag: String) : LogIt(tag) {

    fun v(t: String, m: String) {
        LogManager.emit("[VERBOSE] [$t] $m")
        if (debug) Log.v(t, m)
    }

    fun v(m: String) { v(tag, m) }

    fun d(t: String, m: String) {
        LogManager.emit("[DEBUG] [$t] $m")
        if (debug) Log.d(t, m)
    }

    fun d(m: String) { d(tag, m) }

    override fun i(t: String, m: String) {
        LogManager.emit("[INFO] [$t] $m")
        if (debug) Log.i(t, m)
    }

    override fun i(m: String) { i(tag, m) }

    fun w(t: String, m: String) {
        LogManager.emit("[WARN] [$t] $m")
        if (debug) Log.w(t, m)
    }

    fun w(m: String) { w(tag, m) }

    fun e(t: String, m: String) {
        LogManager.emit("[ERROR] [$t] $m")
        if (debug) Log.e(t, m)
    }

    fun e(m: String) { e(tag, m) }

    fun et(t: String, m: String, e: Throwable) {
        LogManager.emit("[ERROR] [$t] $m $e")
        Log.e(t, m, e)
    }

    fun et(m: String, e: Throwable) { et(tag, m, e) }
}