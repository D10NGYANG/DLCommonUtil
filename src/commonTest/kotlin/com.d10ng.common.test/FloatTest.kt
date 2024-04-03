package com.d10ng.common.test

import com.d10ng.common.base.toStringWithMaxDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FloatTest {

    @Test
    fun testToString() {
        // 正常情况
        val value = 0.1234567f
        assertEquals("0.1", value.toStringWithMaxDecimal(1))
        assertEquals("0.12", value.toStringWithMaxDecimal(2))
        assertEquals("0.123", value.toStringWithMaxDecimal(3))
        assertEquals("0.1234", value.toStringWithMaxDecimal(4))
        assertEquals("0.12345", value.toStringWithMaxDecimal(5))
        assertEquals("0.1234567", value.toStringWithMaxDecimal(7))
        assertEquals("0.1234567", value.toStringWithMaxDecimal(8))

        // 边界情况
        assertEquals("0", value.toStringWithMaxDecimal(0))

        // 异常情况
        assertFails { value.toStringWithMaxDecimal(-1) }
    }
}