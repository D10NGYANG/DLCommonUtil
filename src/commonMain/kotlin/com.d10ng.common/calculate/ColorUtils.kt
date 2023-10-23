@file:JsExport
package com.d10ng.common.calculate

import kotlin.js.JsExport
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * 判断颜色是否为深色
 * @param color [String] 颜色值，如#45216B
 * @return [Boolean] true为深色，false为浅色
 */
fun isDarkColor(color: String): Boolean {
    val rgb = getRgbValueArrayFromHexColorString(color)
    return (rgb[0] * 0.0008337 + rgb[1] * 0.0028047 + rgb[2] * 0.0002831) <= 0.5
}

/**
 * 获取颜色的下一个等级颜色，或深色或浅色
 * @param color [String] 颜色值，如#45216B
 * @param level [Double] -1.0～1.0，当为-1.0时，返回纯黑色，当为1.0时，返回纯白色
 * @return [String] 颜色值，如#45216B
 */
fun getNextLevelColor(color: String, level: Double): String {
    val target = if (level > 0) "#FFFFFF" else "#000000"
    return getMiddleColor(color, target, abs(level).toFloat())
}

/**
 * 从两个颜色间根据比例获得中间颜色
 * @param color1 [String] 16进制颜色值，如#000000、#000
 * @param color2 [String] 16进制颜色值，如#000000、#000
 * @param present [Float] 比例，0-1
 * @return [String] 16进制颜色值，如#000000、#000
 */
fun getMiddleColor(color1: String, color2: String, present: Float): String {
    val startColorNumbers = getRgbValueArrayFromHexColorString(color1)
    val endColorNumbers = getRgbValueArrayFromHexColorString(color2)
    val redRange = endColorNumbers[0] - startColorNumbers[0]
    val greenRange = endColorNumbers[1] - startColorNumbers[1]
    val blueRange = endColorNumbers[2] - startColorNumbers[2]
    val red = (redRange * present).roundToInt() + startColorNumbers[0]
    val green = (greenRange * present).roundToInt() + startColorNumbers[1]
    val blue = (blueRange * present).roundToInt() + startColorNumbers[2]
    return getHexColorStringFromRgbValueArray(arrayOf(red, green, blue))
}

/**
 * 从16进制颜色值中提取红绿蓝的值
 * @param str [String] 16进制颜色值，如#000000、#000
 * @return [Array<Int>] [red, green, blue]，如果不是16进制颜色值则返回[0, 0, 0]，每个颜色值范围为0-255
 */
fun getRgbValueArrayFromHexColorString(str: String): Array<Int> {
    if (!colorRegex.matches(str)) return arrayOf(0, 0, 0)
    var hexColor = str.lowercase()
    if (str.length == 4) {
        var t = "#"
        for (i in 1 until hexColor.length) {
            t += hexColor[i].toString() + hexColor[i].toString()
        }
        hexColor = t
    }
    val arr = mutableListOf<Int>()
    for (i in 1 until hexColor.length step 2) {
        val s = hexColor.slice(i until i + 2)
        arr.add(s.toUInt(16).toInt())
    }
    return arr.toTypedArray()
}

/**
 * 从红绿蓝的值获取16进制颜色值
 * @param rgb [Array<Int>] [red, green, blue]，每个颜色值范围为0-255
 * @return [String] 16进制颜色值，如#000000
 */
fun getHexColorStringFromRgbValueArray(rgb: Array<Int>): String {
    if (rgb.size != 3) return "#000000"
    val getHex = { num: Int ->
        num.toString(16).padStart(2, '0')
    }
    return "#${getHex(rgb[0])}${getHex(rgb[1])}${getHex(rgb[2])}"
}
