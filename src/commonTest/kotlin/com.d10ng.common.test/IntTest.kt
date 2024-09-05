package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toHexString
import com.d10ng.common.base.toInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
        assertEquals((-1).toByteArray().toHexString(), "FFFFFFFF")
    }

    @Test
    fun testByteArrayToInt() {
        // 测试正常情况
        assertEquals(0x12345678, byteArrayOf(0x12, 0x34, 0x56, 0x78).toInt())
        assertEquals(-1, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toInt())
        assertEquals(1, byteArrayOf(0x00, 0x00, 0x00, 0x01).toInt())

        // 测试字节数组长度小于4的情况
        assertEquals(0x12, byteArrayOf(0x12).toInt())
        assertEquals(0x1234, byteArrayOf(0x12, 0x34).toInt())
        assertEquals(0x123456, byteArrayOf(0x12, 0x34, 0x56).toInt())

        // 测试空字节数组的情况
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf().toInt()
        }

        // 测试字节数组长度大于4的情况
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte()).toInt()
        }
    }
}