package com.d10ng.common.test

import com.d10ng.common.toHexString
import kotlin.test.Test

class IntTest {

    @Test
    fun test() {
        println(byteArrayOf(255.toByte(), 255.toByte()).toHexString().toInt(16))
    }
}