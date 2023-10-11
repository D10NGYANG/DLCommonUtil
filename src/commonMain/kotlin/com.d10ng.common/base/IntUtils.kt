@file:JsExport
package com.d10ng.common.base

import com.d10ng.common.toByteArrayFromHex
import kotlin.js.JsExport

/**
 * 将 无符号整型 转为 N 个字节的 ByteArray
 * @receiver [Int] 整型
 * @param size [Int] 字节数，默认为4
 * @return [ByteArray] 字节数组
 */
fun Int.toByteArray(size: Int = 4): ByteArray {
    if (this < 0) throw IllegalArgumentException("Int must be unsigned")
    val hex = toString(16).padStart(size * 2, '0')
    return hex.toByteArrayFromHex()
}