@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport

/**
 * 提取缓冲区限制范围内的指定比特并组成新的字节数组。
 *
 * 位索引从缓冲区索引 `0` 处字节的最高有效位开始。结果不足整字节时默认向高位补
 * `0`。该操作不改变缓冲区位置。
 *
 * @receiver 要读取的字节缓冲区
 * @param start 起始位索引
 * @param length 要提取的位数
 * @param paddingHigh `true` 时向高位补 `0`，`false` 时向低位补 `0`
 * @return 包含所选比特的新字节数组
 * @throws IllegalArgumentException 当范围为负数或超出缓冲区限制时
 */
@kotlin.jvm.JvmOverloads
fun ByteBuffer.getBitRange(start: Int, length: Int, paddingHigh: Boolean = true): ByteArray {
    require(start >= 0) { "start must be non-negative, but was $start" }
    require(length >= 0) { "length must be non-negative, but was $length" }
    val bitLimit = limit().toLong() * Byte.SIZE_BITS
    require(start.toLong() <= bitLimit && length.toLong() <= bitLimit - start) {
        "Bit range [$start, ${start.toLong() + length}) exceeds $bitLimit bits"
    }
    if (length == 0) return byteArrayOf()

    val firstByte = start / Byte.SIZE_BITS
    val lastByteExclusive =
        ((start.toLong() + length + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS).toInt()
    return ByteArray(lastByteExclusive - firstByte) { this[firstByte + it] }
        .getBitRange(start % Byte.SIZE_BITS, length, paddingHigh)
}
