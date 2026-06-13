@file:JsExport
package com.d10ng.common.coordinate

import com.d10ng.common.coordinate.CoordinateConvert.transformBD09ToGCJ02
import com.d10ng.common.coordinate.CoordinateConvert.transformBD09ToWGS84
import com.d10ng.common.coordinate.CoordinateConvert.transformGCJ02ToBD09
import com.d10ng.common.coordinate.CoordinateConvert.transformGCJ02ToWGS84
import com.d10ng.common.coordinate.CoordinateConvert.transformWGS84ToBD09
import com.d10ng.common.coordinate.CoordinateConvert.transformWGS84ToGCJ02
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.math.pow
import kotlin.math.roundToLong

private const val DEFAULT_COORDINATE_PATTERN = "CHFd°m′S.ss″"

private fun Double.requireLongitudeValue(name: String = "longitude") {
    require(isFinite() && this in -180.0..360.0) {
        "$name must be finite and in -180.0..360.0, but was $this"
    }
}

private fun Double.requireLatitudeValue(name: String = "latitude") {
    require(isFinite() && this in -90.0..180.0) {
        "$name must be finite and in -90.0..180.0, but was $this"
    }
}

private fun Coordinate.requireConvertible() {
    require(lat.isFinite() && lat in -90.0..90.0) {
        "lat must be finite and in -90.0..90.0, but was $lat"
    }
    require(lng.isFinite() && lng in -180.0..180.0) {
        "lng must be finite and in -180.0..180.0, but was $lng"
    }
}

/**
 * 在支持的地理坐标系之间转换坐标。
 *
 * @receiver 源坐标，纬度必须在 `-90.0..90.0`，经度必须在 `-180.0..180.0`。
 * @param from 源坐标系。
 * @param to 目标坐标系。
 * @return 转换后的新坐标；坐标系相同时返回当前坐标的副本。
 * @throws IllegalArgumentException 当坐标不是有限值或超出经纬度范围时。
 */
fun Coordinate.convert(from: CoordinateSystemType, to: CoordinateSystemType): Coordinate {
    requireConvertible()
    return when(from) {
        CoordinateSystemType.WGS84 -> {
            when(to) {
                CoordinateSystemType.GCJ02 -> transformWGS84ToGCJ02(lng, lat).let { Coordinate(it[1], it[0]) }
                CoordinateSystemType.BD09 -> transformWGS84ToBD09(lng, lat).let { Coordinate(it[1], it[0]) }
                else -> this.copy()
            }
        }
        CoordinateSystemType.GCJ02 -> {
            when(to) {
                CoordinateSystemType.WGS84 -> transformGCJ02ToWGS84(lng, lat).let { Coordinate(it[1], it[0]) }
                CoordinateSystemType.BD09 -> transformGCJ02ToBD09(lng, lat).let { Coordinate(it[1], it[0]) }
                else -> this.copy()
            }
        }
        CoordinateSystemType.BD09 -> {
            when(to) {
                CoordinateSystemType.WGS84 -> transformBD09ToWGS84(lng, lat).let { Coordinate(it[1], it[0]) }
                CoordinateSystemType.GCJ02 -> transformBD09ToGCJ02(lng, lat).let { Coordinate(it[1], it[0]) }
                else -> this.copy()
            }
        }
    }
}

/**
 * 判断当前经度表示是否属于东经。
 *
 * @receiver 经度；支持 `[-180, 180]` 和 `[0, 360]` 两种表示。
 * @return 位于 `0.0..180.0` 时返回 `true`，其他值返回 `false`。
 */
fun Double.isEastLongitude(): Boolean = this in 0.0..180.0

/**
 * 判断当前纬度表示是否属于北纬。
 *
 * @receiver 纬度；支持 `[-90, 90]` 和 `[0, 180]` 两种表示。
 * @return 位于 `0.0..90.0` 时返回 `true`，其他值返回 `false`。
 */
fun Double.isNorthLatitude(): Boolean = this in 0.0..90.0

