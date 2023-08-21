@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport

/**
 * 获取整型数据的 高位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeHighByte(): Byte =
    ushr(8).toByte()

/**
 * 获取整型数据的 低位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeLowByte(): Byte =
    (this and 0xff).toByte()

/**
 * 将 整型 转 2个字节以内的 ByteArray
 * @receiver [Int]
 * @return [ByteArray]
 */
fun Int.toByteArray(): ByteArray {
    var hex = toString(16)
    // 如果hex的长度为单数，则需要在前面补0
    if (hex.length % 2 != 0) {
        hex = "0$hex"
    }
    return hex.toByteArrayFromHex()
}