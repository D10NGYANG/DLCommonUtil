package com.d10ng.common.test

import com.d10ng.common.base.BitWriter
import com.d10ng.common.base.BufferOverflowException
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BitWriterTest {

    @Test
    fun writesBitsAcrossByteBoundaries() {
        val writer = BitWriter.allocate(2)
            .writeBits(0b101, 3)
            .writeBits(0b11, 2)
            .writeBits(0b10010, 5)

        assertContentEquals(
            byteArrayOf(0b10111100.toByte(), 0b10000000.toByte()),
            writer.toByteArray(),
        )
        assertEquals(10, writer.positionBits)
        assertEquals(6, writer.remainingBits)
    }

    @Test
    fun writesLowBitsOfSignedValues() {
        val writer = BitWriter.allocate(5)
            .writeBits(-1, 4)
            .writeBits(0x123456789abcdef0L, 36)

        assertContentEquals(
            byteArrayOf(
                0xf8.toByte(),
                0x9a.toByte(),
                0xbc.toByte(),
                0xde.toByte(),
                0xf0.toByte(),
            ),
            writer.toByteArray(),
        )
    }

    @Test
    fun wrapCopiesInputAndWritingZeroClearsExistingBits() {
        val source = byteArrayOf(0xff.toByte(), 0xff.toByte())
        val writer = BitWriter.wrap(source)
            .positionBits(3)
            .writeBits(0, 5)

        assertContentEquals(
            byteArrayOf(0xe0.toByte(), 0xff.toByte()),
            writer.toByteArray(),
        )
        assertContentEquals(
            byteArrayOf(0xff.toByte(), 0xff.toByte()),
            source,
        )
    }

    @Test
    fun writesBytesFromAnUnalignedPosition() {
        val writer = BitWriter.allocate(2)
            .writeBit(true)
            .writeBytes(byteArrayOf(0x81.toByte()))

        assertContentEquals(
            byteArrayOf(0xc0.toByte(), 0x80.toByte()),
            writer.toByteArray(),
        )
        assertEquals(9, writer.positionBits)
    }

    @Test
    fun rejectsInvalidOperationsWithoutChangingState() {
        assertFailsWith<IllegalArgumentException> { BitWriter.allocate(-1) }

        val writer = BitWriter.allocate(1).writeBits(0b1010, 4)
        val before = writer.toByteArray()

        assertFailsWith<IllegalArgumentException> { writer.writeBits(0, 33) }
        assertFailsWith<BufferOverflowException> { writer.writeBits(0, 5) }
        assertFailsWith<IllegalArgumentException> { writer.positionBits(9) }

        assertEquals(4, writer.positionBits)
        assertContentEquals(before, writer.toByteArray())
    }

    @Test
    fun rewindPreservesDataAndClearResetsIt() {
        val writer = BitWriter.allocate(1).writeBits(0xff, 8)

        writer.rewind()
        assertEquals(0, writer.positionBits)
        assertContentEquals(byteArrayOf(0xff.toByte()), writer.toByteArray())

        writer.clear()
        assertEquals(0, writer.positionBits)
        assertContentEquals(byteArrayOf(0), writer.toByteArray())
    }
}
