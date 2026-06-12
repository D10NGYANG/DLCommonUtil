package com.d10ng.common.test

import com.d10ng.common.calculate.filter
import com.d10ng.common.calculate.filterByRegexStr
import com.d10ng.common.calculate.isColor
import com.d10ng.common.calculate.isEmail
import com.d10ng.common.calculate.isIdCard
import com.d10ng.common.calculate.isMobileNumber
import com.d10ng.common.calculate.isOnlyChEnNum
import com.d10ng.common.calculate.isOnlyChSymbol
import com.d10ng.common.calculate.isOnlyChinese
import com.d10ng.common.calculate.isOnlyCode
import com.d10ng.common.calculate.isOnlyEnNum
import com.d10ng.common.calculate.isOnlyLetter
import com.d10ng.common.calculate.isOnlyNumber
import com.d10ng.common.calculate.keep
import com.d10ng.common.calculate.keepByRegexStr
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class RegexTest {

    @Test
    fun testKeep() {
        assertEquals("123456", "123ABC456".keep("[0-9]+".toRegex()))
        assertEquals("ABC", "123ABC456".keepByRegexStr("[A-Z]+"))
        assertEquals("", "abc".keep("(?=a)".toRegex()))
        assertEquals("", "".keep("[0-9]+".toRegex()))
    }

    @Suppress("DEPRECATION")
    @Test
    fun testFilterCompatibilityFunctions() {
        assertEquals("ABC", "123ABC456".filter("[0-9]+".toRegex()))
        assertEquals("123456", "123ABC456".filterByRegexStr("[A-Z]+"))
    }

    @Test
    fun testDynamicRegexRejectsInvalidPattern() {
        assertFailsWith<IllegalArgumentException> {
            "value".keepByRegexStr("[")
        }
        @Suppress("DEPRECATION")
        assertFailsWith<IllegalArgumentException> {
            "value".filterByRegexStr("[")
        }
    }

    @Test
    fun testIdentityAndContactFormats() {
        assertTrue("11010519491231002X".isIdCard())
        assertTrue("11010519490231002X".isIdCard(), "The compatibility API checks format only")
        assertFalse("11010519491231002".isIdCard())

        assertTrue("13106673302".isMobileNumber())
        assertFalse("10106673302".isMobileNumber())
        assertFalse("1310667330".isMobileNumber())

        assertTrue("user123@example.com".isEmail())
        assertTrue("用户@example.com".isEmail())
        assertFalse("user+tag@example.com".isEmail())
        assertFalse("user@example".isEmail())
    }

    @Test
    fun testCharacterClassChecks() {
        assertTrue("中文123ABC".isOnlyChEnNum())
        assertTrue("123ABC".isOnlyEnNum())
        assertTrue("中文".isOnlyChinese())
        assertTrue("ABCxyz".isOnlyLetter())
        assertTrue("0123456789".isOnlyNumber())
        assertTrue("0123ABCdef".isOnlyCode())
        assertTrue("中文，。".isOnlyChSymbol())

        assertFalse("".isOnlyChEnNum())
        assertFalse("中文_123".isOnlyChEnNum())
        assertFalse("１２３".isOnlyNumber())
        assertFalse("G".isOnlyCode())
        assertFalse("abc".isOnlyChSymbol())
    }

    @Test
    fun testColorFormat() {
        assertTrue("#abc".isColor())
        assertTrue("#ABCDEF".isColor())
        assertFalse("ABCDEF".isColor())
        assertFalse("#abcd".isColor())
        assertFalse("#GGGGGG".isColor())
    }
}