/**
 * 将带方向的经度表示转换为不带方向的绝对度数。
 *
 * 负值按西经解释，`(180, 360]` 的值按正数西经表示解释。
 *
 * @receiver `[-180, 360]` 范围内的经度。
 * @return `[0, 180]` 范围内的绝对经度。
 * @throws IllegalArgumentException 当值不是有限值或超出支持范围时。
 */
fun Double.toLongitudeNoPre(): Double {
    requireLongitudeValue()
    val base = if (this > 180) 360 else if (this < 0) 0 else return this
    return base - this
}

/**
 * 将带方向的纬度表示转换为不带方向的绝对度数。
 *
 * 负值按南纬解释，`(90, 180]` 的值按正数南纬表示解释。
 *
 * @receiver `[-90, 180]` 范围内的纬度。
 * @return `[0, 90]` 范围内的绝对纬度。
 * @throws IllegalArgumentException 当值不是有限值或超出支持范围时。
 */
fun Double.toLatitudeNoPre(): Double {
    requireLatitudeValue()
    val base = if (this > 90) 180 else if (this < 0) 0 else return this
    return base - this
}

/**
 * 根据方向将经度转换为完整表示。
 *
 * @receiver `[-180, 360]` 范围内的经度或绝对经度。
 * @param isEast `true` 表示东经，`false` 表示西经。
 * @param isPositive 西经是否使用 `(180, 360]` 的正数表示；为 `false` 时使用负数表示。
 * @return 带方向的经度。
 * @throws IllegalArgumentException 当接收值无效时。
 */
fun Double.toFullLongitude(isEast: Boolean, isPositive: Boolean = true): Double {
    requireLongitudeValue()
    if (!this.isEastLongitude()) {
        // 当前输入的数据，是一个西经完整数据
        if (isPositive) {
            // 输出要求正值
            return if (this > 0) {
                // 输入数据是正值
                this
            } else {
                // 输入数据是负值
                360 + this
            }
        } else {
            // 输出要求负值
            return if (this > 0) {
                // 输入数据是正值
                this - 360
            } else {
                // 输入数据是负值
                this
            }
        }
    } else if (isEast) {
        // 当前输入的数据，是一个东经完整数据
        return this
    } else {
        // 当前输入的数据，是一个西经非完整数据
        return if (isPositive) {
            // 输出要求正值
            360 - this
        } else {
            // 输出要求负值
            - this
        }
    }
}

/**
 * 根据方向将纬度转换为完整表示。
 *
 * @receiver `[-90, 180]` 范围内的纬度或绝对纬度。
 * @param isNorth `true` 表示北纬，`false` 表示南纬。
 * @param isPositive 南纬是否使用 `(90, 180]` 的正数表示；为 `false` 时使用负数表示。
 * @return 带方向的纬度。
 * @throws IllegalArgumentException 当接收值无效时。
 */
fun Double.toFullLatitude(isNorth: Boolean, isPositive: Boolean = true): Double {
    requireLatitudeValue()
    if (!this.isNorthLatitude()) {
        // 当前输入的数据，是一个南纬完整数据
        if (isPositive) {
            // 输出要求正值
            return if (this > 0) {
                // 输入数据是正值
                this
            } else {
                // 输入数据是负值
                180 + this
            }
        } else {
            // 输出要求负值
            return if (this > 0) {
                // 输入数据是正值
                this - 180
            } else {
                // 输入数据是负值
                this
            }
        }
    } else if (isNorth) {
        // 当前输入的数据，是一个北纬完整数据
        return this
    } else {
        // 当前输入的数据，是一个南纬非完整数据
        return if (isPositive) {
            // 输出要求正值
            180 - this
        } else {
            // 输出要求负值
            - this
        }
    }
}

/**
 * 将经纬度转换为保留两位小数秒的度分秒值。
 *
 * 四舍五入导致秒达到 60 时会自动向分、度进位。
 *
 * @receiver 经度或纬度。
 * @param isLongitude `true` 表示经度，`false` 表示纬度。
 * @return 不带方向的度分秒值。
 * @throws IllegalArgumentException 当接收值无效时。
 */
