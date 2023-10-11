package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class LongTest {

    @Test
    fun testToByteArray() {
        // 正常情况
        mapOf(
            0x00L to byteArrayOf(0x00),
            0x0104L to byteArrayOf(0x01, 0x04),
            0x123456L to byteArrayOf(0x12, 0x34, 0x56),
            0x12345678L to byteArrayOf(0x12, 0x34, 0x56, 0x78),
            0x123456789AL to byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte()),
            0x123456789ABCL to byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte(), 0xBC.toByte()),
            0x123456789ABCDEF0L to byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte(), 0xBC.toByte(), 0xDE.toByte(), 0xF0.toByte()),
        ).forEach { (long, byteArray) ->
            assertEquals(byteArray.toHexString(), long.toByteArray().toHexString())
        }
        // 自定义字节数
        assertEquals(byteArrayOf(0x12).toHexString(), 0x12.toByteArray(1).toHexString())
        assertEquals(byteArrayOf(0x12, 0x34).toHexString(), 0x1234.toByteArray(2).toHexString())
        assertEquals(byteArrayOf(0x12, 0x34, 0x56).toHexString(), 0x123456.toByteArray(3).toHexString())
        // 负数
        assertFails { (-1).toByteArray() }
    }
}