@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 Byte 转为 8位 二进制字符串 "00110011"
 * @receiver [Byte] 字节
 * @return [String] 二进制字符串
 */
@JsName("toBinStringByByte")
fun Byte.toBinString() =
    this.toUByte().toString(2).padStart(8, '0')

/**
 * 将 Byte 转为 2位 16进制字符串 "fc"
 * @receiver [Byte] 字节
 * @param uppercase [Boolean] 是否需要大写
 * @return [String] 16进制字符串
 */
@JsName("toHexStringByByte")
fun Byte.toHexString(uppercase: Boolean = false): String {
    val result = toUByte().toString(16).padStart(2, '0')
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 Byte 0xFF 转为 无符号整数 255
 * @receiver Byte 字节，如0xFF
 * @return Int 无符号整数，如255
 */
@JsName("toUnsignedIntByByte")
fun Byte.toUnsignedInt() = toUByte().toInt()