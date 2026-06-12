package com.d10ng.common.test

import com.d10ng.common.calculate.getHexColorStringFromRgbValueArray
import com.d10ng.common.calculate.getMiddleColor
import com.d10ng.common.calculate.getNextLevelColor
import com.d10ng.common.calculate.getRgbValueArrayFromHexColorString
import com.d10ng.common.calculate.isDarkColor
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ColorTest {

    @Test
    fun testIsDarkColor() {
        mapOf(
            "#000000" to true,
            "#FFFFFF" to false,
            "#45216B" to true,
            "#FF0000" to true,
            "#00FF00" to false,
            "#0000FF" to true,
            "#FFFF00" to false,
            "#00FFFF" to false,
            "#FF00FF" to true,
            "#C0C0C0" to false,
        ).forEach { (color, isDark) ->
            assertEquals(isDark, isDarkColor(color))
        }
        assertEquals(true, isDarkColor("invalid"))
    }

    @Test
    fun testGetNextLevelColor() {
        assertEquals(getNextLevelColor("#1488FC", 1.0), "#ffffff")
        assertEquals(getNextLevelColor("#1488FC", -1.0), "#000000")
        assertEquals(getNextLevelColor("#1488FC", 0.1), "#2c94fc")
        assertEquals(getNextLevelColor("#1488FC", 0.2), "#43a0fd")
        assertEquals(getNextLevelColor("#1488FC", 0.3), "#5bacfd")
        assertEquals(getNextLevelColor("#1488FC", 0.4), "#72b8fd")
        assertEquals(getNextLevelColor("#1488FC", 0.5), "#8ac4fe")
        assertEquals(getNextLevelColor("#1488FC", 0.6), "#a1cffe")
        assertEquals(getNextLevelColor("#1488FC", 0.7), "#b9dbfe")
        assertEquals(getNextLevelColor("#1488FC", 0.8), "#d0e7fe")
        assertEquals(getNextLevelColor("#1488FC", 0.9), "#e8f3ff")
        assertEquals(getNextLevelColor("#1488FC", -0.2), "#106dca")
        assertEquals(getNextLevelColor("#45216B", 0.5), "#a290b5")
        assertEquals("#1488fc", getNextLevelColor("#1488FC", 0.0))
    }

    @Test
    fun testGetNextLevelColorRejectsInvalidLevel() {
        listOf(Double.NaN, Double.POSITIVE_INFINITY, -1.01, 1.01).forEach { level ->
            assertFailsWith<IllegalArgumentException> {
                getNextLevelColor("#1488FC", level)
            }
        }
    }

    @Test
    fun testGetMiddleColor() {
        assertEquals("#000000", getMiddleColor("#000", "#fff", 0.0f))
        assertEquals("#808080", getMiddleColor("#000", "#fff", 0.5f))
        assertEquals("#ffffff", getMiddleColor("#000", "#fff", 1.0f))
        assertEquals("#800080", getMiddleColor("#f00", "#00f", 0.5f))
        assertEquals("#808080", getMiddleColor("invalid", "#fff", 0.5f))
    }

    @Test
    fun testGetMiddleColorRejectsInvalidPresent() {
        listOf(Float.NaN, Float.POSITIVE_INFINITY, -0.01f, 1.01f).forEach { present ->
            assertFailsWith<IllegalArgumentException> {
                getMiddleColor("#000", "#fff", present)
            }
        }
    }

    @Test
    fun testGetRgbValueArrayFromHexColorString() {
        mapOf(
            "" to arrayOf(0, 0, 0),
            "#000000" to arrayOf(0, 0, 0),
            "#FFFFFF" to arrayOf(255, 255, 255),
            "#45216B" to arrayOf(69, 33, 107),
            "#FF0000" to arrayOf(255, 0, 0),
            "#00FF00" to arrayOf(0, 255, 0),
            "#0000FF" to arrayOf(0, 0, 255),
            "#FFFF00" to arrayOf(255, 255, 0),
            "#00FFFF" to arrayOf(0, 255, 255),
            "#FF00FF" to arrayOf(255, 0, 255),
            "#C0C0C0" to arrayOf(192, 192, 192),
            "#abc" to arrayOf(170, 187, 204),
        ).forEach { (color, rgb) ->
            assertContentEquals(rgb, getRgbValueArrayFromHexColorString(color))
        }
    }

    @Test
    fun testGetHexColorStringFromRgbValueArray() {
        mapOf(
            arrayOf(0, 0, 0) to "#000000",
            arrayOf(255, 255, 255) to "#ffffff",
            arrayOf(69, 33, 107) to "#45216b",
            arrayOf(255, 0, 0) to "#ff0000",
            arrayOf(0, 255, 0) to "#00ff00",
            arrayOf(0, 0, 255) to "#0000ff",
            arrayOf(255, 255, 0) to "#ffff00",
            arrayOf(0, 255, 255) to "#00ffff",
            arrayOf(255, 0, 255) to "#ff00ff",
            arrayOf(192, 192, 192) to "#c0c0c0",
        ).forEach { (rgb, color) ->
            assertEquals(color, getHexColorStringFromRgbValueArray(rgb))
        }
    }

    @Test
    fun testGetHexColorStringRejectsInvalidRgb() {
        listOf<Array<Int>>(
            emptyArray(),
            arrayOf(0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(-1, 0, 0),
            arrayOf(0, 256, 0),
        ).forEach { rgb ->
            assertFailsWith<IllegalArgumentException> {
                getHexColorStringFromRgbValueArray(rgb)
            }
        }
    }
}
