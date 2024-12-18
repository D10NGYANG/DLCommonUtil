package com.d10ng.common.test

import com.d10ng.common.log.LogIt
import kotlin.test.Test

object DebugLog: LogIt("debug")

class LogTest {

    @Test
    fun test() {
        DebugLog.debug = true
        DebugLog.v("v")
        DebugLog.d("d")
        DebugLog.i("i")
        DebugLog.w("w")
        DebugLog.e("e")
    }
}