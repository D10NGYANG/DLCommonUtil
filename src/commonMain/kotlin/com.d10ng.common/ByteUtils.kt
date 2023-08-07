package com.d10ng.common

/**
 * 将 Byte 转为 8位 二进制字符串 "00110011"
 * @receiver [Byte]
 * @return [String]
 */
fun Byte.toBinString(): String =
    this.toUByte().toString(2).fillLength(8)

/**
 * 将 Byte 转为 2位 16进制字符串 "fc"
 * @receiver [Byte]
 * @param uppercase [Boolean] 是否需要大写
 * @return [String]
 */
fun Byte.toHexString(uppercase: Boolean = false): String {
    val result = toUByte().toString(16).fillLength(2)
    return if (uppercase) result.uppercase() else result.lowercase()
}