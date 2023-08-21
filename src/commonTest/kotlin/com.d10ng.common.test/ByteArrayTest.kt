

package com.d10ng.common.test

import com.d10ng.common.*
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
        val byteArray = byteArrayOf(0x01, 0x23, 0x45, 0x67, 0x89.toByte())
        val searchArray = byteArrayOf(0x45, 0x67)
        val index = byteArray.indexOf(searchArray)
        assertEquals(2, index)
    }

    @Test
    fun testFillLength() {
        val byteArray = byteArrayOf(0x01, 0x23)
        val filledArray = byteArray.fillLength(4, 0xFF.toByte(), isInStart = true, isForced = true)
        assertEquals(listOf(0xFF.toByte(), 0xFF.toByte(), 0x01, 0x23.toByte()), filledArray.toList())
    }
}
