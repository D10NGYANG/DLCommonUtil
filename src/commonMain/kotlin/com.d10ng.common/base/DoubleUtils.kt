package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将双精度浮点数转换为字符串并截断到最多指定小数位数。
 *
 * 该函数不进行四舍五入，并保留科学计数法中的指数部分。
 *
 * @receiver 要格式化的双精度浮点数
 * @param maxDecimalCount 最大小数位数
 * @return 截断后的字符串
 * @throws IllegalArgumentException 当 [maxDecimalCount] 为负数时
 */
fun Double.toStringWithMaxDecimal(maxDecimalCount: Int) = toString().keep(maxDecimalCount)

/**
 * 将双精度浮点数的 IEEE 754 位表示转换为大端序字节数组。
 *
 * @receiver 要转换的双精度浮点数
 * @return 长度为 8 的字节数组
 */
@JsExport
@JsName("doubleToByteArray")
fun Double.toByteArray(): ByteArray {
    return toBits().toByteArray(8)
}

/**
 * 将大端序 IEEE 754 位表示转换为双精度浮点数。
 *
 * @receiver 长度必须为 8 的字节数组
 * @return 解码后的双精度浮点数
 * @throws IllegalArgumentException 当数组长度不为 8 时
 */
@JsExport
@JsName("byteArrayToDouble")
fun ByteArray.toDouble(): Double {
    require(size == Double.SIZE_BYTES) {
        "ByteArray size must be ${Double.SIZE_BYTES}, but was $size"
    }
    return Double.fromBits(toLong())
}
