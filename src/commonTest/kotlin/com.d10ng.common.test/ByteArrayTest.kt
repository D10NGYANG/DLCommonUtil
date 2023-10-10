package com.d10ng.common.test

import com.d10ng.common.*
import com.d10ng.common.base.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ByteArrayTest {

    @Test
    fun testToBinString() {
        // 默认情况
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00) to "000000000000000000000000",
            byteArrayOf(0x00, 0x00, 0x01) to "000000000000000000000001",
            byteArrayOf(0x01, 0x23, 0x45) to "000000010010001101000101",
            byteArrayOf(0x12, 0x34, 0x56) to "000100100011010001010110",
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()) to "011111111111111111111111",
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to "111111111111111111111111",
        ).forEach { (data, binString) ->
            assertEquals(binString, data.toBinString())
        }
        // 带空格
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00) to "00000000 00000000 00000000",
            byteArrayOf(0x00, 0x00, 0x01) to "00000000 00000000 00000001",
            byteArrayOf(0x01, 0x23, 0x45) to "00000001 00100011 01000101",
            byteArrayOf(0x12, 0x34, 0x56) to "00010010 00110100 01010110",
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()) to "01111111 11111111 11111111",
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to "11111111 11111111 11111111",
        ).forEach { (data, binString) ->
            assertEquals(binString, data.toBinString(space = true))
        }
        // 单个byte
        assertEquals("00000000", byteArrayOf(0x00).toBinString())
        // 空数组
        assertEquals("", byteArrayOf().toBinString())
    }

    @Test
    fun testToHexString() {
        // 默认情况
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00) to "000000",
            byteArrayOf(0x00, 0x00, 0x01) to "000001",
            byteArrayOf(0x01, 0x23, 0x45) to "012345",
            byteArrayOf(0x12, 0x34, 0x56) to "123456",
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()) to "7FFFFF",
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to "FFFFFF",
        ).forEach { (data, hexString) ->
            assertEquals(hexString, data.toHexString())
        }
        // 带空格
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00) to "00 00 00",
            byteArrayOf(0x00, 0x00, 0x01) to "00 00 01",
            byteArrayOf(0x01, 0x23, 0x45) to "01 23 45",
            byteArrayOf(0x12, 0x34, 0x56) to "12 34 56",
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()) to "7F FF FF",
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to "FF FF FF",
        ).forEach { (data, hexString) ->
            assertEquals(hexString, data.toHexString(space = true))
        }
        // 带空格，小写
        mapOf(
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()) to "7f ff ff",
            byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to "ff ff ff",
        ).forEach { (data, hexString) ->
            assertEquals(hexString, data.toHexString(space = true, uppercase = false))
        }
        // 单个byte
        assertEquals("00", byteArrayOf(0x00).toHexString())
        // 空数组
        assertEquals("", byteArrayOf().toHexString())
    }

    @Test
    fun testToUnsignedInt() {
        // 正常情况
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00, 0x00) to 0,
            byteArrayOf(0x00, 0x00, 0x00, 0x01) to 1,
            byteArrayOf(0x01, 0x23, 0x45, 0x67) to 19088743,
            byteArrayOf(0x12, 0x34, 0x56, 0x78) to 305419896,
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to 2147483647
        ).forEach { (data, unsignedInt) ->
            assertEquals(unsignedInt, data.toUnsignedInt())
        }
        // 超过Int最大值
        assertEquals(-1, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toUnsignedInt())
        // 单个byte
        assertEquals(1, byteArrayOf(0x01).toUnsignedInt())
        // 空数组
        assertEquals(0, byteArrayOf().toUnsignedInt())
    }

    @Test
    fun testToUnsignedLong() {
        // 正常情况，8个字节
        mapOf(
            byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00) to 0L,
            byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01) to 1L,
            byteArrayOf(0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11) to 1229782938247303441L,
            byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x12, 0x34, 0x56, 0x78) to 1311768465173141112L,
            byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()) to 9223372036854775807L
        ).forEach { (data, unsignedLong) ->
            assertEquals(unsignedLong, data.toUnsignedLong())
        }
        // 超过Long最大值
        assertEquals(-1, byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()).toUnsignedInt())
        // 单个byte
        assertEquals(1, byteArrayOf(0x01).toUnsignedLong())
        // 空数组
        assertEquals(0, byteArrayOf().toUnsignedLong())
    }

    @Test
    fun testIndexOf() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        // 正常情况
        assertEquals(2, byteArray.indexOf("4567".toByteArrayFromHex()))
        // 没有情况
        assertEquals(-1, byteArray.indexOf("0000".toByteArrayFromHex()))
        // 空数组
        assertEquals(-1, byteArray.indexOf(byteArrayOf()))
        // 超过长度
        assertEquals(-1, byteArray.indexOf("0123456789012345678901".toByteArrayFromHex()))
        // 单个byte
        assertEquals(1, byteArray.indexOf(byteArrayOf(0x23)))
    }

    @Test
    fun testPadStart() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        val paddedArray = byteArray.padStart(20, 0xFF.toByte())
        assertEquals("FFFFFFFFFFFFFFFFFFFF01234567890123456789", paddedArray.toHexString())
    }

    @Test
    fun testPadEnd() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        val paddedArray = byteArray.padEnd(20, 0xFF.toByte())
        assertEquals("01234567890123456789FFFFFFFFFFFFFFFFFFFF", paddedArray.toHexString())
    }

    @Test
    fun testGetBitRange() {
        // 字节数组
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        // 正常情况
        assertEquals("0123456789", byteArray.getBitRange(0, 40).toHexString())
        assertEquals("01", byteArray.getBitRange(7, 1).toHexString())
        assertEquals("0D", byteArray.getBitRange(14, 4).toHexString())
        // 不从0开始
        assertFails {byteArray.getBitRange(-1, 40).toHexString() }
        // 获取长度为0
        assertEquals("", byteArray.getBitRange(0, 0).toHexString())
        // 超过长度
        assertFails { byteArray.getBitRange(0, 81).toHexString() }
    }
}
