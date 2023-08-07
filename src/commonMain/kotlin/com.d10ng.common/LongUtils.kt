package com.d10ng.common

/**
 * 将 Long 转 8个字节以内的 ByteArray
 * @receiver [Long]
 * @return [ByteArray]
 */
fun Long.toByteArray(): ByteArray {
    var hex = toString(16)
    // 如果hex的长度为单数，则需要在前面补0
    if (hex.length % 2 != 0) {
        hex = "0$hex"
    }
    return hex.toByteArrayFromHex()
}