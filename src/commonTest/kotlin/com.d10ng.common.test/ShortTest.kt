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
        // 自动长度边界
        assertContentEquals(byteArrayOf(0x00), 0.toShort().toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte()), 0xff.toShort().toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00), 0x100.toShort().toByteArray())
        assertContentEquals(byteArrayOf(0x7f, 0xff.toByte()), Short.MAX_VALUE.toByteArray())

        // 显式长度的高位补零和低位截断
        assertContentEquals(byteArrayOf(0x00, 0x12), 0x12.toShort().toByteArray(2))
        assertContentEquals(byteArrayOf(0x34), 0x1234.toShort().toByteArray(1))

        // 负数自动使用完整补码，显式缩短时保留低位字节
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xff.toByte()), (-1).toShort().toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xf0.toByte()), (-16).toShort().toByteArray())
        assertContentEquals(byteArrayOf(0xf0.toByte()), (-16).toShort().toByteArray(1))
    }

    @Test
    fun testShortToByteArrayRejectsInvalidSize() {
        listOf(Int.MIN_VALUE, -1, 0, 3, Int.MAX_VALUE).forEach { size ->
            assertFailsWith<IllegalArgumentException> {
                1.toShort().toByteArray(size)
            }
        }
    }

    @Test
    fun testByteArrayToShort() {
        assertEquals(0.toShort(), byteArrayOf(0x00, 0x00).toShort())
        assertEquals(0x0102.toShort(), byteArrayOf(0x01, 0x02).toShort())
        assertEquals(Short.MAX_VALUE, byteArrayOf(0x7f, 0xff.toByte()).toShort())

        // 双字节按 Short 的 16 位补码解析
        assertEquals((-1).toShort(), byteArrayOf(0xff.toByte(), 0xff.toByte()).toShort())
        assertEquals(Short.MIN_VALUE, byteArrayOf(0x80.toByte(), 0x00).toShort())

        // 单字节按无符号数值解析
        assertEquals(0.toShort(), byteArrayOf(0x00).toShort())
        assertEquals(0x7f.toShort(), byteArrayOf(0x7f).toShort())
        assertEquals(0x80.toShort(), byteArrayOf(0x80.toByte()).toShort())
        assertEquals(0xff.toShort(), byteArrayOf(0xff.toByte()).toShort())

        assertFailsWith<IllegalArgumentException> { byteArrayOf().toShort() }
        assertFailsWith<IllegalArgumentException> { ByteArray(3).toShort() }
    }

    @Test
    fun testTwoByteRoundTrip() {
        listOf(Short.MIN_VALUE, (-1).toShort(), 0.toShort(), 1.toShort(), Short.MAX_VALUE).forEach { value ->
            assertEquals(value, value.toByteArray(Short.SIZE_BYTES).toShort())
        }
    }
}
