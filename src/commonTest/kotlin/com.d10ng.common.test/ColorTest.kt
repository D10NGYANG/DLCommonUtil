package com.d10ng.common.test

import com.d10ng.common.calculate.getHexColorStringFromRgbValueArray
import com.d10ng.common.calculate.getNextLevelColor
import com.d10ng.common.calculate.getRgbValueArrayFromHexColorString
import com.d10ng.common.calculate.isDarkColor
import kotlin.test.Test
import kotlin.test.assertEquals

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
            assertEquals(isDarkColor(color), isDark)
        }
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
    }

    @Test
    fun testGetRgbValueArrayFromHexColorString() {
        val intArrayToString = { array: Array<Int> -> array.joinToString(",") }
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
        ).forEach { (color, rgb) ->
            assertEquals(intArrayToString(rgb), intArrayToString(getRgbValueArrayFromHexColorString(color)))
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
}