@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将字节数组转换为short数组
 * @receiver [ByteArray]
 * @return [ShortArray]
 */
@JsName("byteArrayToShortArray")
fun ByteArray.toShortArray(): ShortArray {
    val shorts = ShortArray(this.size / 2)
    for (i in this.indices step 2) {
        val byte1 = this[i].toInt() and 0xFF
        val byte2 = this[i + 1].toInt() and 0xFF
        val short = (byte2 shl 8) or byte1
        shorts[i / 2] = short.toShort()
    }
    return shorts
}

/**
 * 将short数组转换为字节数组
 * @receiver [ShortArray]
 * @return [ByteArray]
 */
@JsName("shortArrayToByteArray")
fun ShortArray.toByteArray(): ByteArray {
    val bytes = ByteArray(this.size * 2)
    for (i in this.indices) {
        val short = this[i]
        val byte1 = short.toInt() and 0xFF
        val byte2 = (short.toInt() shr 8) and 0xFF
        bytes[i * 2] = byte1.toByte()
        bytes[i * 2 + 1] = byte2.toByte()
    }
    return bytes
}