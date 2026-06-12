package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 [Short] 转换为大端序 [ByteArray]。
 *
 * 当 [size] 为 `null` 时，`0..255` 使用 1 字节，其余值使用 2 字节。
 * 负数固定使用 2 字节，以保留完整的二进制补码。
 *
 * 当显式指定 [size] 时：
 * - 仅允许 1 到 2 字节；
 * - 长度大于非负值所需长度时，在高位补 `0`；
 * - 长度小于值所需长度时，仅保留低位字节。
 *
 * 例如，`0x1234.toShort().toByteArray(1)` 的结果为 `[0x34]`。
 *
 * @receiver 要转换的短整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 2 之间
 * @return 表示当前值低位字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 2 之间时
 */
fun Short.toByteArray(size: Int? = null): ByteArray {
    if (size != null) {
        require(size in 1..Short.SIZE_BYTES) {
            "size must be between 1 and ${Short.SIZE_BYTES}, but was $size"
        }
    }
    val length = size ?: run {
        val significantBitCount = Short.SIZE_BITS - countLeadingZeroBits()
        ((significantBitCount + Byte.SIZE_BITS - 1) / Byte.SIZE_BITS).coerceAtLeast(1)
    }

    var remaining = toInt()
    return ByteArray(length).also { bytes ->
        for (index in bytes.lastIndex downTo 0) {
            bytes[index] = (remaining and 0xff).toByte()
            remaining = remaining ushr Byte.SIZE_BITS
        }
    }
}

/**
 * 将 [short] 转换为大端序 [ByteArray]。
 *
 * 该函数是 [Short.toByteArray] 的普通函数形式，尺寸规则、截断行为和异常条件均与其一致。
 *
 * @param short 要转换的短整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 2 之间
 * @return 表示 [short] 低位字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 2 之间时
 */
fun shortToByteArray(short: Short, size: Int? = null): ByteArray =
    short.toByteArray(size)

/**
 * 将大端序 [ByteArray] 转换为 [Short]。
 *
 * 数组长度必须为 1 到 2。单字节数组按无符号数值解析，即在高位补 `0`；
 * 因此 `[0xFF]` 的结果是 `255`。双字节数组按 [Short] 的 16 位二进制补码解析，
 * 因此 `[0xFF, 0xFF]` 的结果是 `-1`。
 *
 * @receiver 包含大端序短整型的字节数组
 * @return 由数组中的二进制位组成的短整型值
 * @throws IllegalArgumentException 当数组为空或长度大于 2 时
 */
@JsExport
@JsName("byteArrayToShort")
fun ByteArray.toShort(): Short {
    require(size in 1..Short.SIZE_BYTES) {
        "ByteArray size must be between 1 and ${Short.SIZE_BYTES}, but was $size"
    }

    var value = 0
    for (byte in this) {
        value = (value shl Byte.SIZE_BITS) or (byte.toInt() and 0xff)
    }
    return value.toShort()
}