fun Double.toDMS(isLongitude: Boolean): DMS {
    val value = if (isLongitude) toLongitudeNoPre() else toLatitudeNoPre()
    val totalCentiseconds = (value * 360_000.0).roundToLong()
    val degrees = (totalCentiseconds / 360_000L).toInt()
    val remainder = totalCentiseconds % 360_000L
    val minutes = (remainder / 6_000L).toInt()
    val seconds = (remainder % 6_000L) / 100.0f
    return DMS(degrees, minutes, seconds)
}

/**
 * 将度分秒值转换为十进制度。
 *
 * @receiver 度分秒值。
 * @return 十进制度。
 * @throws IllegalArgumentException 当分或秒不是有效范围时。
 */
fun DMS.toLatLng(): Double {
    require(minutes in 0..59) { "minutes must be in 0..59, but was $minutes" }
    require(seconds.isFinite() && seconds >= 0.0f && seconds < 60.0f) {
        "seconds must be finite and in [0, 60), but was $seconds"
    }
    val sign = if (degrees < 0) -1.0 else 1.0
    return sign * (kotlin.math.abs(degrees.toDouble()) + minutes / 60.0 + seconds / 3600.0)
}

private fun String.maxFractionDigits(): Int {
    var maxDigits = 0
    var index = 0
    while (index < length) {
        if (this[index] != 's') {
            index++
            continue
        }
        val start = index
        while (index < length && this[index] == 's') index++
        maxDigits = maxOf(maxDigits, index - start)
    }
    return maxDigits
}

/**
 * 将经纬度格式化为方向和度分秒文本。
 *
 * 模板标记：`d` 为度、`m` 为分、`S` 为整数秒、`s` 为小数秒、`F` 为英文方向，
 * `CH` 为中文方向。重复的数值标记表示最小输出宽度。
 *
 * @receiver 经度或纬度。
 * @param isLongitude `true` 表示经度，`false` 表示纬度。
 * @param pattern 输出模板。
 * @return 格式化文本。
 * @throws IllegalArgumentException 当接收值无效、模板为空或小数秒精度过大时。
 */
fun Double.toLatLngString(
    isLongitude: Boolean,
    pattern: String = DEFAULT_COORDINATE_PATTERN
): String {
    require(pattern.isNotEmpty()) { "pattern must not be empty" }
    val value = if (isLongitude) toLongitudeNoPre() else toLatitudeNoPre()
    val fractionDigits = pattern.maxFractionDigits()
    require(fractionDigits <= 9) {
        "pattern supports at most 9 fractional second digits, but requested $fractionDigits"
    }

    val scale = 10.0.pow(fractionDigits).roundToLong()
    val totalUnits = (value * 3600.0 * scale).roundToLong()
    val degrees = totalUnits / (3600L * scale)
    val afterDegrees = totalUnits % (3600L * scale)
    val minutes = afterDegrees / (60L * scale)
    val afterMinutes = afterDegrees % (60L * scale)
    val seconds = afterMinutes / scale
    val fraction = afterMinutes % scale
    val chineseDirection = if (isLongitude) {
        if (isEastLongitude()) "东经" else "西经"
    } else {
        if (isNorthLatitude()) "北纬" else "南纬"
    }
    val englishDirection = if (isLongitude) {
        if (isEastLongitude()) "E" else "W"
    } else {
        if (isNorthLatitude()) "N" else "S"
    }

    return buildString(pattern.length + 8) {
        var index = 0
        while (index < pattern.length) {
            if (pattern.startsWith("CH", index)) {
                append(chineseDirection)
                index += 2
                continue
            }
            val marker = pattern[index]
            if (marker == 'F') {
                append(englishDirection)
                index++
                continue
            }
            if (marker !in "dmSs") {
                append(marker)
                index++
                continue
            }
            val start = index
            while (index < pattern.length && pattern[index] == marker) index++
            val width = index - start
            val text = when (marker) {
                'd' -> degrees.toString()
                'm' -> minutes.toString()
                'S' -> seconds.toString()
                else -> {
                    if (fractionDigits == 0) ""
                    else fraction.toString().padStart(fractionDigits, '0').take(width)
                }
            }
            append(if (marker == 's') text.padEnd(width, '0') else text.padStart(width, '0'))
        }
    }
}

