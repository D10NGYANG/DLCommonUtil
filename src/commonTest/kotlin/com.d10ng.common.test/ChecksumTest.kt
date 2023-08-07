package com.d10ng.common.test

import com.d10ng.common.ChecksumType
import com.d10ng.common.addChecksum
import com.d10ng.common.assertChecksum
import com.d10ng.common.getChecksum
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
        val byteArray = byteArrayOf()
        assertFalse(byteArray.assertChecksum())
    }

    @Test
    fun testGetChecksum_DefaultType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum()
        assertEquals(0x04, checksum)
    }

    @Test
    fun testGetChecksum_AndType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum(ChecksumType.AND)
        assertEquals(0x0A, checksum)
    }

    @Test
    fun testGetChecksum_OrType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val checksum = byteArray.getChecksum(ChecksumType.OR)
        assertEquals(0x07, checksum)
    }

    @Test
    fun testAddChecksum_DefaultType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum()
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x04), result)
    }

    @Test
    fun testAddChecksum_AndType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum(ChecksumType.AND)
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x0A), result)
    }

    @Test
    fun testAddChecksum_OrType() {
        val byteArray = byteArrayOf(0x01, 0x02, 0x03, 0x04)
        val result = byteArray.addChecksum(ChecksumType.OR)
        assertContentEquals(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x07), result)
    }
}