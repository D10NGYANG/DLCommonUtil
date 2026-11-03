package com.d10ng.common.test

import com.d10ng.common.base.getBitRange
import java.nio.ByteBuffer
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NioByteBufferTest {
    @Test
    fun readsLogicalViewOfHeapDirectAndReadOnlyBuffers() {
        val heap = ByteBuffer.wrap(byteArrayOf(0x11, 0x53, 0x66)).apply {
            position(1)
            limit(2)
        }.slice()
        val direct = ByteBuffer.allocateDirect(1).apply { put(0, 0x53) }
        for (buffer in listOf(heap, heap.asReadOnlyBuffer(), direct, direct.asReadOnlyBuffer())) {
            buffer.position(1)
            assertContentEquals(byteArrayOf(0x53), buffer.getBitRange(0, 8))
            assertContentEquals(byteArrayOf(0x09), buffer.getBitRange(3, 4))
            assertContentEquals(byteArrayOf(0x90.toByte()), buffer.getBitRange(3, 4, false))
            assertEquals(1, buffer.position())
            assertContentEquals(byteArrayOf(), buffer.getBitRange(8, 0))
            assertFailsWith<IllegalArgumentException> { buffer.getBitRange(0, 9) }
            assertFailsWith<IllegalArgumentException> { buffer.getBitRange(-1, 1) }
            assertFailsWith<IllegalArgumentException> { buffer.getBitRange(0, -1) }
            assertFailsWith<IllegalArgumentException> { buffer.getBitRange(Int.MAX_VALUE, Int.MAX_VALUE) }
            assertEquals(1, buffer.position())
        }
    }

    @Test
    fun respectsLimitAndPreservesMark() {
        val buffer = ByteBuffer.wrap(byteArrayOf(0x12, 0x34, 0x56)).apply {
            limit(2)
            position(1)
            mark()
        }
        assertContentEquals(byteArrayOf(0x23), buffer.getBitRange(4, 8))
        assertFailsWith<IllegalArgumentException> { buffer.getBitRange(16, 1) }
        buffer.reset()
        assertEquals(1, buffer.position())
    }
}