/**
 * 将经度格式化为方向和度分秒文本。
 *
 * @receiver 经度。
 * @param pattern 输出模板，标记规则见 [toLatLngString]。
 * @return 格式化后的经度文本。
 * @throws IllegalArgumentException 当经度或模板无效时。
 */
fun Double.toLongitudeString(pattern: String = DEFAULT_COORDINATE_PATTERN): String =
    toLatLngString(true, pattern)

/**
 * 将纬度格式化为方向和度分秒文本。
 *
 * @receiver 纬度。
 * @param pattern 输出模板，标记规则见 [toLatLngString]。
 * @return 格式化后的纬度文本。
 * @throws IllegalArgumentException 当纬度或模板无效时。
 */
fun Double.toLatitudeString(pattern: String = DEFAULT_COORDINATE_PATTERN): String =
    toLatLngString(false, pattern)

private const val AXIS_UNKNOWN = 0
private const val AXIS_LONGITUDE = 1
private const val AXIS_LATITUDE = 2

private fun String.parseCoordinate(pattern: String, expectedAxis: Int): Double {
    require(pattern.isNotEmpty()) { "pattern must not be empty" }
    val fields = mutableListOf<String>()
    val expression = buildString(pattern.length * 2 + 2) {
        append('^')
        var index = 0
        while (index < pattern.length) {
            if (pattern.startsWith("CH", index)) {
                append("(东经|西经|北纬|南纬)")
                fields.add("CH")
                index += 2
                continue
            }
            val marker = pattern[index]
            if (marker == 'F') {
                append("([EWNSewns])")
                fields.add("F")
                index++
                continue
            }
            if (marker !in "dmSs") {
                append(Regex.escape(marker.toString()))
                index++
                continue
            }
            val start = index
            while (index < pattern.length && pattern[index] == marker) index++
            val width = index - start
            val digitCount = when {
                width > 1 -> "{$width}"
                marker == 'd' -> "{1,3}"
                marker == 's' -> "{1}"
                else -> "{1,2}"
            }
            append("([0-9]$digitCount)")
            fields.add(marker.toString())
        }
        append('$')
    }
    val match = Regex(expression).matchEntire(this)
        ?: throw IllegalArgumentException("value '$this' does not match pattern '$pattern'")

    var degrees: Int? = null
    var minutes = 0
    var seconds = 0
    var fraction = 0.0
    var hasMinutes = false
    var hasSeconds = false
    var hasFraction = false
    var axis = AXIS_UNKNOWN
    var sign = 1.0
    fields.forEachIndexed { fieldIndex, field ->
        val value = match.groupValues[fieldIndex + 1]
        when (field) {
            "d" -> {
                require(degrees == null) { "pattern must contain at most one degree field" }
                degrees = value.toInt()
            }
            "m" -> {
                require(!hasMinutes) { "pattern must contain at most one minute field" }
                minutes = value.toInt()
                hasMinutes = true
            }
            "S" -> {
                require(!hasSeconds) { "pattern must contain at most one integer second field" }
                seconds = value.toInt()
                hasSeconds = true
            }
            "s" -> {
                require(!hasFraction) { "pattern must contain at most one fractional second field" }
                fraction = value.toDouble() / 10.0.pow(value.length)
                hasFraction = true
            }
            "CH", "F" -> {
                val normalized = value.uppercase()
                val currentAxis = when (normalized) {
                    "东经", "西经", "E", "W" -> AXIS_LONGITUDE
                    else -> AXIS_LATITUDE
                }
                val currentSign = when (normalized) {
                    "西经", "南纬", "W", "S" -> -1.0
                    else -> 1.0
                }
                require(axis == AXIS_UNKNOWN || axis == currentAxis) {
                    "direction fields in value '$this' use different axes"
                }
                require(axis == AXIS_UNKNOWN || sign == currentSign) {
                    "direction fields in value '$this' conflict"
                }
                axis = currentAxis
                sign = currentSign
            }
        }
    }

    val degreeValue = requireNotNull(degrees) { "pattern must contain a degree field" }
    require(minutes in 0..59) { "minutes must be in 0..59, but was $minutes" }
    require(seconds in 0..59) { "seconds must be in 0..59, but was $seconds" }
    if (expectedAxis != AXIS_UNKNOWN && axis != AXIS_UNKNOWN) {
        require(axis == expectedAxis) { "direction in value '$this' does not match the expected axis" }
    }
    val resolvedAxis = if (axis == AXIS_UNKNOWN) expectedAxis else axis
    val maximumDegrees = if (resolvedAxis == AXIS_LATITUDE) 90 else 180
    require(degreeValue <= maximumDegrees) {
        "degrees must be in 0..$maximumDegrees, but was $degreeValue"
    }
    require(degreeValue < maximumDegrees || minutes == 0 && seconds == 0 && fraction == 0.0) {
        "minutes and seconds must be zero at $maximumDegrees degrees"
    }
    return sign * (degreeValue + minutes / 60.0 + (seconds + fraction) / 3600.0)
}

