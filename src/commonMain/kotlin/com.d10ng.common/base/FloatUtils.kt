package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 截断十进制字符串的小数部分，同时保留科学计数法指数。
 *
 * @receiver 浮点数的字符串形式
 * @param maxDecimalCount 最多保留的小数位数
 * @return 未四舍五入的截断结果
 * @throws IllegalArgumentException 当 [maxDecimalCount] 为负数时
 */
internal fun String.keep(maxDecimalCount: Int): String {
    require(maxDecimalCount >= 0) {
        "maxDecimalCount must be non-negative, but was $maxDecimalCount"
    }
    val pointIndex = indexOf('.')
    if (pointIndex < 0) return this

    val exponentIndex = indexOfAny(charArrayOf('e', 'E'), startIndex = pointIndex + 1)
        .let { if (it < 0) length else it }
    val decimalCount = exponentIndex - pointIndex - 1
    if (decimalCount <= maxDecimalCount) return this

    val fractionEnd = pointIndex + 1 + maxDecimalCount
    return buildString(length - (decimalCount - maxDecimalCount)) {
        append(this@keep, 0, if (maxDecimalCount == 0) pointIndex else fractionEnd)
        append(this@keep, exponentIndex, this@keep.length)
    }
}

/**
 * 将浮点数转换为字符串并截断到最多指定小数位数。
 *
 * 该函数不进行四舍五入，并保留科学计数法中的指数部分。
 *
 * @receiver 要格式化的浮点数
 * @param maxDecimalCount 最大小数位数
 * @return 截断后的字符串
 * @throws IllegalArgumentException 当 [maxDecimalCount] 为负数时
 */
fun Float.toStringWithMaxDecimal(maxDecimalCount: Int) = toString().keep(maxDecimalCount)

/**
 * 将浮点数的 IEEE 754 位表示转换为大端序字节数组。
 *
 * @receiver 要转换的浮点数
 * @return 长度为 4 的字节数组
 */
@JsExport
@JsName("floatToByteArray")
fun Float.toByteArray(): ByteArray {
    return toBits().toByteArray(4)
}

/**
 * 将大端序 IEEE 754 位表示转换为浮点数。
 *
 * @receiver 长度必须为 4 的字节数组
 * @return 解码后的浮点数
 * @throws IllegalArgumentException 当数组长度不为 4 时
 */
@JsExport
@JsName("byteArrayToFloat")
fun ByteArray.toFloat(): Float {
    require(size == Float.SIZE_BYTES) {
        "ByteArray size must be ${Float.SIZE_BYTES}, but was $size"
    }
    return Float.fromBits(toInt())
}
