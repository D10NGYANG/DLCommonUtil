package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class NumberTest {
    @Test
    fun numbersUseTheirValueInsteadOfErasedShortType() {
        val value: Number = 0xF1FF
        assertContentEquals(byteArrayOf(0xf1.toByte(), 0xff.toByte()), value.toByteArray())
        assertContentEquals(byteArrayOf(0, 0xf1.toByte(), 0xff.toByte()), value.toByteArray(3))
        val wider: Number = 0x12345678
        assertContentEquals(byteArrayOf(0x12, 0x34, 0x56, 0x78), wider.toByteArray())
        val unsigned: Number = 4_294_967_295.0
        assertContentEquals(ByteArray(4) { -1 }, unsigned.toByteArray())
        val negative: Number = -1
        assertContentEquals(ByteArray(4) { -1 }, negative.toByteArray())
        assertContentEquals(ByteArray(8) { -1 }, negative.toByteArray(8))
        val long: Number = Long.MIN_VALUE
        assertContentEquals(byteArrayOf(0x80.toByte()) + ByteArray(7), long.toByteArray())
    }

    @Test
    fun rejectsLossyNumbersAndInvalidWidths() {
        for (value: Number in listOf(1.5, Double.NaN, Double.POSITIVE_INFINITY, 9_007_199_254_740_992.0)) {
            assertFailsWith<IllegalArgumentException> { value.toByteArray() }
        }
        val value: Number = 1
        for (width in listOf(-1, 0, 9)) {
            assertFailsWith<IllegalArgumentException> { value.toByteArray(width) }
        }
        val outOfRange: Number = js("BigInt('9223372036854775808')")
        assertFailsWith<IllegalArgumentException> { outOfRange.toByteArray() }
    }
}
