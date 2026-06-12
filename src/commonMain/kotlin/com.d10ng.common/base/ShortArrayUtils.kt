@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将小端序字节对转换为短整型数组。
 *
 * @receiver 字节数必须为偶数的原始数组
 * @return 转换后的短整型数组
 * @throws IllegalArgumentException 当数组包含不完整的末尾字节对时
 */
@JsName("byteArrayToShortArray")
fun ByteArray.toShortArray(): ShortArray {
    require(size % Short.SIZE_BYTES == 0) {
        "ByteArray size must be even, but was $size"
    }
    return ShortArray(size / Short.SIZE_BYTES) { index ->
        val byteIndex = index * Short.SIZE_BYTES
        val low = this[byteIndex].toInt() and 0xff
        val high = this[byteIndex + 1].toInt() and 0xff
        ((high shl Byte.SIZE_BITS) or low).toShort()
    }
}

/**
 * 将短整型数组转换为小端序字节对。
 *
 * @receiver 原始短整型数组
 * @return 长度为接收者两倍的字节数组
 */
@JsName("shortArrayToByteArray")
fun ShortArray.toByteArray(): ByteArray {
    require(size <= Int.MAX_VALUE / Short.SIZE_BYTES) {
        "ShortArray is too large to convert to ByteArray"
    }
    val bytes = ByteArray(size * Short.SIZE_BYTES)
    forEachIndexed { index, value ->
        val byteIndex = index * Short.SIZE_BYTES
        bytes[byteIndex] = value.toByte()
        bytes[byteIndex + 1] = (value.toInt() ushr Byte.SIZE_BITS).toByte()
    }
    return bytes
}
