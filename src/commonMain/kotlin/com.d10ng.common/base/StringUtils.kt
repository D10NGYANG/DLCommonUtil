@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 从字符串中提取二进制数字并转换为字节。
 *
 * 非 `0`、`1` 字符会被忽略；有效位不足 8 位时在高位补 `0`。
 *
 * @receiver 待解析的字符串
 * @return 解析后的字节；没有有效二进制数字时返回 `0`
 * @throws IllegalArgumentException 当有效二进制数字超过 8 位时
 */
@JsName("binStringToByte")
fun String.toByteFromBin(): Byte {
    val value = filter { it == '0' || it == '1' }
    require(value.length <= Byte.SIZE_BITS) {
        "Binary value must contain at most ${Byte.SIZE_BITS} bits, but was ${value.length}"
    }
    val str = value.padStart(8, '0')
    return str.toInt(2).toByte()
}

/**
 * 从字符串中提取二进制数字并转换为字节数组。
 *
 * 非 `0`、`1` 字符会被忽略；总位数不是 8 的倍数时在整个数据高位补 `0`。
 *
 * @receiver 待解析的字符串
 * @return 大端序字节数组；没有有效二进制数字时返回空数组
 */
@JsName("binStringToByteArray")
fun String.toByteArrayFromBin(): ByteArray {
    val value = filter { it == '0' || it == '1' }
    if (value.isEmpty()) return byteArrayOf()
    val byteCount = value.length / Byte.SIZE_BITS +
        if (value.length % Byte.SIZE_BITS == 0) 0 else 1
    val padding = (Byte.SIZE_BITS - value.length % Byte.SIZE_BITS) % Byte.SIZE_BITS
    return ByteArray(byteCount) { byteIndex ->
        var byteValue = 0
        repeat(Byte.SIZE_BITS) { bitOffset ->
            val sourceIndex = byteIndex * Byte.SIZE_BITS + bitOffset - padding
            val bit = if (sourceIndex >= 0) value[sourceIndex] - '0' else 0
            byteValue = (byteValue shl 1) or bit
        }
        byteValue.toByte()
    }
}

/**
 * 从字符串中提取十六进制数字并转换为字节。
 *
 * 该兼容接口会忽略非十六进制字符，并在有效数字不足 2 位时高位补 `0`。
 * Kotlin 标准库替代项采用严格解析，不会忽略非法字符。
 *
 * @receiver 待解析的字符串
 * @return 解析后的字节；没有有效十六进制数字时返回 `0`
 * @throws IllegalArgumentException 当有效十六进制数字超过 2 位时
 */
@Deprecated(
    message = "Use Kotlin's hexToByte() for strict hexadecimal parsing.",
    replaceWith = ReplaceWith("this.hexToByte()"),
)
@JsName("hexStringToByte")
fun String.toByteFromHex(): Byte {
    val value = filter(Char::isHexDigit)
    val str = value.padStart(2, '0')
    return str.hexToByte()
}

/**
 * 从字符串中提取十六进制数字并转换为字节数组。
 *
 * 该兼容接口会忽略非十六进制字符，并在有效数字为奇数时高位补 `0`。
 * Kotlin 标准库替代项采用严格解析，不会忽略非法字符或接受奇数位输入。
 *
 * @receiver 待解析的字符串
 * @return 解析后的大端序字节数组；没有有效十六进制数字时返回空数组
 */
@Deprecated(
    message = "Use Kotlin's hexToByteArray() for strict hexadecimal parsing.",
    replaceWith = ReplaceWith("this.hexToByteArray()"),
)
@OptIn(ExperimentalStdlibApi::class)
@JsName("hexStringToByteArray")
fun String.toByteArrayFromHex(): ByteArray {
    var value = filter(Char::isHexDigit)
    if (value.isEmpty()) return byteArrayOf()
    if (value.length % 2 != 0) value = "0$value"
    return value.hexToByteArray()
}

/**
 * 按兼容规则计算字符串宽度。
 *
 * Unicode 码元值不超过 `255` 时计为 1，其余计为 2。该结果不是任何具体字符编码的
 * 实际字节数，并且代理项会分别计数。
 *
 * @receiver 待计算的字符串
 * @return 按上述规则累计的宽度
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
 * 获取首个 UTF-16 码元的大写形式。
 *
 * @receiver 原始字符串
 * @return 首个码元的大写字符串；接收者为空时返回空字符串
 */
@JsName("getStringFirstUpperCase")
fun String.getFirstUpperCase(): String {
    return if (this.isEmpty()) ""
    else this[0].uppercase()
}

/**
 * 将 ASCII 空格和可打印字符转换为对应全角字符。
 *
 * ASCII `!` 到 `~` 使用 Unicode 全角区映射，普通空格转换为全角空格，其他字符保持不变。
 *
 * @receiver 原始字符串
 * @return 转换后的字符串
 */
@JsName("stringToFullWidthString")
fun String.toFullWidthString(): String {
    val sb = StringBuilder(length)
    for (c in this) {
        when (c.code) {
            in 33..126 -> sb.append((c.code + 65248).toChar())
            32 -> sb.append("　")
            else -> sb.append(c)
        }
    }
    return sb.toString()
}

private fun Char.isHexDigit(): Boolean =
    this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'
