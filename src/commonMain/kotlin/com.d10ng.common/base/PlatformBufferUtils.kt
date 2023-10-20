package com.d10ng.common.base

import com.ditchoom.buffer.PlatformBuffer
import kotlin.math.ceil

/**
 * 获取指定范围的比特位组成新的字节数组
 * @receiver [PlatformBuffer] 原始字节数组
 * @param start [Int] 开始比特位置
 * @param length [Int] 比特位长度
 * @return [ByteArray] 新的字节数组
 */
fun PlatformBuffer.getBitRange(start: Int, length: Int): ByteArray {
    if (length <= 0) return byteArrayOf()
    if (start < 0) throw IllegalArgumentException("start must be greater than 0")
    if (start + length > capacity * 8) throw IllegalArgumentException("start + offset must be less than capacity * 8")
    // 右移位数
    val right = (8 - (start + length) % 8) % 8
    // 左侧清空位数
    val left = (8 - length % 8) % 8
    // 最终生成字节数组长度
    val resSize = ceil(length / 8.0).toInt()
    // 字节数组
    val result = ByteArray(resSize)
    // 开始的字节位置
    val startByteIdx = start / 8
    // 结束字节位置
    val endByteIdx = ceil((start + length) / 8.0).toInt()
    // 缓存区
    val buf = this
    // 原始字节数据长度
    val originSize = endByteIdx - startByteIdx
    var idx = originSize - 1
    // 循环将最后一个字节的数据写入到结果数组中
    while (resSize - (originSize - idx) >= 0) {
        // 读取缓存区的第idx位置的字节，向右移动right位
        var temp = buf[idx + startByteIdx].toUnsignedInt() shr right
        // 如果idx不为0，读取缓存区的第idx-1位置的字节，向左移动8-right位
        if (idx > 0) temp = temp or (buf[idx + startByteIdx - 1].toUnsignedInt() shl (8 - right))
        result[resSize - (originSize - idx)] = temp.toByte()
        idx --
    }
    // 清空第一个字节的左侧bit数据
    result[0] = (result[0].toUnsignedInt() shl left and 0xff shr left).toByte()
    return result
}