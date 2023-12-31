package com.d10ng.common.transform

import com.d10ng.common.base.toByteArrayFromHex
import com.d10ng.common.base.toHexString
import kotlin.js.JsExport

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@JsExport
fun String.encodeGBK(): ByteArray = encodeGBKDo()

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
expect fun String.encodeGBKDo(): ByteArray

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@JsExport
fun ByteArray.decodeGBK(): String = decodeGBKDo()

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
expect fun ByteArray.decodeGBKDo(): String

/**
 * 将字符串转换成字节数组，编码格式为UTF8
 * @receiver [String] 字符串
 * @return [ByteArray] UTF8编码的字节数组
 */
@JsExport
fun String.encodeUTF8(): ByteArray = encodeToByteArray()

/**
 * 将字节数组转换成字符串，编码格式为UTF8
 * @receiver [ByteArray] UTF8编码的字节数组
 * @return [String] 字符串
 */
@JsExport
fun ByteArray.decodeUTF8(): String = decodeToString()

/**
 * 将字符串转换成字节数组，编码格式为Unicode
 * @receiver [String] 字符串
 * @return [ByteArray] Unicode编码的字节数组
 */
@JsExport
fun String.encodeUnicode(): ByteArray {
    val hex = encodeUnicodeString(false)
    return hex.toByteArrayFromHex()
}

/**
 * 将字符串转换成Unicode格式的16进制代码字符串，编码格式为Unicode
 * @receiver [String] 字符串
 * @param isNeedU [Boolean] 是否需要带"\\u"，默认true
 * @return [String] Unicode格式的16进制代码字符串
 */
@JsExport
fun String.encodeUnicodeString(isNeedU: Boolean = true): String {
    val builder = StringBuilder()
    for (c in this.iterator()) {
        val item = c.code.toString(16)
        if (isNeedU) builder.append("\\u")
        builder.append(item.padStart(4, '0'))
    }
    return builder.toString()
}

/**
 * 将字节数组转换成字符串，编码格式为Unicode
 * @receiver [ByteArray] Unicode编码的字节数组
 * @return [String] 字符串
 */
@JsExport
fun ByteArray.decodeUnicode(): String = toHexString().decodeUnicodeString()

/**
 * 将Unicode格式的16进制代码字符串转换成明文字符串，编码格式为Unicode
 * - 可以带"\\u"，也可以不带"\\u"
 * - 字符串中可以带空格
 * @receiver [String] Unicode格式的16进制代码字符串
 * @return [String] 明文字符串
 */
@JsExport
fun String.decodeUnicodeString(): String {
    val value = this.replace("\\u", "").replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return ""
    val builder = StringBuilder()
    var str = value
    if (value.length % 4 != 0) {
        str = value.padEnd((value.length / 4 + 1) * 4, '0')
    }
    for (i in str.indices step 4) {
        val end = i + 4
        if (str.length >= end) {
            val item = str.substring(i, end)
            val data = item.toIntOrNull(16)?: 0
            if (data != 0) builder.append(data.toChar())
        }
    }
    return builder.toString()
}

/**
 * 将字符串转换成字节数组，编码格式为ASCII
 * @receiver [String] 字符串
 * @return [ByteArray] ASCII编码的字节数组
 */
@JsExport
fun String.encodeASCII(): ByteArray = encodeASCIIString().toByteArrayFromHex()

/**
 * 将 明文字符串 转换成 ASCII格式的16进制代码字符串
 * - ASCII 只支持英文数字和英文符号
 * @receiver [String] 明文字符串
 * @return [String] ASCII格式的16进制代码字符串
 */
@JsExport
fun String.encodeASCIIString(): String {
    val regex = "[\\u0020-\\u007e]+".toRegex()
    val isMatch = regex.matches(this)
    if (!isMatch) return ""
    val builder = StringBuilder()
    for (c in this.iterator()) {
        val item = c.code.toString(16)
        builder.append(item.padStart(2, '0'))
    }
    return builder.toString()
}

/**
 * 将 ASCII格式的字节数组 转换成 明文字符串
 * @receiver [ByteArray] ASCII格式的字节数组
 * @return [String] 明文字符串
 */
@JsExport
fun ByteArray.decodeASCII(): String = toHexString().decodeASCIIString()

/**
 * 将 ASCII格式的16进制代码字符串 转换成 明文字符串
 * @receiver [String] ASCII格式的16进制代码字符串
 * @return [String] 明文字符串
 */
@JsExport
fun String.decodeASCIIString(): String {
    val value = this.replace(" ", "")
    val regex = "[A-Fa-f0-9]+".toRegex()
    val isMatch = regex.matches(value)
    if (!isMatch) return ""
    val builder = StringBuilder()
    var str = value
    if (value.length % 2 != 0) {
        str = value.padEnd((value.length / 2 + 1) * 2, '0')
    }
    for (i in str.indices step 2) {
        val temp = str.substring(i, i + 2)
        builder.append(temp.toInt(16).toChar())
    }
    return builder.toString()
}