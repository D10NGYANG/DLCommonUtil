package com.d10ng.common.test

import com.d10ng.common.base.*
import kotlin.test.Test
import kotlin.test.assertEquals

class StringTest {

    @Test
    fun testToByteFromBin() {
        // 正常情况
        mapOf(
            "00000000" to 0x00.toByte(),
            "00000001" to 0x01.toByte(),
            "00010010" to 0x12.toByte(),
            "01111111" to 0x7F.toByte(),
            "11111111" to 0xFF.toByte(),
        ).forEach { (binString, byte) ->
            assertEquals(byte, binString.toByteFromBin())
        }
        // 带空格
        mapOf(
            "0000 0000" to 0x00.toByte(),
            "0000 0001" to 0x01.toByte(),
            "0001 0010" to 0x12.toByte(),
            "0111 1111" to 0x7F.toByte(),
            "1111 1111" to 0xFF.toByte(),
        ).forEach { (binString, byte) ->
            assertEquals(byte, binString.toByteFromBin())
        }
        // 空字符串
        assertEquals(0x00.toByte(), "".toByteFromBin())
        // 非法字符串
        assertEquals(0x00.toByte(), "非法字符串".toByteFromBin())
    }

    @Test
    fun testToByteArrayFromBin() {
        // 正常情况
        mapOf(
            "00000000" to byteArrayOf(0x00),
            "00000001" to byteArrayOf(0x01),
            "00010010" to byteArrayOf(0x12),
            "01111111" to byteArrayOf(0x7F),
            "11111111" to byteArrayOf(0xFF.toByte()),
            "00000000 00000000" to byteArrayOf(0x00, 0x00),
            "00000000 00000001" to byteArrayOf(0x00, 0x01),
            "00010010 00110100" to byteArrayOf(0x12, 0x34),
            "01111111 11111111" to byteArrayOf(0x7F, 0xFF.toByte()),
            "11111111 11111111" to byteArrayOf(0xFF.toByte(), 0xFF.toByte()),
            "00000000 00000000 00000000" to byteArrayOf(0x00, 0x00, 0x00),
            "00000000 00000000 00000001" to byteArrayOf(0x00, 0x00, 0x01),
            "00010010 00110100 01010110" to byteArrayOf(0x12, 0x34, 0x56),
            "01111111 11111111 11111111" to byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()),
            "11111111 11111111 11111111" to byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()),
        ).forEach { (binString, byteArray) ->
            assertEquals(byteArray.toHexString(), binString.toByteArrayFromBin().toHexString())
        }
        // 空字符串
        assertEquals(byteArrayOf().toHexString(), "".toByteArrayFromBin().toHexString())
        // 非法字符串
        assertEquals(byteArrayOf().toHexString(), "非法字符串".toByteArrayFromBin().toHexString())
    }

    @Test
    fun testToByteFromHex() {
        // 正常情况
        mapOf(
            "00" to 0x00.toByte(),
            "01" to 0x01.toByte(),
            "12" to 0x12.toByte(),
            "7F" to 0x7F.toByte(),
            "FF" to 0xFF.toByte(),
        ).forEach { (hexString, byte) ->
            assertEquals(byte, hexString.toByteFromHex())
        }
        // 空字符串
        assertEquals(0x00.toByte(), "".toByteFromHex())
        // 非法字符串
        assertEquals(0x00.toByte(), "非法字符串".toByteFromHex())
    }

    @Test
    fun testToByteArrayFromHex() {
        // 正常情况
        mapOf(
            "00" to byteArrayOf(0x00),
            "01" to byteArrayOf(0x01),
            "12" to byteArrayOf(0x12),
            "7F" to byteArrayOf(0x7F),
            "FF" to byteArrayOf(0xFF.toByte()),
            "0000" to byteArrayOf(0x00, 0x00),
            "0001" to byteArrayOf(0x00, 0x01),
            "1234" to byteArrayOf(0x12, 0x34),
            "7FFF" to byteArrayOf(0x7F, 0xFF.toByte()),
            "FFFF" to byteArrayOf(0xFF.toByte(), 0xFF.toByte()),
            "000000" to byteArrayOf(0x00, 0x00, 0x00),
            "000001" to byteArrayOf(0x00, 0x00, 0x01),
            "123456" to byteArrayOf(0x12, 0x34, 0x56),
            "7FFFFF" to byteArrayOf(0x7F, 0xFF.toByte(), 0xFF.toByte()),
            "FFFFFF" to byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte()),
        ).forEach { (hexString, byteArray) ->
            assertEquals(byteArray.toHexString(), hexString.toByteArrayFromHex().toHexString())
        }
        // 空字符串
        assertEquals(byteArrayOf().toHexString(), "".toByteArrayFromHex().toHexString())
        // 非法字符串
        assertEquals(byteArrayOf().toHexString(), "非法字符串".toByteArrayFromHex().toHexString())
    }

    @Test
    fun testGetByteLength() {
        mapOf(
            "" to 0,
            "1" to 1,
            "12" to 2,
            "123" to 3,
            "hanzi" to 5,
            "汉字" to 4,
            "汉字123" to 7,
            "汉字123abc" to 10,
            "汉字123abc,." to 12,
        ).forEach { (str, length) ->
            assertEquals(length, str.getByteLength())
        }
    }

    @Test
    fun testGetFirstUpperCase() {
        mapOf(
            "" to "",
            "a" to "A",
            "A" to "A",
            "abc" to "A",
            "Abc" to "A",
            "我的" to "我",
        ).forEach { (str, firstUpperCase) ->
            assertEquals(firstUpperCase, str.getFirstUpperCase())
        }
    }

    @Test
    fun testToFullWidthString() {
        mapOf(
            "123" to "１２３",
            "a" to "ａ",
            "A" to "Ａ",
            "abc" to "ａｂｃ",
            "Abc" to "Ａｂｃ",
            "我de" to "我ｄｅ",
        ).forEach { (str, fullWidthString) ->
            assertEquals(fullWidthString, str.toFullWidthString())
        }
    }
}