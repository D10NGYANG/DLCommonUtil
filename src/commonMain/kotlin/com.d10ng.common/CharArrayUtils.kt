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
    val byteList = mutableListOf<Byte>()
    for (char in this) {
        byteList.add(char.code.toByte())
    }
    return byteList.toByteArray()
}