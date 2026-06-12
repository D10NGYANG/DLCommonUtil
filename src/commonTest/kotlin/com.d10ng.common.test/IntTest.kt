package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toInt
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class IntTest {

    @Test
    fun testToByteArray() {
        // 自动长度边界
        assertContentEquals(byteArrayOf(0x00), 0.toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte()), 0xff.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00), 0x100.toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xff.toByte()), 0xffff.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00), 0x10000.toByteArray())
        assertContentEquals(
            byteArrayOf(0xff.toByte(), 0xff.toByte(), 0xff.toByte()),
            0xffffff.toByteArray(),
        )
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00), 0x1000000.toByteArray())
        assertContentEquals(byteArrayOf(0x7f, 0xff.toByte(), 0xff.toByte(), 0xff.toByte()), Int.MAX_VALUE.toByteArray())

        // 显式长度的高位补零和低位截断
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x12), 0x12.toByteArray(4))
        assertContentEquals(byteArrayOf(0x56, 0x78), 0x12345678.toByteArray(2))

        // 负数自动使用完整补码，显式缩短时保留低位字节
        assertContentEquals(ByteArray(4) { 0xff.toByte() }, (-1).toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xf0.toByte()), (-16).toByteArray(2))
    }

    @Test
    fun testToByteArrayRejectsInvalidSize() {
        listOf(Int.MIN_VALUE, -1, 0, 5, Int.MAX_VALUE).forEach { size ->
            assertFailsWith<IllegalArgumentException> {
                1.toByteArray(size)
            }
        }
    }

    @Test
    fun testByteArrayToInt() {
        assertEquals(0x12345678, byteArrayOf(0x12, 0x34, 0x56, 0x78).toInt())
        assertEquals(-1, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toInt())
        assertEquals(1, byteArrayOf(0x00, 0x00, 0x00, 0x01).toInt())

        // 1 到 3 字节按无符号数值解析
        assertEquals(0x12, byteArrayOf(0x12).toInt())
        assertEquals(0xff, byteArrayOf(0xff.toByte()).toInt())
        assertEquals(0x1234, byteArrayOf(0x12, 0x34).toInt())
        assertEquals(0x123456, byteArrayOf(0x12, 0x34, 0x56).toInt())

        assertFailsWith<IllegalArgumentException> {
            byteArrayOf().toInt()
        }
        assertFailsWith<IllegalArgumentException> {
            byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte()).toInt()
        }
    }

    @Test
    fun testFourByteRoundTrip() {
        listOf(Int.MIN_VALUE, -1, 0, 1, Int.MAX_VALUE).forEach { value ->
            assertEquals(value, value.toByteArray(Int.SIZE_BYTES).toInt())
        }
    }
}
