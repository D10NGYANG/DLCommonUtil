package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 无符号长整型 转 N 个字节 的 ByteArray
 * > 字节数设置为null（默认），则根据整型值自动计算字节数；
 * > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
 * @receiver [Long] 长整型
 * @param size [Int]? 字节数，默认为null，根据整型值自动计算字节数
 * @return [ByteArray] 字节数组
 */
fun Long.toByteArray(size: Int? = null): ByteArray {
    val length = when {
        size != null && size > 0 -> size
        this in 0..0xff -> 1
        this in 0x100 .. 0xffff -> 2
        this in 0x10000..0xffffff -> 3
        this in 0x1000000..0xffffffff -> 4
        this in 0x100000000..0xffffffffff -> 5
        this in 0x10000000000..0xffffffffffff -> 6
        this in 0x1000000000000..0xffffffffffffff -> 7
        else -> 8
    }
    return ByteArray(length) { i ->
        (this shr ((length - 1 - i) * 8) and 0xFF).toByte()
    }
}

/**
 * 将 无符号长整型 转 N 个字节 的 ByteArray
 * > 字节数设置为null（默认），则根据整型值自动计算字节数；
 * > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
 * @param long [Long] 长整型
 * @param size [Int]? 字节数，默认为null，根据整型值自动计算字节数
 * @return [ByteArray] 字节数组
 */
fun longToByteArray(long: Long, size: Int? = null): ByteArray {
    return long.toByteArray(size)
}

/**
 * 将 字节数组 转换成 长整型
 * @receiver [ByteArray] 最大长度为8的字节数组
 * @return [Int] 整型
 */
@JsExport
@JsName("byteArrayToLong")
@Suppress("NON_EXPORTABLE_TYPE")
fun ByteArray.toLong(): Long {
    if (this.isEmpty()) throw IllegalArgumentException("ByteArray must not be empty")
    if (this.size > 8) throw IllegalArgumentException("ByteArray must not be greater than 4 bytes")
    var value = 0L
    // 遍历字节数组的每个元素
    for (i in this.indices) {
        // 将每个字节转换为无符号整数（0~255）
        val byte = this[i].toLong() and 0xFF
        // 将每个字节左移相应的位数，并与 value 进行按位或运算
        value = value or (byte shl ((size - i - 1) * 8))
    }
    // 返回最终的整数值
    return value
}