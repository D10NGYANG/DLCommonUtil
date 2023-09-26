package com.d10ng.common.test

import com.d10ng.common.base.getBit
import com.d10ng.common.base.toBinString
import com.d10ng.common.base.toHexString
import com.d10ng.common.base.toUnsignedInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ByteTest {

    @Test
    fun testToBinString() {
        // 正常情况
        mapOf(
            0b00000000.toByte() to "00000000",
            0b00000100.toByte() to "00000100",
            0b00110011.toByte() to "00110011",
            0b10110011.toByte() to "10110011",
            0b11111111.toByte() to "11111111",
        ).forEach { (byte, binString) ->
            assertEquals(binString, byte.toBinString())
        }
    }

    @Test
    fun testToHexString() {
        // 正常情况
        mapOf(
            0x00.toByte() to "00",
            0x04.toByte() to "04",
            0x33.toByte() to "33",
            0xB3.toByte() to "B3",
            0xFF.toByte() to "FF",
        ).forEach { (byte, hexString) ->
            assertEquals(hexString, byte.toHexString())
        }
    }

    @Test
    fun testToUnsignedInt() {
        // 正常情况
        mapOf(
            0x00.toByte() to 0,
            0x04.toByte() to 4,
            0x33.toByte() to 51,
            0xB3.toByte() to 179,
            0xFF.toByte() to 255,
        ).forEach { (byte, unsignedInt) ->
            assertEquals(unsignedInt, byte.toUnsignedInt())
        }
    }

    @Test
    fun testGetBit() {
        // 正常情况
        val byte = 0b10110011.toByte()
        assertEquals(1, byte.getBit(0))
        assertEquals(1, byte.getBit(1))
        assertEquals(0, byte.getBit(2))
        assertEquals(0, byte.getBit(3))
        assertEquals(1, byte.getBit(4))
        assertEquals(1, byte.getBit(5))
        assertEquals(0, byte.getBit(6))
        assertEquals(1, byte.getBit(7))
        // 异常情况
        assertFails { byte.getBit(8) }
    }
}