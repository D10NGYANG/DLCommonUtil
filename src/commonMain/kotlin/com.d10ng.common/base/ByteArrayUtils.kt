@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将字节数组转换为二进制字符串。
 *
 * @receiver 要转换的字节数组
 * @param space 是否在相邻字节之间插入空格
 * @return 每个字节固定占 8 位的二进制字符串
 */
@JsName("byteArrayToBinString")
fun ByteArray.toBinString(space: Boolean = false): String {
    if (isEmpty()) return ""
    val separatorSize = if (space) size - 1 else 0
    val expectedLength = (size.toLong() * Byte.SIZE_BITS + separatorSize)
        .coerceAtMost(Int.MAX_VALUE.toLong())
        .toInt()
    return buildString(expectedLength) {
        this@toBinString.forEachIndexed { index, byte ->
            if (space && index > 0) append(' ')
            append(byte.toBinString())
        }
    }
}

/**
 * 将字节数组转换为十六进制字符串。
 *
 * @receiver 要转换的字节数组
 * @param space 是否在相邻字节之间插入空格
 * @param uppercase `true` 时使用大写字母，`false` 时使用小写字母
 * @return 每个字节固定占 2 位的十六进制字符串
 */
@Deprecated(
    message = "Use Kotlin's ByteArray.toHexString(HexFormat) instead.",
    replaceWith = ReplaceWith(
        "this.toHexString(HexFormat { upperCase = uppercase; bytes.byteSeparator = if (space) \" \" else \"\" })",
        "kotlin.text.HexFormat",
    ),
)
@JsName("byteArrayToHexString")
fun ByteArray.toHexString(space: Boolean = false, uppercase: Boolean = true): String {
    return this.toHexString(
        HexFormat {
            upperCase = uppercase
            bytes.byteSeparator = if (space) " " else ""
        },
    )
}

/**
 * 将不超过 4 个大端序字节合并为 [Int]。
 *
 * 不足 4 字节时按无符号数值解析；4 字节结果可能因最高位为 `1` 而表现为负数。
 *
 * @receiver 包含大端序整数的字节数组
 * @return 合并后的整数；空数组返回 `0`
 * @throws IllegalArgumentException 当数组长度大于 4 时
 */
@JsName("byteArrayToUnsignedInt")
fun ByteArray.toUnsignedInt(): Int {
    require(size <= Int.SIZE_BYTES) {
        "ByteArray size must not exceed ${Int.SIZE_BYTES}, but was $size"
    }
    var result = 0
    for (byte in this) {
        result = (result shl Byte.SIZE_BITS) or (byte.toInt() and 0xff)
    }
    return result
}

/**
 * 将不超过 8 个大端序字节合并为 [Long]。
 *
 * 不足 8 字节时按无符号数值解析；8 字节结果可能因最高位为 `1` 而表现为负数。
 *
 * @receiver 包含大端序长整数的字节数组
 * @return 合并后的长整数；空数组返回 `0`
 * @throws IllegalArgumentException 当数组长度大于 8 时
 */
@JsName("byteArrayToUnsignedLong")
fun ByteArray.toUnsignedLong(): Long {
    require(size <= Long.SIZE_BYTES) {
        "ByteArray size must not exceed ${Long.SIZE_BYTES}, but was $size"
    }
    var result = 0L
    for (byte in this) {
        result = (result shl Byte.SIZE_BITS) or (byte.toLong() and 0xffL)
    }
    return result
}

/**
 * 查找子字节数组首次出现的位置。
 *
 * @receiver 被搜索的字节数组
 * @param bs 要查找的子字节数组
 * @return 首次匹配的起始索引；未找到或 [bs] 为空时返回 `-1`
 */
