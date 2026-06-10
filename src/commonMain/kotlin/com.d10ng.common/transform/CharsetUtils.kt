package com.d10ng.common.transform

import kotlin.js.JsExport

private const val HEX_DIGITS = "0123456789abcdef"

private fun Char.hexValue(): Int = when (this) {
    in '0'..'9' -> code - '0'.code
    in 'a'..'f' -> code - 'a'.code + 10
    in 'A'..'F' -> code - 'A'.code + 10
    else -> -1
}

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
expect fun String.encodeGBK(): ByteArray

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
expect fun ByteArray.decodeGBK(): String

/**
 * 将字符串转换成字节数组，编码格式为GB18030
 * @receiver [String] 字符串
 * @return [ByteArray] GB18030编码的字节数组
 */
expect fun String.encodeGB18030(): ByteArray

/**
 * 将字节数组转换成字符串，编码格式为GB18030
 * @receiver [ByteArray] GB18030编码的字节数组
 * @return [String] 字符串
 */
expect fun ByteArray.decodeGB18030(): String

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
    val result = ByteArray(length * 2)
    for (i in indices) {
        val value = this[i].code
        result[i * 2] = (value shr 8).toByte()
        result[i * 2 + 1] = value.toByte()
    }
    return result
}

/**
 * 将字符串转换成Unicode格式的16进制代码字符串，编码格式为Unicode
 * @receiver [String] 字符串
 * @param isNeedU [Boolean] 是否需要带"\\u"，默认true
 * @return [String] Unicode格式的16进制代码字符串
 */
@JsExport
fun String.encodeUnicodeString(isNeedU: Boolean = true): String {
    val itemLength = if (isNeedU) 6 else 4
    val builder = StringBuilder(length * itemLength)
    for (c in this) {
        val value = c.code
        if (isNeedU) builder.append("\\u")
        builder.append(HEX_DIGITS[value ushr 12])
        builder.append(HEX_DIGITS[value ushr 8 and 0x0F])
        builder.append(HEX_DIGITS[value ushr 4 and 0x0F])
        builder.append(HEX_DIGITS[value and 0x0F])
    }
    return builder.toString()
}

/**
 * 将字节数组转换成字符串，编码格式为Unicode
 * @receiver [ByteArray] Unicode编码的字节数组
 * @return [String] 字符串
 */
@JsExport
fun ByteArray.decodeUnicode(): String {
    val builder = StringBuilder((size + 1) / 2)
    var i = 0
    while (i < size) {
        val high = this[i].toInt() and 0xFF
        val low = if (i + 1 < size) this[i + 1].toInt() and 0xFF else 0
        val value = high shl 8 or low
        if (value != 0) builder.append(value.toChar())
        i += 2
    }
    return builder.toString()
}

/**
 * 将Unicode格式的16进制代码字符串转换成明文字符串，编码格式为Unicode
 * - 可以带"\\u"，也可以不带"\\u"
 * - 字符串中可以带空格
 * @receiver [String] Unicode格式的16进制代码字符串
 * @return [String] 明文字符串
 */
@JsExport
fun String.decodeUnicodeString(): String {
    var digitCount = 0
    var i = 0
    while (i < length) {
        when {
            this[i] == ' ' -> i++
            this[i] == '\\' && i + 1 < length && this[i + 1] == 'u' -> i += 2
            this[i].hexValue() >= 0 -> {
                digitCount++
                i++
            }
            else -> return ""
        }
    }
    if (digitCount == 0) return ""

    val builder = StringBuilder((digitCount + 3) / 4)
    var value = 0
    var digitsInValue = 0
    i = 0
    while (i < length) {
        when {
            this[i] == ' ' -> i++
            this[i] == '\\' -> i += 2
            else -> {
                value = value shl 4 or this[i].hexValue()
                digitsInValue++
                i++
                if (digitsInValue == 4) {
                    if (value != 0) builder.append(value.toChar())
                    value = 0
                    digitsInValue = 0
                }
            }
        }
    }
    if (digitsInValue != 0) {
        value = value shl ((4 - digitsInValue) * 4)
        if (value != 0) builder.append(value.toChar())
    }
    return builder.toString()
}

/**
 * 将字符串转换成字节数组，编码格式为ASCII
 * @receiver [String] 字符串
 * @return [ByteArray] ASCII编码的字节数组
 */
@JsExport
fun String.encodeASCII(): ByteArray {
    val result = ByteArray(length)
    for (i in indices) {
        val value = this[i].code
        if (value !in 0x20..0x7E) return byteArrayOf()
        result[i] = value.toByte()
    }
    return result
}

/**
 * 将 明文字符串 转换成 ASCII格式的16进制代码字符串
 * - ASCII 只支持英文数字和英文符号
 * @receiver [String] 明文字符串
 * @return [String] ASCII格式的16进制代码字符串
 */
@JsExport
fun String.encodeASCIIString(): String {
    if (isEmpty()) return ""
    val builder = StringBuilder(length * 2)
    for (c in this) {
        val value = c.code
        if (value !in 0x20..0x7E) return ""
        builder.append(HEX_DIGITS[value ushr 4])
        builder.append(HEX_DIGITS[value and 0x0F])
    }
    return builder.toString()
}

/**
 * 将 ASCII格式的字节数组 转换成 明文字符串
 * @receiver [ByteArray] ASCII格式的字节数组
 * @return [String] 明文字符串
 */
@JsExport
fun ByteArray.decodeASCII(): String {
    val builder = StringBuilder(size)
    for (value in this) {
        builder.append((value.toInt() and 0xFF).toChar())
    }
    return builder.toString()
}

/**
 * 将 ASCII格式的16进制代码字符串 转换成 明文字符串
 * @receiver [String] ASCII格式的16进制代码字符串
 * @return [String] 明文字符串
 */
@JsExport
fun String.decodeASCIIString(): String {
    var digitCount = 0
    for (c in this) {
        if (c == ' ') continue
        if (c.hexValue() < 0) return ""
        digitCount++
    }
    if (digitCount == 0) return ""

    val builder = StringBuilder((digitCount + 1) / 2)
    var highNibble = -1
    for (c in this) {
        if (c == ' ') continue
        val value = c.hexValue()
        if (highNibble < 0) {
            highNibble = value
        } else {
            builder.append((highNibble shl 4 or value).toChar())
            highNibble = -1
        }
    }
    if (highNibble >= 0) {
        builder.append((highNibble shl 4).toChar())
    }
    return builder.toString()
}
