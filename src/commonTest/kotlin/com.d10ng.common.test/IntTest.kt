package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class IntTest {

    @Test
    fun testToByteArray() {
        // 正常情况
        mapOf(
            0x00 to byteArrayOf(0x00),
            0x04 to byteArrayOf(0x04),
            0xFFFF to byteArrayOf(0xFF.toByte(), 0xFF.toByte()),
            0x12345678 to byteArrayOf(0x12, 0x34, 0x56, 0x78),
        ).forEach { (int, byteArray) ->
            assertEquals(byteArray.toHexString(), int.toByteArray().toHexString())
        }
        // 自定义字节数
        assertEquals(byteArrayOf(0x12).toHexString(), 0x12.toByteArray(1).toHexString())
        assertEquals(byteArrayOf(0x12, 0x34).toHexString(), 0x1234.toByteArray(2).toHexString())
        assertEquals(byteArrayOf(0x12, 0x34, 0x56).toHexString(), 0x123456.toByteArray(3).toHexString())
        // 负数
        assertFails { (-1).toByteArray() }
    }
}