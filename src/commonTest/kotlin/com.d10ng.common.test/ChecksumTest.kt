package com.d10ng.common.test

import com.d10ng.common.calculate.ChecksumType
import com.d10ng.common.calculate.addChecksum
import com.d10ng.common.calculate.assertChecksum
import com.d10ng.common.calculate.getChecksum
import kotlin.test.*

class ChecksumTest {

    @Test
    fun testAssertChecksum_PositiveCase() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x04)
        assertTrue(byteArray.assertChecksum())
    }

    @Test
    fun testAssertChecksum_NegativeCase() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x08)
        assertFalse(byteArray.assertChecksum())
    }

    @Test
    fun testAssertChecksum_EmptyArray() {
        assertFalse(byteArrayOf().assertChecksum())
        assertFalse(byteArrayOf(0x00).assertChecksum())
    }

    @Test
    fun testGetChecksum_DefaultType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum()
        assertEquals(0x04, checksum)
    }

    @Test
    fun testGetChecksum_SumType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum(ChecksumType.SUM)
        assertEquals(0x0A, checksum)
    }

    @Test
    fun testGetChecksum_OrType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum(ChecksumType.OR)
        assertEquals(0x07, checksum)
    }

    @Test
    fun testGetChecksum_SubRange() {
        val byteArray = byteArrayOf(0x7F, 0x01, 0x02, 0x03, 0x04, 0x7F)
        assertEquals(0x04, byteArray.getChecksum(start = 1, length = 4))
        assertEquals(0x00, byteArray.getChecksum(start = byteArray.size, length = 0))
    }

    @Test
    fun testGetChecksum_UsesUnsignedByteBits() {
        val byteArray = byteArrayOf(0x80.toByte(), 0x01)
        assertEquals(0x81.toByte(), byteArray.getChecksum(ChecksumType.OR))
        assertEquals(0x81.toByte(), byteArray.getChecksum(ChecksumType.XOR))
        assertEquals(0x81.toByte(), byteArray.getChecksum(ChecksumType.SUM))
    }

    @Test
    fun testGetChecksum_SumWrapsToByte() {
        assertEquals(0x00, byteArrayOf(0xFF.toByte(), 0x01).getChecksum(ChecksumType.SUM))
    }

    @Test
    fun testGetChecksumRejectsInvalidRange() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03)
        listOf(
            -1 to 1,
            4 to 0,
            0 to -1,
            1 to 3,
            Int.MAX_VALUE to Int.MAX_VALUE,
        ).forEach { (start, length) ->
            assertFailsWith<IllegalArgumentException> {
                byteArray.getChecksum(start = start, length = length)
            }
        }
    }

    @Test
    fun testAddChecksum_DefaultType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum()
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x04), result)
    }

    @Test
    fun testAddChecksum_SumType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum(ChecksumType.SUM)
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x0A), result)
    }

    @Test
    fun testAddChecksum_OrType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum(ChecksumType.OR)
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x07), result)
    }

    @Suppress("DEPRECATION")
    @Test
    fun testLegacyAndTypeUsesSumForCompatibility() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        assertEquals(
            byteArray.getChecksum(ChecksumType.SUM),
            byteArray.getChecksum(ChecksumType.AND),
        )
    }
}