@JsName("indexOfByteArray")
fun ByteArray.indexOf(bs: ByteArray): Int {
    if (bs.isEmpty() || bs.size > size) return -1
    if (bs.size == 1) return indexOf(bs[0])

    val prefixLengths = IntArray(bs.size)
    var prefixLength = 0
    for (index in 1 until bs.size) {
        while (prefixLength > 0 && bs[index] != bs[prefixLength]) {
            prefixLength = prefixLengths[prefixLength - 1]
        }
        if (bs[index] == bs[prefixLength]) prefixLength++
        prefixLengths[index] = prefixLength
    }

    var matchedLength = 0
    for (index in indices) {
        while (matchedLength > 0 && this[index] != bs[matchedLength]) {
            matchedLength = prefixLengths[matchedLength - 1]
        }
        if (this[index] == bs[matchedLength]) matchedLength++
        if (matchedLength == bs.size) return index - bs.lastIndex
    }
    return -1
}

/**
 * 从数组开头填充或截取到指定长度。
 *
 * 当目标长度小于原数组长度时，保留末尾的 [length] 个字节。
 *
 * @receiver 原始字节数组
 * @param length 目标长度，必须为非负数
 * @param padByte 填充值
 * @return 新的定长字节数组
 * @throws IllegalArgumentException 当 [length] 为负数时
 */
@JsName("byteArrayPadStart")
fun ByteArray.padStart(length: Int, padByte: Byte = 0x00): ByteArray {
    require(length >= 0) { "length must be non-negative, but was $length" }
    if (size >= length) return copyOfRange(size - length, size)
    return ByteArray(length) { padByte }.also {
        copyInto(it, destinationOffset = length - size)
    }
}

/**
 * 从数组末尾填充或截取到指定长度。
 *
 * 当目标长度小于原数组长度时，保留开头的 [length] 个字节。
 *
 * @receiver 原始字节数组
 * @param length 目标长度，必须为非负数
 * @param padByte 填充值
 * @return 新的定长字节数组
 * @throws IllegalArgumentException 当 [length] 为负数时
 */
@JsName("byteArrayPadEnd")
fun ByteArray.padEnd(length: Int, padByte: Byte = 0x00): ByteArray {
    require(length >= 0) { "length must be non-negative, but was $length" }
    if (size >= length) return copyOf(length)
    return ByteArray(length) { padByte }.also {
        copyInto(it)
    }
}

/**
 * 提取指定范围的比特并组成新的字节数组。
 *
 * 位索引从数组第一个字节的最高有效位开始。结果不足整字节时默认向高位补 `0`。
 *
 * @receiver 原始字节数组
 * @param start 起始位索引
 * @param length 要提取的位数
 * @param paddingHigh `true` 时向高位补 `0`，`false` 时向低位补 `0`
 * @return 包含所选比特的新字节数组
 * @throws IllegalArgumentException 当范围为负数或超出数组时
 */
@kotlin.jvm.JvmOverloads
@JsName("byteArrayGetBitRange")
fun ByteArray.getBitRange(start: Int, length: Int, paddingHigh: Boolean = true): ByteArray {
    require(start >= 0) { "start must be non-negative, but was $start" }
    require(length >= 0) { "length must be non-negative, but was $length" }
    val bitSize = size.toLong() * Byte.SIZE_BITS
    require(start.toLong() <= bitSize && length.toLong() <= bitSize - start) {
        "Bit range [$start, ${start.toLong() + length}) exceeds $bitSize bits"
    }
    if (length == 0) return byteArrayOf()

    val resultSize = length / Byte.SIZE_BITS +
        if (length % Byte.SIZE_BITS == 0) 0 else 1
    val result = ByteArray(resultSize)
    val destinationPadding = if (paddingHigh) {
        (Byte.SIZE_BITS - length % Byte.SIZE_BITS) % Byte.SIZE_BITS
    } else {
        0
    }
    for (offset in 0 until length) {
        val sourceBitIndex = start + offset
        val sourceBit = (this[sourceBitIndex / Byte.SIZE_BITS].toInt() ushr
            (Byte.SIZE_BITS - 1 - sourceBitIndex % Byte.SIZE_BITS)) and 1
        if (sourceBit != 0) {
            val destinationBitIndex = destinationPadding + offset
            val destinationByteIndex = destinationBitIndex / Byte.SIZE_BITS
            val destinationMask = 1 shl (Byte.SIZE_BITS - 1 - destinationBitIndex % Byte.SIZE_BITS)
            result[destinationByteIndex] =
                (result[destinationByteIndex].toInt() or destinationMask).toByte()
        }
    }
    return result
}
