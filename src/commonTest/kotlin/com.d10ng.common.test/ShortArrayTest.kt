package com.d10ng.common.test

import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toShortArray
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ShortArrayTest {

    @Test
    fun testByteArrayToShortArray() {
        // 准备测试数据
        val byteArray = byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte(), 0xBC.toByte())
        val expectedShortArray = shortArrayOf(0x3412, 0x7856, 0xBC9A.toShort())
        // 执行测试
        assertContentEquals(expectedShortArray, byteArray.toShortArray())
    }

    @Test
    fun testShortArrayToByteArray() {
        // 准备测试数据
        val shortArray = shortArrayOf(0x3412, 0x7856, 0xBC9A.toShort())
        val expectedByteArray = byteArrayOf(0x12, 0x34, 0x56, 0x78, 0x9A.toByte(), 0xBC.toByte())
        // 执行测试
        assertContentEquals(expectedByteArray, shortArray.toByteArray())
    }
}