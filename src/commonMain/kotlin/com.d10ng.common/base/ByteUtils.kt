@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 Byte 转为 8位 二进制字符串 "00110011"
 * @receiver [Byte] 字节
 * @return [String] 二进制字符串
 */
@JsName("byteToBinString")
fun Byte.toBinString() =
    this.toUByte().toString(2).padStart(8, '0')

/**
 * 将 Byte 转为 2位 16进制字符串 "FC"
 * @receiver [Byte] 字节
 * @param uppercase [Boolean] 是否需要大写，默认true需要
 * @return [String] 16进制字符串
 */
@JsName("byteToHexString")
fun Byte.toHexString(uppercase: Boolean = true): String {
    val result = toUByte().toString(16).padStart(2, '0')
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 Byte 0xFF 转为 无符号整数 255
 * @receiver [Byte] 字节，如0xFF
 * @return [Int] 无符号整数，如255
 */
@JsName("byteToUnsignedInt")
fun Byte.toUnsignedInt() = toUByte().toInt()

/**
 * 从 Byte 中获取指定位置的 bit
 * @receiver [Byte] 字节
 * @param bitIndex [Int] 0..7
 * @return [Int] 0/1
 */
@JsName("byteGetBit")
fun Byte.getBit(bitIndex: Int): Int {
    if (bitIndex !in 0..7) throw IndexOutOfBoundsException("bitIndex must be in 0..7")
    return (this.toUnsignedInt() shr bitIndex) and 0x01
}