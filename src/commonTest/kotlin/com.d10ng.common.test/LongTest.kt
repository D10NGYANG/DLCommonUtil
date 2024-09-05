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
        // 测试自动计算字节数的情况
        assertContentEquals(byteArrayOf(0x00), 0L.toByteArray())
        assertContentEquals(byteArrayOf(0x01), 1L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte()), 255L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00), 256L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte()), 65535L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00), 65536L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), 16777215L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00), 16777216L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), 4294967295L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00), 4294967296L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), 1099511627775L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00), 1099511627776L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), 281474976710655L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), 281474976710656L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), 72057594037927935L.toByteArray())
        assertContentEquals(byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), 72057594037927936L.toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), (-1L).toByteArray())

        // 测试指定字节数的情况
        assertContentEquals(byteArrayOf(0x01), 1L.toByteArray(1))
        assertContentEquals(byteArrayOf(0x00, 0x01), 1L.toByteArray(2))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x01), 1L.toByteArray(3))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x01), 1L.toByteArray(4))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x01), 1L.toByteArray(5))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x01), 1L.toByteArray(6))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01), 1L.toByteArray(7))
        assertContentEquals(byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01), 1L.toByteArray(8))

        // 测试负数的情况
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()), (-1L).toByteArray())
        assertContentEquals(byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xF0.toByte()), (-16L).toByteArray())
    }

    @Test
    fun testByteArrayToLong() {
        // 测试正整数
        assertEquals(0L, byteArrayOf(0x00).toLong())
        assertEquals(1L, byteArrayOf(0x01).toLong())
        assertEquals(255L, byteArrayOf(0xFF.toByte()).toLong())
        assertEquals(256L, byteArrayOf(0x01, 0x00).toLong())
        assertEquals(65535L, byteArrayOf(0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(65536L, byteArrayOf(0x01, 0x00, 0x00).toLong())
        assertEquals(16777215L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(16777216L, byteArrayOf(0x01, 0x00, 0x00, 0x00).toLong())
        assertEquals(4294967295L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(4294967296L, byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00).toLong())
        assertEquals(1099511627775L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(1099511627776L, byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00).toLong())
        assertEquals(281474976710655L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(281474976710656L, byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00).toLong())
        assertEquals(72057594037927935L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(72057594037927936L, byteArrayOf(0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00).toLong())

        // 测试负整数（补码表示）
        assertEquals(-1L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toLong())
        assertEquals(-16L, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xF0.toByte()).toLong())

        // 测试空数组和超过8字节的数组
        assertFailsWith<IllegalArgumentException> { byteArrayOf().toLong() }
        assertFailsWith<IllegalArgumentException> { ByteArray(9) { 0 }.toLong() }
    }
}