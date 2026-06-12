package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toLong
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LongTest {

    @Test
    fun testLongToByteArray() {
        // 自动长度边界
        assertContentEquals(byteArrayOf(0x00), 0L.toByteArray())
        for (byteCount in 1 until Long.SIZE_BYTES) {
            val currentMax = (1L shl (byteCount * Byte.SIZE_BITS)) - 1
            val nextMin = 1L shl (byteCount * Byte.SIZE_BITS)

            assertEquals(byteCount, currentMax.toByteArray().size)
            assertEquals(byteCount + 1, nextMin.toByteArray().size)
        }
        assertEquals(Long.SIZE_BYTES, Long.MAX_VALUE.toByteArray().size)

        // 显式长度的高位补零和低位截断
        assertContentEquals(ByteArray(7) + byteArrayOf(0x12), 0x12L.toByteArray(8))
        assertContentEquals(
            byteArrayOf(0x9a.toByte(), 0xbc.toByte(), 0xde.toByte(), 0xf0.toByte()),
            0x123456789abcdef0L.toByteArray(4),
        )

        // 负数自动使用完整补码，显式缩短时保留低位字节
        assertContentEquals(ByteArray(Long.SIZE_BYTES) { 0xff.toByte() }, (-1L).toByteArray())
        assertContentEquals(byteArrayOf(0xff.toByte(), 0xf0.toByte()), (-16L).toByteArray(2))
    }

    @Test
    fun testLongToByteArrayRejectsInvalidSize() {
        listOf(Int.MIN_VALUE, -1, 0, 9, Int.MAX_VALUE).forEach { size ->
            assertFailsWith<IllegalArgumentException> {
                1L.toByteArray(size)
            }
        }
    }

    @Test
    fun testByteArrayToLong() {
        assertEquals(0L, byteArrayOf(0x00).toLong())
        assertEquals(255L, byteArrayOf(0xFF.toByte()).toLong())
        assertEquals(0x123456789abcdef0L, byteArrayOf(
            0x12,
            0x34,
            0x56,
            0x78,
            0x9a.toByte(),
            0xbc.toByte(),
            0xde.toByte(),
            0xf0.toByte(),
        ).toLong())

        // 1 到 7 字节按无符号数值解析
        assertEquals(0xffffL, byteArrayOf(0xff.toByte(), 0xff.toByte()).toLong())
        assertEquals(0xffffffffL, ByteArray(4) { 0xff.toByte() }.toLong())

        assertFailsWith<IllegalArgumentException> { byteArrayOf().toLong() }
        assertFailsWith<IllegalArgumentException> { ByteArray(9) { 0 }.toLong() }
    }

    @Test
    fun testEightByteRoundTrip() {
        listOf(Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE).forEach { value ->
            assertEquals(value, value.toByteArray(Long.SIZE_BYTES).toLong())
        }
    }
}
