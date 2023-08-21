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
    val str = value.fillLength(8)
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
        str = value.fillLength((value.length / 8 + 1) * 8)
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
    val str = value.fillLength(8)
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
        str = value.fillLength((value.length / 2 + 1) * 2)
    }
    val list = mutableListOf<Byte>()
    for (i in str.indices step 2) {
        list.add(str.substring(i, i + 2).toByteFromHex())
    }
    return list.toByteArray()
}

/**
 * 字符串填充到指定长度
 * @receiver [String]
 * @param length [Int] 需要填充到的长度
 * @param filler [Char] 填充物
 * @param isInStart [Boolean] 是否从开头处填充，
 *                            true：开头；
 *                            false：结尾
 * @param isForced [Boolean] 是否强制性要只保留指定长度字符串，
 *                           true：如果字符串本身已经比输入长度[length]长，也要只截取其中的部分；
 *                           false：如果字符串本身已经比输入长度[length]长，那也不管；
 * @return [String]
 */
@Suppress("NON_EXPORTABLE_TYPE")
fun String.fillLength(
    length: Int,
    filler: Char = '0',
    isInStart: Boolean = true,
    isForced: Boolean = true
): String {
    val result = StringBuilder()
    if (!isInStart) result.append(this)
    if (this.length < length) {
        for (i in 0 until length - this.length) {
            result.append(filler)
        }
    }
    if (isInStart) result.append(this)
    return if (isForced) {
        if (isInStart) result.toString().substring(result.length - length)
        else result.toString().substring(0, length)
    } else result.toString()
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