@file:JsExport

package com.d10ng.common.calculate

import kotlin.js.JsExport
import kotlin.math.abs
import kotlin.math.roundToInt

private const val BLACK_COLOR = "#000000"
private const val WHITE_COLOR = "#FFFFFF"
private const val HEX_DIGITS = "0123456789abcdef"

/**
 * 判断十六进制 RGB 颜色是否为深色。
 *
 * 使用加权亮度阈值 `0.5` 判断颜色明暗。无法解析的颜色按黑色处理。
 *
 * @param color `#RGB` 或 `#RRGGBB` 格式的颜色字符串
 * @return 颜色为深色时返回 `true`，否则返回 `false`
 */
fun isDarkColor(color: String): Boolean {
    val rgb = parseHexColor(color) ?: 0
    val red = rgb shr 16 and 0xFF
    val green = rgb shr 8 and 0xFF
    val blue = rgb and 0xFF
    return red * 0.0008337 + green * 0.0028047 + blue * 0.0002831 <= 0.5
}

/**
 * 将颜色按指定等级向黑色或白色插值。
 *
 * 负数向黑色插值，正数向白色插值，`0.0` 返回原颜色。无法解析的颜色按黑色处理。
 *
 * @param color `#RGB` 或 `#RRGGBB` 格式的颜色字符串
 * @param level 插值等级，范围为 `-1.0..1.0`
 * @return 小写 `#rrggbb` 格式的颜色字符串
 * @throws IllegalArgumentException 当 [level] 不是有限值或超出允许范围时
 */
fun getNextLevelColor(color: String, level: Double): String {
    require(level.isFinite() && level in -1.0..1.0) {
        "level must be finite and in -1.0..1.0, but was $level"
    }
    val target = if (level > 0) WHITE_COLOR else BLACK_COLOR
    return getMiddleColor(color, target, abs(level).toFloat())
}

/**
 * 按比例计算两个十六进制 RGB 颜色之间的插值颜色。
 *
 * 无法解析的颜色按黑色处理。
 *
 * @param color1 起始颜色，格式为 `#RGB` 或 `#RRGGBB`
 * @param color2 结束颜色，格式为 `#RGB` 或 `#RRGGBB`
 * @param present 插值比例，范围为 `0.0..1.0`
 * @return 小写 `#rrggbb` 格式的颜色字符串
 * @throws IllegalArgumentException 当 [present] 不是有限值或超出允许范围时
 */
fun getMiddleColor(color1: String, color2: String, present: Float): String {
    require(present.isFinite() && present in 0.0f..1.0f) {
        "present must be finite and in 0.0..1.0, but was $present"
    }
    val start = parseHexColor(color1) ?: 0
    val end = parseHexColor(color2) ?: 0
    val red = interpolateColorChannel(start shr 16 and 0xFF, end shr 16 and 0xFF, present)
    val green = interpolateColorChannel(start shr 8 and 0xFF, end shr 8 and 0xFF, present)
    val blue = interpolateColorChannel(start and 0xFF, end and 0xFF, present)
    return formatHexColor(red, green, blue)
}

/**
 * 将十六进制 RGB 颜色解析为红、绿、蓝三个通道值。
 *
 * @param str `#RGB` 或 `#RRGGBB` 格式的颜色字符串
 * @return `[red, green, blue]`；格式无效时返回 `[0, 0, 0]`
 */
fun getRgbValueArrayFromHexColorString(str: String): Array<Int> {
    val rgb = parseHexColor(str) ?: 0
    return arrayOf(rgb shr 16 and 0xFF, rgb shr 8 and 0xFF, rgb and 0xFF)
}

/**
 * 将红、绿、蓝三个通道值转换为十六进制 RGB 颜色。
 *
 * @param rgb 依次包含红、绿、蓝通道的数组，每个值必须在 `0..255`
 * @return 小写 `#rrggbb` 格式的颜色字符串
 * @throws IllegalArgumentException 当数组长度不是 3 或任一通道超出 `0..255` 时
 */
fun getHexColorStringFromRgbValueArray(rgb: Array<Int>): String {
    require(rgb.size == 3) { "rgb must contain exactly 3 channels, but contained ${rgb.size}" }
    rgb.forEachIndexed { index, value ->
        require(value in 0..255) {
            "rgb[$index] must be in 0..255, but was $value"
        }
    }
    return formatHexColor(rgb[0], rgb[1], rgb[2])
}

private fun interpolateColorChannel(start: Int, end: Int, fraction: Float): Int {
    return ((end - start) * fraction).roundToInt() + start
}

private fun parseHexColor(color: String): Int? {
    if (!colorRegex.matches(color)) return null
    return if (color.length == 4) {
        val red = color[1].digitToInt(16)
        val green = color[2].digitToInt(16)
        val blue = color[3].digitToInt(16)
        (red * 17 shl 16) or (green * 17 shl 8) or (blue * 17)
    } else {
        var value = 0
        for (index in 1 until color.length) {
            value = (value shl 4) or color[index].digitToInt(16)
        }
        value
    }
}

private fun formatHexColor(red: Int, green: Int, blue: Int): String = buildString(7) {
    append('#')
    appendHexChannel(red)
    appendHexChannel(green)
    appendHexChannel(blue)
}

private fun StringBuilder.appendHexChannel(value: Int) {
    append(HEX_DIGITS[value ushr 4])
    append(HEX_DIGITS[value and 0x0F])
}
