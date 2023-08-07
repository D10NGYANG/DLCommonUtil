package com.d10ng.common

/**
 * 将 Byte 转为 8位 二进制字符串 "00110011"
 * @receiver [Byte] 字节
 * @return [String] 二进制字符串
 */
fun Byte.toBinString(): String =
    this.toUByte().toString(2).fillLength(8)

/**
 * 将 Byte 转为 2位 16进制字符串 "fc"
 * @receiver [Byte] 字节
 * @param uppercase [Boolean] 是否需要大写
 * @return [String] 16进制字符串
 */
fun Byte.toHexString(uppercase: Boolean = false): String {
    val result = toUByte().toString(16).fillLength(2)
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 Byte 0xFF 转为 无符号整数 255
 * @receiver Byte 字节，如0xFF
 * @return Int 无符号整数，如255
 */
fun Byte.toUnsignedInt(): Int = this.toUByte().toInt()