package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 [Long] 转换为大端序 [ByteArray]。
 *
 * 当 [size] 为 `null` 时，非负数使用能够容纳当前值的最少字节数，负数固定使用
 * 8 字节以保留完整的二进制补码。值 `0` 使用 1 字节表示。
 *
 * 当显式指定 [size] 时：
 * - 仅允许 1 到 8 字节；
 * - 长度大于非负值所需长度时，在高位补 `0`；
 * - 长度小于值所需长度时，仅保留低位字节。
 *
 * 例如，`0x123456789abcdef0L.toByteArray(4)` 的结果为
 * `[0x9A, 0xBC, 0xDE, 0xF0]`。
 *
 * @receiver 要转换的长整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 8 之间
 * @return 表示当前值低位字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 8 之间时
 */
fun Long.toByteArray(size: Int? = null): ByteArray {
    if (size != null) {
        require(size in 1..Long.SIZE_BYTES) {
            "size must be between 1 and ${Long.SIZE_BYTES}, but was $size"
        }
    }
    val length = size ?: run {
        val significantBitCount = Long.SIZE_BITS - countLeadingZeroBits()
        ((significantBitCount + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS).coerceAtLeast(1)
    }

    var remaining = this
    return ByteArray(length).also { bytes ->
        for (index in bytes.lastIndex downTo 0) {
            bytes[index] = (remaining and 0xff).toByte()
            remaining = remaining ushr Byte.SIZE_BITS
        }
    }
}

/**
 * 将 [long] 转换为大端序 [ByteArray]。
 *
 * 该函数是 [Long.toByteArray] 的普通函数形式，尺寸规则、截断行为和异常条件均与其一致。
 *
 * @param long 要转换的长整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 8 之间
 * @return 表示 [long] 低位字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 8 之间时
 */
fun longToByteArray(long: Long, size: Int? = null): ByteArray = long.toByteArray(size)

/**
 * 将大端序 [ByteArray] 转换为 [Long]。
 *
 * 数组长度必须为 1 到 8。长度不足 8 字节时按无符号数值解析，即在高位补 `0`；
 * 因此单字节 `[0xFF]` 的结果是 `255`，而八字节
 * `[0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]` 的结果是 `-1`。
 *
 * @receiver 包含大端序长整型的字节数组
 * @return 由数组中的二进制位组成的长整型值
 * @throws IllegalArgumentException 当数组为空或长度大于 8 时
 */
@JsExport
@JsName("byteArrayToLong")
fun ByteArray.toLong(): Long {
    require(size in 1..Long.SIZE_BYTES) {
        "ByteArray size must be between 1 and ${Long.SIZE_BYTES}, but was $size"
    }

    var value = 0L
    for (byte in this) {
        value = (value shl Byte.SIZE_BITS) or (byte.toLong() and 0xffL)
    }
    return value
}
