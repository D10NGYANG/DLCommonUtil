package com.d10ng.common.test

import com.d10ng.common.toBinString
import com.d10ng.common.toHexString
import com.d10ng.common.toUnsignedInt
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteTest {

    @Test
    fun testToBinString() {
        val byte: Byte = 0x33
        val binString = byte.toBinString()
        assertEquals("00110011", binString)
    }

    @Test
    fun testToHexString() {
        val byte: Byte = 0xFC.toByte()
        val hexString = byte.toHexString(uppercase = true)
        assertEquals("FC", hexString)
    }

    @Test
    fun testToUnsignedInt() {
        val byte: Byte = (-1).toByte()
        val unsignedInt = byte.toUnsignedInt()
        assertEquals(255, unsignedInt)
    }
}