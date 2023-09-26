package com.d10ng.common.test

import com.d10ng.common.base.toString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FloatTest {

    @Test
    fun testToString() {
        // 正常情况
        val value = 0.1234567f
        assertEquals("0.1", value.toString(1))
        assertEquals("0.12", value.toString(2))
        assertEquals("0.123", value.toString(3))
        assertEquals("0.1234", value.toString(4))
        assertEquals("0.12345", value.toString(5))
        assertEquals("0.1234567", value.toString(7))
        assertEquals("0.1234567", value.toString(8))

        // 边界情况
        assertEquals("0", value.toString(0))

        // 异常情况
        assertFails { value.toString(-1) }
    }
}