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
 * 将 整型 转为4个字节的 ByteArray
 * @receiver [Int]
 * @return [ByteArray]
 */
fun Int.toByteArray(): ByteArray {
    val hex = toString(16).padStart(8, '0')
    return hex.toByteArrayFromHex()
}