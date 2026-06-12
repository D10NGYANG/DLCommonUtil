@file:JsExport

package com.d10ng.common.calculate

import kotlin.experimental.or
import kotlin.experimental.xor
import kotlin.js.JsExport

/**
 * 校验和计算方式。
 */
enum class ChecksumType {
    /**
     * 兼容旧版的逐字节累加校验。
     *
     * 该枚举名称历史上有误，实际行为不是按位与。
     */
    @Deprecated(
        message = "AND historically performs addition. Use SUM instead.",
        replaceWith = ReplaceWith("ChecksumType.SUM"),
    )
    AND,

    /** 逐字节按位或校验。 */
    OR,

    /** 逐字节按位异或校验。 */
    XOR,

    /** 逐字节累加并保留低 8 位。 */
    SUM,
}

/**
 * 校验字节数组末尾保存的校验和。
 *
 * 数组至少需要包含一个数据字节和一个校验和字节。
 *
 * @receiver 最后一字节为校验和的字节数组
 * @param type 校验和计算方式
 * @return 校验和一致时返回 `true`；数组长度不足或校验失败时返回 `false`
 */
fun ByteArray.assertChecksum(type: ChecksumType = ChecksumType.XOR): Boolean {
    if (size < 2) return false
    val checksumIndex = lastIndex
    return getChecksum(type, length = checksumIndex) == this[checksumIndex]
}

/**
 * 计算字节数组指定范围的校验和。
 *
 * 空范围的校验和为 `0`。累加结果按字节自然截断，只保留低 8 位。
 *
 * @receiver 待计算的字节数组
 * @param type 校验和计算方式
 * @param start 起始索引，可等于数组长度以表示空范围
 * @param length 从 [start] 开始计算的字节数
 * @return 指定范围的校验和
 * @throws IllegalArgumentException 当 [start] 或 [length] 不能构成有效数组范围时
 */
@Suppress("DEPRECATION")
fun ByteArray.getChecksum(
    type: ChecksumType = ChecksumType.XOR,
    start: Int = 0,
    length: Int = size - start,
): Byte {
    require(start in 0..size) {
        "start must be in 0..$size, but was $start"
    }
    require(length >= 0 && length <= size - start) {
        "length must be in 0..${size - start} for start $start, but was $length"
    }

    var checksum = 0
    val endExclusive = start + length
    for (index in start until endExclusive) {
        checksum = when (type) {
            ChecksumType.AND,
            ChecksumType.SUM,
            -> (checksum + this[index].toInt()) and 0xFF
            ChecksumType.OR -> checksum or this[index].toUByte().toInt()
            ChecksumType.XOR -> checksum xor this[index].toUByte().toInt()
        }
    }
    return checksum.toByte()
}

/**
 * 计算当前数组的校验和并追加到数组末尾。
 *
 * @receiver 待添加校验和的字节数组
 * @param type 校验和计算方式
 * @return 包含原始数据和末尾校验和的新数组
 */
fun ByteArray.addChecksum(type: ChecksumType = ChecksumType.XOR): ByteArray {
    return plus(getChecksum(type))
}
