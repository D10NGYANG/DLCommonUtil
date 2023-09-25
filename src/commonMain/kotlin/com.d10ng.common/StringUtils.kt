@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport

/**
 * 将 8位 二进制字符串 "00110011" 转为 Byte
 * @receiver [String] 二进制字符串，可以包含空格，如 "0011 0011"或"00110011"
 * @return [Byte] 转换后的 Byte，如果转换失败则返回 0x00
 */
fun String.toByteFromBin(): Byte {
    val value = replace(" ", "")
    val regex = "[01]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return 0x00
    val str = value.padStart(8, '0')
    return str.toInt(2).toByte()
}

/**
 * 将 8*N 二进制字符串 "00110011" 转为 ByteArray
 * @receiver [String] 二进制字符串，可以包含空格，如 "0011 0011"或"00110011"
 * @return [ByteArray] 转换后的 ByteArray，如果转换失败则返回 byteArrayOf()
 */
fun String.toByteArrayFromBin(): ByteArray {
    val value = replace(" ", "")
    val regex = "[01]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return byteArrayOf()
    var str = value
    if (value.length % 8 != 0) {
        str = value.padStart((value.length / 8 + 1) * 8, '0')
    }
    val list = mutableListOf<Byte>()
    for (i in str.indices step 8) {
        list.add(str.substring(i, i + 8).toByteFromBin())
    }
    return list.toByteArray()
}

/**
 * 将 2位 16进制字符串 "fc" 转为 Byte
 * @receiver [String] 16进制字符串，如 "fc"
 * @return [Byte] 转换后的 Byte，如果转换失败则返回 0x00
 */
fun String.toByteFromHex(): Byte {
    val value = replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return 0x00
    val str = value.padStart(8, '0')
    return str.toInt(16).toByte()
}

/**
 * 将 2*N 16进制字符串 "fcfc" 转为 ByteArray
 * @receiver [String] 16进制字符串，可以包含空格，如 "fc fc"或"fcfc"
 * @return [ByteArray] 转换后的 ByteArray，如果转换失败则返回 byteArrayOf()
 */
fun String.toByteArrayFromHex(): ByteArray {
    val value = replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return byteArrayOf()
    var str = value
    if (value.length % 2 != 0) {
        str = value.padStart((value.length / 2 + 1) * 2, '0')
    }
    val list = mutableListOf<Byte>()
    for (i in str.indices step 2) {
        list.add(str.substring(i, i + 2).toByteFromHex())
    }
    return list.toByteArray()
}

/**
 * 以字节单位获得字符串的长度
 * 汉字 - 占两个Byte长度
 * 英文数字 - 占一个Byte长度
 * @return [Int] 长度
 */
fun String.getByteLength(): Int {
    var length = 0
    val chars = this.toCharArray()
    for (i in chars.iterator()) {
        val ascii = i.code
        if (ascii in 0..255) length++
        else length += 2
    }
    return length
}

/**
 * 获取字符串的第一个字并转换成大写的
 * @receiver [String]
 * @return [String]
 */
fun String.getFirstUpperCase(): String {
    return if (this.isEmpty()) ""
    else this.substring(0, 1).uppercase()
}