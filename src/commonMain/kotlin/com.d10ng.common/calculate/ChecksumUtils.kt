@file:JsExport
package com.d10ng.common.calculate

import kotlin.experimental.or
import kotlin.experimental.xor
import kotlin.js.JsExport
import kotlin.math.min

/**
 * 检验和类型
 */
enum class ChecksumType {
    AND,
    OR,
    XOR
}

/**
 * 从字节数组中检验校验和，其中最后一位为校验和
 * @receiver [ByteArray] 字节数组
 * @param type [ChecksumType] 计算方式，默认异或校验；
 * @return [Boolean] true:检验成功; false:检验失败;
 */
fun ByteArray.assertChecksum(type: ChecksumType = ChecksumType.XOR) : Boolean {
    if (size < 2) return false
    val end = size -1
    val num = getChecksum(type, 0, end)
    return num == this[end]
}

/**
 * 获取校验和
 * @param type [ChecksumType] 计算方式，默认异或校验；
 * @param start [Int] 开始位置
 * @param length [Int] 长度
 * @return [Byte] 校验和
 */
fun ByteArray.getChecksum(type: ChecksumType = ChecksumType.XOR, start: Int = 0, length: Int = (size - start)) : Byte {
    var num = (0).toByte()
    val end = min(start + length, size)
    for (i in start until end) {
        num = when(type) {
            ChecksumType.AND -> ((num + this[i]).toByte())
            ChecksumType.OR -> (num or this[i])
            ChecksumType.XOR -> (num xor this[i])
        }
    }
    return num
}

/**
 * 添加校验和
 * @param type [ChecksumType] 计算方式，默认异或校验；
 * @return [ByteArray] 增加较验和后的字节数组
 */
fun ByteArray.addChecksum(type: ChecksumType = ChecksumType.XOR) = plus(this.getChecksum(type))