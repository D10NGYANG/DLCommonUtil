package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 [Int] 转换为大端序 [ByteArray]。
 *
 * 当 [size] 为 `null` 时，非负数使用能够容纳当前值的最少字节数，负数固定使用
 * 4 字节以保留完整的二进制补码。值 `0` 使用 1 字节表示。
 *
 * 当显式指定 [size] 时：
 * - 仅允许 1 到 4 字节；
 * - 长度大于值所需长度时，在高位补 `0`；
 * - 长度小于值所需长度时，仅保留低位字节。
 *
 * 例如，`0x12345678.toByteArray(2)` 的结果为 `[0x56, 0x78]`。
 *
 * @receiver 要转换的整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 4 之间
 * @return 表示当前值低 [size] 字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 4 之间时
 */
fun Int.toByteArray(size: Int? = null): ByteArray {
    if (size != null) {
        require(size in 1..Int.SIZE_BYTES) {
            "size must be between 1 and ${Int.SIZE_BYTES}, but was $size"
        }
    }
    val length = size ?: when (this) {
        in 0..0xff -> 1
        in 0x100..0xffff -> 2
        in 0x10000..0xffffff -> 3
        else -> Int.SIZE_BYTES
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
 * 将 [value] 转换为大端序 [ByteArray]。
 *
 * 该函数是 [Int.toByteArray] 的普通函数形式，尺寸规则、截断行为和异常条件均与其一致。
 *
 * @param value 要转换的整型值
 * @param size 目标字节数；`null` 表示自动计算，非 `null` 时必须在 1 到 4 之间
 * @return 表示 [value] 低 [size] 字节（自动长度时为完整值）的大端序字节数组
 * @throws IllegalArgumentException 当 [size] 不在 1 到 4 之间时
 */
fun intToByteArray(value: Int, size: Int? = null): ByteArray = value.toByteArray(size)

/**
 * 将大端序 [ByteArray] 转换为 [Int]。
 *
 * 数组长度必须为 1 到 4。长度不足 4 字节时按无符号数值解析，即在高位补 `0`；
 * 因此单字节 `[0xFF]` 的结果是 `255`，而四字节
 * `[0xFF, 0xFF, 0xFF, 0xFF]` 的结果是 `-1`。
 *
 * @receiver 包含大端序整数的字节数组
 * @return 由数组中的二进制位组成的整型值
 * @throws IllegalArgumentException 当数组为空或长度大于 4 时
 */
@JsExport
@JsName("byteArrayToInt")
fun ByteArray.toInt(): Int {
    require(size in 1..Int.SIZE_BYTES) {
        "ByteArray size must be between 1 and ${Int.SIZE_BYTES}, but was $size"
    }

    var value = 0
    for (byte in this) {
        value = (value shl Byte.SIZE_BITS) or (byte.toInt() and 0xff)
    }
    return value
}
