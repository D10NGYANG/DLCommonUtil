package com.d10ng.common.base

import java.nio.ByteBuffer

/**
 * 获取指定范围的比特位组成新的字节数组
 * @receiver [ByteBuffer] 原始字节数组
 * @param start [Int] 开始比特位置
 * @param length [Int] 比特位长度
 * @param paddingHigh [Boolean] 是否向高位补0，默认为true；false表示向低位补0
 * @return [ByteArray] 新的字节数组
 */
@JvmOverloads
fun ByteBuffer.getBitRange(
    start: Int,
    length: Int,
    paddingHigh: Boolean = true,
): ByteArray {
    require(start >= 0 && length >= 0) { "start and length must be non-negative" }
    val bitLimit = limit().toLong() * Byte.SIZE_BITS
    require(start.toLong() <= bitLimit && length.toLong() <= bitLimit - start) {
        "Bit range [$start, ${start.toLong() + length}) exceeds $bitLimit bits"
    }
    if (length == 0) return byteArrayOf()
    val firstByte = start / Byte.SIZE_BITS
    val endByte = ((start.toLong() + length + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS).toInt()
    return ByteArray(endByte - firstByte) { get(firstByte + it) }
        .getBitRange(start % Byte.SIZE_BITS, length, paddingHigh)
}
