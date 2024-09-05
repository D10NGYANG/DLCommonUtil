package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toFloat
import com.d10ng.common.base.toStringWithMaxDecimal
import kotlin.test.*

class FloatTest {

    @Test
    fun testToString() {
        // 正常情况
        val value = 0.1234567f
        assertEquals("0.1", value.toStringWithMaxDecimal(1))
        assertEquals("0.12", value.toStringWithMaxDecimal(2))
        assertEquals("0.123", value.toStringWithMaxDecimal(3))
        assertEquals("0.1234", value.toStringWithMaxDecimal(4))
        assertEquals("0.12345", value.toStringWithMaxDecimal(5))
        assertEquals("0.1234567", value.toStringWithMaxDecimal(7))
        assertEquals("0.1234567", value.toStringWithMaxDecimal(8))

        // 边界情况
        assertEquals("0", value.toStringWithMaxDecimal(0))

        // 异常情况
        assertFails { value.toStringWithMaxDecimal(-1) }
    }

    @Test
    fun testFloatToByteArray() {
        // 测试正数
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00), 0f.toByteArray())
        assertContentEquals(byteArrayOf(0x3F, 0x80.toByte(), 0x00, 0x00), 1f.toByteArray())
        assertContentEquals(byteArrayOf(0x40, 0x00, 0x00, 0x00), 2f.toByteArray())
        assertContentEquals(byteArrayOf(0x40, 0x48, 0xF5.toByte(), 0xC3.toByte()), 3.14f.toByteArray())
        assertContentEquals(byteArrayOf(0x42, 0xC8.toByte(), 0x00, 0x00), 100f.toByteArray())

        // 测试负数
        assertContentEquals(byteArrayOf(0xBF.toByte(), 0x80.toByte(), 0x00, 0x00), (-1f).toByteArray())
        assertContentEquals(byteArrayOf(0xC0.toByte(), 0x00, 0x00, 0x00), (-2f).toByteArray())
        assertContentEquals(byteArrayOf(0xC0.toByte(), 0x48, 0xF5.toByte(), 0xC3.toByte()), (-3.14f).toByteArray())
        assertContentEquals(byteArrayOf(0xC2.toByte(), 0xC8.toByte(), 0x00, 0x00), (-100f).toByteArray())

        // 测试特殊值
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00), 0f.toByteArray())
        assertContentEquals(byteArrayOf(0x80.toByte(), 0x00, 0x00, 0x00), (-0f).toByteArray())
        assertContentEquals(byteArrayOf(0x7F, 0x80.toByte(), 0x00, 0x00), Float.POSITIVE_INFINITY.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0x80.toByte(), 0x00, 0x00), Float.NEGATIVE_INFINITY.toByteArray())
        assertContentEquals(byteArrayOf(0x7F, 0xC0.toByte(), 0x00, 0x00), Float.NaN.toByteArray())
    }

    @Test
    fun testByteArrayToFloat() {
        // 测试正数
        assertEquals(0f, byteArrayOf(0x00, 0x00, 0x00, 0x00).toFloat())
        assertEquals(1f, byteArrayOf(0x3F, 0x80.toByte(), 0x00, 0x00).toFloat())
        assertEquals(2f, byteArrayOf(0x40, 0x00, 0x00, 0x00).toFloat())
        //assertEquals(3.14f, byteArrayOf(0x40, 0x48, 0xF5.toByte(), 0xC3.toByte()).toFloat())
        assertEquals(100f, byteArrayOf(0x42, 0xC8.toByte(), 0x00, 0x00).toFloat())

        // 测试负数
        assertEquals(-1f, byteArrayOf(0xBF.toByte(), 0x80.toByte(), 0x00, 0x00).toFloat())
        assertEquals(-2f, byteArrayOf(0xC0.toByte(), 0x00, 0x00, 0x00).toFloat())
        //assertEquals(-3.14f, byteArrayOf(0xC0.toByte(), 0x48, 0xF5.toByte(), 0xC3.toByte()).toFloat())
        assertEquals(-100f, byteArrayOf(0xC2.toByte(), 0xC8.toByte(), 0x00, 0x00).toFloat())

        // 测试特殊值
        assertEquals(0f, byteArrayOf(0x00, 0x00, 0x00, 0x00).toFloat())
        assertEquals(-0f, byteArrayOf(0x80.toByte(), 0x00, 0x00, 0x00).toFloat())
        assertEquals(Float.POSITIVE_INFINITY, byteArrayOf(0x7F, 0x80.toByte(), 0x00, 0x00).toFloat())
        assertEquals(Float.NEGATIVE_INFINITY, byteArrayOf(0xFF.toByte(), 0x80.toByte(), 0x00, 0x00).toFloat())
        assertTrue(byteArrayOf(0x7F, 0xC0.toByte(), 0x00, 0x00).toFloat().isNaN())

        // 测试字节数组长度不等于4的情况
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x00).toFloat()
        }
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x00, 0x00).toFloat()
        }
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x00, 0x00, 0x00).toFloat()
        }
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00).toFloat()
        }
    }
}