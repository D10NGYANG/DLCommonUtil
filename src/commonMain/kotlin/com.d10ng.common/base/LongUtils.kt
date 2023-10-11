@file:JsExport
package com.d10ng.common.base

import com.d10ng.common.toByteArrayFromHex
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 无符号长整型 转 N 个字节 的 ByteArray
 * @receiver [Long] 长整型
 * @param size [Int] 字节数，默认为8
 * @return [ByteArray] 字节数组
 */
@JsName("toByteArrayByLong")
fun Long.toByteArray(size: Int = 8): ByteArray {
    if (this < 0) throw IllegalArgumentException("Long must be unsigned")
    val hex = toString(16).padStart(size * 2, '0')
    return hex.toByteArrayFromHex()
}