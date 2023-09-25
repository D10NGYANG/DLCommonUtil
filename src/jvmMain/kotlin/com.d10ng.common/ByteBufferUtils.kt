package com.d10ng.common

import com.ditchoom.buffer.JvmBuffer
import java.nio.ByteBuffer

/**
 * 获取指定范围的比特位组成新的字节数组
 * @receiver [ByteBuffer] 原始字节数组
 * @param start [Int] 开始比特位置
 * @param offset [Int] 比特位长度
 * @return [ByteArray] 新的字节数组
 */
fun ByteBuffer.getBitRange(start: Int, offset: Int): ByteArray {
    return JvmBuffer(this).getBitRange(start, offset)
}