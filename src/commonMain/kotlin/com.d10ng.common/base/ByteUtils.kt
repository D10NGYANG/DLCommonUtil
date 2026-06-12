@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将字节转换为固定 8 位的二进制字符串。
 *
 * @receiver 要转换的字节
 * @return 由 `0` 和 `1` 组成的 8 字符字符串
 */
@JsName("byteToBinString")
fun Byte.toBinString() =
    this.toUByte().toString(2).padStart(8, '0')

/**
 * 将字节转换为固定 2 位的十六进制字符串。
 *
 * @receiver 要转换的字节
 * @param uppercase `true` 时使用大写字母，`false` 时使用小写字母
 * @return 2 字符十六进制字符串
 */
@Deprecated(
    message = "Use Kotlin's Byte.toHexString(HexFormat) instead.",
    replaceWith = ReplaceWith(
        "this.toHexString(HexFormat { upperCase = uppercase })",
        "kotlin.text.HexFormat",
    ),
)
@OptIn(ExperimentalStdlibApi::class)
@JsName("byteToHexString")
fun Byte.toHexString(uppercase: Boolean = true): String {
    return this.toHexString(HexFormat { upperCase = uppercase })
}

/**
 * 将字节按无符号值转换为 [Int]。
 *
 * @receiver 要转换的字节
 * @return 范围为 `0..255` 的整数
 */
@Deprecated(
    message = "Use toUByte().toInt() instead.",
    replaceWith = ReplaceWith("this.toUByte().toInt()"),
)
@JsName("byteToUnsignedInt")
fun Byte.toUnsignedInt() = toUByte().toInt()

/**
 * 获取字节中指定位置的比特值。
 *
 * 位索引从最低有效位开始，范围为 `0..7`。
 *
 * @receiver 要读取的字节
 * @param bitIndex 从最低有效位开始的位索引
 * @return `0` 或 `1`
 * @throws IndexOutOfBoundsException 当 [bitIndex] 不在 `0..7` 时
 */
@JsName("byteGetBit")
fun Byte.getBit(bitIndex: Int): Int {
    if (bitIndex !in 0 until Byte.SIZE_BITS) {
        throw IndexOutOfBoundsException("bitIndex must be in 0..7, but was $bitIndex")
    }
    return (toInt() ushr bitIndex) and 0x01
}
