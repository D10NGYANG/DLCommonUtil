package com.d10ng.common.base

import kotlin.math.ceil

/**
 * 将 无符号整型 转 N 个字节的 ByteArray
 * > 字节数设置为小于等于0，则根据整型值自动计算字节数（默认设置为0）;
 * > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
 * @receiver [Int] 整型
 * @param size [Int] 字节数，默认为0
 * @return [ByteArray] 字节数组
 */
fun Int.toByteArray(size: Int = 0): ByteArray {
    if (this < 0) throw IllegalArgumentException("Int must be unsigned")
    val hex = toString(16)
    val length = if (size <= 0) ceil(hex.length / 2.0).toInt() else size
    return hex.padStart(length * 2, '0').toByteArrayFromHex()
}