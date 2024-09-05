package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toShort
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ShortTest {

    @Test
    fun testShortToByteArray() {
        // 测试自动计算字节数的情况
        assertContentEquals(byteArrayOf(0x12), 0x12.toByteArray())
        assertContentEquals(byteArrayOf(0x12, 0x34), 0x1234.toByteArray())

        // 测试指定字节数的情况
        assertContentEquals(byteArrayOf(0x12), 0x12.toByteArray( 1))
        assertContentEquals(byteArrayOf(0x00, 0x12), 0x12.toByteArray( 2))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x12), 0x12.toByteArray( 3))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x12), 0x12.toByteArray( 4))

        assertContentEquals(byteArrayOf(0x12, 0x34), 0x1234.toByteArray( 2))
        assertContentEquals(byteArrayOf(0x00, 0x12, 0x34), 0x1234.toByteArray( 3))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x12, 0x34), 0x1234.toByteArray( 4))

        // 负数
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xff.toByte()), (-1).toShort().toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xf0.toByte()), (-16).toShort().toByteArray())
    }

    @Test
    fun testByteArrayToShort() {
        // 测试正数
        assertEquals(0x0000, byteArrayOf(0x00, 0x00).toShort())
        assertEquals(0x0001, byteArrayOf(0x00, 0x01).toShort())
        assertEquals(0x0102, byteArrayOf(0x01, 0x02).toShort())
        assertEquals(0x7FFF, byteArrayOf(0x7F, 0xFF.toByte()).toShort())

        // 测试负数
        assertEquals(-1, byteArrayOf(0xFF.toByte(), 0xFF.toByte()).toShort())
        assertEquals(-2, byteArrayOf(0xFF.toByte(), 0xFE.toByte()).toShort())
        assertEquals(-32768, byteArrayOf(0x80.toByte(), 0x00).toShort())

        // 测试单字节情况
        assertEquals(0x0000, byteArrayOf(0x00).toShort())
        assertEquals(0x0001, byteArrayOf(0x01).toShort())
        assertEquals(0x007F, byteArrayOf(0x7F).toShort())
        assertEquals(-1, byteArrayOf(0xFF.toByte()).toShort())
        assertEquals(-128, byteArrayOf(0x80.toByte()).toShort())

        // 测试空字节数组
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf().toShort()
        }

        // 测试字节数组长度大于2的情况
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x00, 0x00, 0x00).toShort()
        }
    }
}