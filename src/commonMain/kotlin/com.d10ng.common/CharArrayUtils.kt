@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 CharArray 转换成 ByteArray
 * @receiver [CharArray]
 * @return [ByteArray]
 */
@JsName("toByteArrayByCharArray")
fun CharArray.toByteArray(): ByteArray {
    val bytes = ByteArray(this.size)
    forEachIndexed { index, c ->
        bytes[index] = c.code.toByte()
    }
    return bytes
}