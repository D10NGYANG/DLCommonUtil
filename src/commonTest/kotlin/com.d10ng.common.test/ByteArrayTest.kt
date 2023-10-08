

package com.d10ng.common.test

import com.d10ng.common.*
import com.d10ng.common.base.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ByteArrayTest {

    @Test
    fun testToUnsignedInt() {
        val byteArray = byteArrayOf(0x00, 0xFF.toByte())
        val unsignedInt = byteArray.toUnsignedInt()
        assertEquals(255, unsignedInt)
    }

    @Test
    fun testToUnsignedLong() {
        val byteArray = byteArrayOf(0x7F.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        val unsignedLong = byteArray.toUnsignedLong()
        assertEquals(36028797018963967L, unsignedLong)
    }

    @Test
    fun testToBinString() {
        val byteArray = byteArrayOf(0x01, 0x23, 0x45)
        val binString = byteArray.toBinString(space = true)
        assertEquals("00000001 00100011 01000101", binString)
    }

    @Test
    fun testToHexString() {
        val byteArray = byteArrayOf(0xFC.toByte(), 0xFE.toByte())
        val hexString = byteArray.toHexString(space = true, uppercase = true)
        assertEquals("FC FE", hexString)
    }

    @Test
    fun testIndexOf() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        assertEquals(2, byteArray.indexOf("4567".toByteArrayFromHex()))
        assertEquals(-1, byteArray.indexOf("0000".toByteArrayFromHex()))
    }

    @Test
    fun testPadStart() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        val paddedArray = byteArray.padStart(20, 0xFF.toByte())
        assertEquals("FFFFFFFFFFFFFFFFFFFF01234567890123456789", paddedArray.toHexString(false, uppercase = true))
    }

    @Test
    fun testPadEnd() {
        val byteArray = "01234567890123456789".toByteArrayFromHex()
        val paddedArray = byteArray.padEnd(20, 0xFF.toByte())
        assertEquals("01234567890123456789FFFFFFFFFFFFFFFFFFFF", paddedArray.toHexString(false, uppercase = true))
    }
}
