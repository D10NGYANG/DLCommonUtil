@file:JsExport
package com.d10ng.common.base

import com.d10ng.common.keepByRegexStr
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.math.ceil

/**
 * 将 8位 二进制字符串 "00110011" 转为 Byte
 * @receiver [String] 二进制字符串，可以包含空格，如 "0011 0011"或"00110011"
 * @return [Byte] 转换后的 Byte，如果转换失败则返回 0x00
 */
@JsName("binStringToByte")
fun String.toByteFromBin(): Byte {
    val value = this.keepByRegexStr("[01]+")
    val str = value.padStart(8, '0')
    return str.toInt(2).toByte()
}

/**
 * 将 8*N 二进制字符串 "00110011" 转为 ByteArray
 * @receiver [String] 二进制字符串，可以包含空格，如 "0011 0011"或"00110011"
 * @return [ByteArray] 转换后的 ByteArray，如果转换失败则返回 byteArrayOf()
 */
@JsName("binStringToByteArray")
fun String.toByteArrayFromBin(): ByteArray {
    var value = this.keepByRegexStr("[01]+")
    if (value.isEmpty()) return byteArrayOf()
    val length = ceil(value.length / 8.0).toInt()
    value = value.padStart(length * 8, '0')
    val result = ByteArray(length)
    for (i in 0 until length) {
        result[i] = value.substring(i * 8, i * 8 + 8).toByteFromBin()
    }
    return result
}

/**
 * 将 2位 16进制字符串 "fc" 转为 Byte
 * @receiver [String] 16进制字符串，如 "fc"
 * @return [Byte] 转换后的 Byte，如果转换失败则返回 0x00
 */
@JsName("hexStringToByte")
fun String.toByteFromHex(): Byte {
    val value = this.keepByRegexStr("[A-Fa-f0-9]+")
    val str = value.padStart(2, '0')
    return str.toInt(16).toByte()
}

/**
 * 将 2*N 16进制字符串 "fcfc" 转为 ByteArray
 * @receiver [String] 16进制字符串，可以包含空格，如 "fc fc"或"fcfc"
 * @return [ByteArray] 转换后的 ByteArray，如果转换失败则返回 byteArrayOf()
 */
@JsName("hexStringToByteArray")
fun String.toByteArrayFromHex(): ByteArray {
    var value = this.keepByRegexStr("[A-Fa-f0-9]+")
    if (value.isEmpty()) return byteArrayOf()
    val length = ceil(value.length / 2.0).toInt()
    value = value.padStart(length * 2, '0')
    val result = ByteArray(length)
    for (i in 0 until length) {
        result[i] = value.substring(i * 2, i * 2 + 2).toByteFromHex()
    }
    return result
}

/**
 * 以字节单位获得字符串的长度，其中全角字符算两个字节，半角字符算一个字节
 * @return [Int] 长度
 */
@JsName("getStringByteLength")
fun String.getByteLength(): Int {
    var length = 0
    for (i in this.iterator()) {
        val ascii = i.code
        if (ascii in 0..255) length ++
        else length += 2
    }
    return length
}

/**
 * 获取字符串的第一个字并转换成大写的
 * @receiver [String]
 * @return [String]
 */
@JsName("getStringFirstUpperCase")
fun String.getFirstUpperCase(): String {
    return if (this.isEmpty()) ""
    else this[0].uppercase()
}