package com.d10ng.common

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

/**
 * 检验和类型
 */
enum class ChecksumType {
    AND,
    OR,
    XOR
}

/**
 * 检验校验和
 * - 最后一位为校验和
 * @receiver [ByteArray]
 * @param type [ChecksumType] 计算方式
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
 * @param type [ChecksumType] 计算方式
 * @param start [Int] 开始位置
 * @param end [Int] 结束位置
 * @return [Byte] 校验和
 */
fun ByteArray.getChecksum(type: ChecksumType = ChecksumType.XOR, start: Int = 0, end: Int = size) : Byte {
    var num = (0).toByte()
    for (i in start until end) {
        num = when(type) {
            ChecksumType.AND -> (num and this[i])
            ChecksumType.OR -> (num or this[i])
            ChecksumType.XOR -> (num xor this[i])
        }
    }
    return num
}

/**
 * 添加较验和
 * @param type [ChecksumType] 计算方式
 * @return [ByteArray] 增加较验和后的Byte数组
 */
fun ByteArray.addChecksum(type: ChecksumType = ChecksumType.XOR): ByteArray {
    val list = this.toMutableList()
    list.add(this.getChecksum(type))
    return list.toByteArray()
}