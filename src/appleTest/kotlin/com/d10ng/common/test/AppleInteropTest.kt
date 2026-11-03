package com.d10ng.common.test

import com.d10ng.common.log.LogLevel
import com.d10ng.common.log.PlatformLogIt
import com.d10ng.common.transform.*
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.create
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class AppleInteropTest {
    @Test
    fun gbkKeepsHistoricalGb18030Extension() {
        val text = "中文😀𠀀"
        val expected = byteArrayOf(
            0xd6.toByte(), 0xd0.toByte(), 0xce.toByte(), 0xc4.toByte(),
            0x94.toByte(), 0x39, 0xfc.toByte(), 0x36,
            0x95.toByte(), 0x32, 0x82.toByte(), 0x36,
        )
        assertContentEquals(expected, text.encodeGBK())
        assertEquals(text, expected.decodeGBK())
        assertContentEquals(text.encodeGB18030(), text.encodeGBK())
    }

    @Test
    fun invalidEncodingFailsInsteadOfReturningNullText() {
        for (bytes in listOf(byteArrayOf(0x81.toByte()), byteArrayOf(0xff.toByte()))) {
            assertFailsWith<IllegalArgumentException> { bytes.decodeGBK() }
            assertFailsWith<IllegalArgumentException> { bytes.decodeGB18030() }
        }
        assertEquals("null", "null".encodeGBK().decodeGBK())
        assertEquals("", byteArrayOf().decodeGBK())
    }

    @Test
    fun nsDataHandlesEmptyAndNonEmptyValues() {
        assertContentEquals(byteArrayOf(), NSData.create(bytes = null, length = 0uL).toByteArray())
        for (bytes in listOf(byteArrayOf(), byteArrayOf(0, 1, -1))) {
            assertContentEquals(bytes, bytes.toNSData().toByteArray())
        }
    }

    @Test
    fun logTreatsFormatSpecifiersAsText() {
        val message = "literal 100%% %@ %s %d"
        val result = PlatformLogIt.log(LogLevel.INFO, "format-test", message, null, true)
        assertTrue(result.endsWith(message))
    }
}
