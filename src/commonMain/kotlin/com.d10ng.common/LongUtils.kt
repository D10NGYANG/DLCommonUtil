package com.d10ng.common

/**
 * 将 Long 转 8个字节以内的 ByteArray
 * @receiver [Long]
 * @return [ByteArray]
 */
fun Long.toByteArray(): ByteArray {
    val ba = byteArrayOf(
        ushr(56).toByte(),
        ushr(48).toByte(),
        ushr(40).toByte(),
        ushr(32).toByte(),
        ushr(24).toByte(),
        ushr(16).toByte(),
        ushr(8).toByte(),
        (this and 0xFF).toByte()
    )
    var startIndex = 0
    for (i in ba.indices) {
        if (ba[i] != (0x00).toByte()) {
            startIndex = i
            break
        }
    }
    return ba.copyOfRange(startIndex, 8)
}