/**
 * 按模板将方向和度分秒文本解析为十进制度。
 *
 * 模板标记规则见 [toLatLngString]。没有方向标记时返回正值。
 *
 * @receiver 待解析文本。
 * @param pattern 输入模板。
 * @return 带正负方向的十进制度。
 * @throws IllegalArgumentException 当文本不匹配模板、方向冲突或数值超出范围时。
 */
@JsName("toLatLngByString")
fun String.toLatLng(pattern: String): Double = parseCoordinate(pattern, AXIS_UNKNOWN)

/**
 * 按模板将经度文本解析为十进制度。
 *
 * @receiver 待解析文本。
 * @param pattern 输入模板。
 * @return 东经为正、西经为负的经度。
 * @throws IllegalArgumentException 当文本或数值无效时。
 */
fun String.toLongitude(pattern: String): Double = parseCoordinate(pattern, AXIS_LONGITUDE)

/**
 * 按模板将纬度文本解析为十进制度。
 *
 * @receiver 待解析文本。
 * @param pattern 输入模板。
 * @return 北纬为正、南纬为负的纬度。
 * @throws IllegalArgumentException 当文本或数值无效时。
 */
fun String.toLatitude(pattern: String): Double = parseCoordinate(pattern, AXIS_LATITUDE)

/**
 * 将 `ddmm.mmmm` 格式转换为十进制度。
 *
 * 该格式常见于北斗 2.0 和 NMEA 协议。
 *
 * @receiver `ddmm.mmmm` 格式的有限数值。
 * @return 十进制度。
 * @throws IllegalArgumentException 当数值不是有限值或分钟部分不在 `[0, 60)` 时。
 */
fun Double.ddmmpmmmm2LatLng(): Double {
    require(isFinite()) { "value must be finite, but was $this" }
    val absoluteValue = kotlin.math.abs(this)
    val degrees = (absoluteValue / 100.0).toInt()
    val minutes = absoluteValue % 100.0
    require(minutes < 60.0) {
        "minutes must be in [0, 60), but was $minutes"
    }
    val sign = if (this < 0.0) -1.0 else 1.0
    return sign * (degrees + minutes / 60.0)
}

/**
 * 将十进制度转换为 `ddmm.mmmm` 格式。
 *
 * @receiver 有限的十进制度。
 * @return `ddmm.mmmm` 格式数值。
 * @throws IllegalArgumentException 当接收值不是有限值时。
 */
fun Double.latLng2ddmmpmmmm(): Double {
    require(isFinite()) { "value must be finite, but was $this" }
    val z = this.toInt()
    val end = (this - z) * 60.0 / 100.0
    return (z + end) * 100
}
