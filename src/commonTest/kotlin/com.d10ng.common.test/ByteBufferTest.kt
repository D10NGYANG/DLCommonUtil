package com.d10ng.common.test

import com.d10ng.common.base.BufferOverflowException
import com.d10ng.common.base.BufferUnderflowException
import com.d10ng.common.base.ByteBuffer
import com.d10ng.common.base.ByteOrder
import com.d10ng.common.base.getBitRange
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ByteBufferTest {

    @Test
    fun testAllocateWrapAndBounds() {
        assertFailsWith<IllegalArgumentException> { ByteBuffer.allocate(-1) }

        val source = byteArrayOf(1, 2, 3)
        val buffer = ByteBuffer.wrap(source)
        source[0] = 9

        assertEquals(3, buffer.capacity())
        assertEquals(0, buffer.position())
        assertEquals(3, buffer.limit())
        assertEquals(1, buffer.get())
        assertFailsWith<IllegalArgumentException> { buffer.position(4) }
        assertFailsWith<IllegalArgumentException> { buffer.limit(4) }
    }

    @Test
    fun testBulkGetUsesDestinationOffset() {
        val buffer = ByteBuffer.wrap(byteArrayOf(1, 2, 3, 4))
        val destination = byteArrayOf(9, 9, 9, 9, 9)

        buffer.get(destination, offset = 1)

        assertContentEquals(byteArrayOf(9, 1, 2, 3, 4), destination)
        assertEquals(4, buffer.position())
    }

    @Test
    fun testBulkGetRejectsInvalidRangeWithoutChangingState() {
        val buffer = ByteBuffer.wrap(byteArrayOf(1, 2, 3))
        val destination = byteArrayOf(9, 9)

        assertFailsWith<IndexOutOfBoundsException> {
            buffer.get(destination, offset = 1, length = 2)
        }
        assertEquals(0, buffer.position())
        assertContentEquals(byteArrayOf(9, 9), destination)

        assertFailsWith<BufferUnderflowException> {
            buffer.get(destination, offset = 0, length = 2)
                .get(destination, offset = 0, length = 2)
        }
        assertEquals(2, buffer.position())
    }

    @Test
    fun testBulkPutUsesSourceOffset() {
        val buffer = ByteBuffer.allocate(4)

        buffer.put(byteArrayOf(9, 1, 2, 3, 4), offset = 1)

        assertEquals(4, buffer.position())
        assertContentEquals(byteArrayOf(1, 2, 3, 4), buffer.array())
    }

    @Test
    fun testBulkPutRejectsInvalidRangeWithoutChangingState() {
        val buffer = ByteBuffer.allocate(2)
        val source = byteArrayOf(1, 2, 3)

        assertFailsWith<IndexOutOfBoundsException> {
            buffer.put(source, offset = 2, length = 2)
        }
        assertEquals(0, buffer.position())
        assertContentEquals(byteArrayOf(0, 0), buffer.array())

        assertFailsWith<BufferOverflowException> {
            buffer.put(source)
        }
        assertEquals(0, buffer.position())
        assertContentEquals(byteArrayOf(0, 0), buffer.array())
    }

    @Test
    fun testBigEndianPrimitiveRoundTrip() {
        val buffer = ByteBuffer.allocate(18)
            .putShort(0x1234.toShort())
            .putInt(0x12345678)
            .putFloat(3.25f)
            .putDouble(-6.5)
            .flip()

        assertEquals(0x1234.toShort(), buffer.getShort())
        assertEquals(0x12345678, buffer.getInt())
        assertEquals(3.25f, buffer.getFloat())
        assertEquals(-6.5, buffer.getDouble())
        assertEquals(0, buffer.remaining())
    }

    @Test
    fun testLittleEndianPrimitiveLayoutAndRoundTrip() {
        val buffer = ByteBuffer.allocate(14).order(ByteOrder.LITTLE_ENDIAN)
            .putShort(0x1234.toShort())
            .putInt(0x12345678)
            .putDouble(1.0)

        assertContentEquals(
            byteArrayOf(
                0x34,
                0x12,
                0x78,
                0x56,
                0x34,
                0x12,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0xf0.toByte(),
                0x3f,
            ),
            buffer.array(),
        )

        buffer.flip()
        assertEquals(0x1234.toShort(), buffer.getShort())
        assertEquals(0x12345678, buffer.getInt())
        assertEquals(1.0, buffer.getDouble())
    }

    @Test
    fun testAbsoluteAccessKeepsPosition() {
        val buffer = ByteBuffer.allocate(8).position(3)

        buffer.putInt(0, 0x12345678)
        assertEquals(3, buffer.position())
        assertEquals(0x12345678, buffer.getInt(0))
        assertEquals(3, buffer.position())

        assertFailsWith<IndexOutOfBoundsException> { buffer.getInt(5) }
        assertEquals(3, buffer.position())
    }

    @Test
    fun testCompactMovesRemainingBytes() {
        val buffer = ByteBuffer.wrap(byteArrayOf(1, 2, 3, 4))
        assertEquals(1, buffer.get())
        assertEquals(2, buffer.get())

        buffer.compact()

        assertEquals(2, buffer.position())
        assertEquals(4, buffer.limit())
        assertContentEquals(byteArrayOf(3, 4, 3, 4), buffer.array())
    }

    @Test
    fun testGetBitRangeDoesNotChangePosition() {
        val buffer = ByteBuffer.wrap(byteArrayOf(0x01, 0x23, 0x45))
        buffer.position(2)

        assertContentEquals(byteArrayOf(0x0d), buffer.getBitRange(14, 4))
        assertContentEquals(byteArrayOf(0xd0.toByte()), buffer.getBitRange(14, 4, false))
        assertEquals(2, buffer.position())
        assertFailsWith<IllegalArgumentException> { buffer.getBitRange(Int.MAX_VALUE, 1) }
        assertEquals(2, buffer.position())
    }

    @Test
    fun testNegativeGetSizeAndPrimitiveUnderflow() {
        val buffer = ByteBuffer.allocate(1)

        assertFailsWith<IllegalArgumentException> { buffer.getBytes(-1) }
        assertFailsWith<BufferUnderflowException> { buffer.getInt() }
        assertEquals(0, buffer.position())
    }
}
