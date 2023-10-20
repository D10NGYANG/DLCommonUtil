@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.ditchoom.buffer.wrap
import kotlin.js.JsName

/**
 * 将 ByteArray 转为 8*N 二进制字符串 "00110011"
 * @receiver [ByteArray]
 * @param space [Boolean] 每个byte中间是否需要空格
 * @return [String]
 */
@JsName("byteArrayToBinString")
fun ByteArray.toBinString(space: Boolean = false): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toBinString())
        if (space) builder.append(" ")
    }
    return builder.toString().trim()
}

/**
 * 将 ByteArray 转为 2*N 16进制字符串 "fcfc"
 * @receiver [ByteArray]
 * @param space [Boolean] 是否需要空格，默认false不需要
 * @param uppercase [Boolean] 是否需要大写，默认true需要
 * @return [String]
 */
@JsName("byteArrayToHexString")
fun ByteArray.toHexString(space: Boolean = false, uppercase: Boolean = true): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toHexString())
        if (space) builder.append(" ")
    }
    val result = builder.toString().trim()
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 将 Byte 数组合并成无符号整型
 * > Int 的最大值为 0x7FFFFFFF，超过这个值会返回-1
 * @receiver [ByteArray]
 * @return [Int]
 */
@JsName("byteArrayToUnsignedInt")
fun ByteArray.toUnsignedInt() = toUnsignedLong().toInt()

/**
 * 将 Byte 数组合并成无符号长整型
 * > Long 的最大值为 0x7FFFFFFFFFFFFFFF，超过这个值会返回-1
 * @receiver [ByteArray]
 * @return [Long]
 */
@Suppress("NON_EXPORTABLE_TYPE")
@JsName("byteArrayToUnsignedLong")
fun ByteArray.toUnsignedLong(): Long {
    if (isEmpty()) return 0L
    val buffer = PlatformBuffer.wrap(this)
    var temp = buffer[0].toUnsignedInt().toLong()
    for (i in 1 until buffer.capacity) {
        temp = temp shl 8 or buffer[i].toUnsignedInt().toLong()
    }
    return temp
}

/**
 * 从ByteArray中查找特定ByteArray的位置
 * > 如果没有找到就返回-1
 * @receiver [ByteArray] 输入ByteArray
 * @param bs [ByteArray] 检索ByteArray
 * @return [Int] 首个位置index
 */
@JsName("indexOfByteArray")
fun ByteArray.indexOf(bs: ByteArray): Int {
    if (bs.isEmpty()) return -1
    if (bs.size > size) return -1
    if (bs.size == 1) return indexOf(bs[0])
    val buffer = PlatformBuffer.wrap(this)
    while (buffer.hasRemaining()) {
        if (buffer.remaining() < bs.size) return -1
        var idx = 0
        while (idx < bs.size) {
            if (buffer.readByte() != bs[idx]) break
            idx ++
        }
        if (idx == bs.size) return buffer.position() - idx
        buffer.position(buffer.position() - idx + 1)
    }
    return -1
}

/**
 * 使用指定Byte将ByteArray填充到指定长度，从前端开始填充。
 * @receiver [ByteArray] 原始ByteArray
 * @param length [Int] 需要填充到的长度
 * @param padByte [Byte] 填充物
 * @return [ByteArray] 填充后的ByteArray
 */
@JsName("byteArrayPadStart")
fun ByteArray.padStart(length: Int, padByte: Byte = 0x00): ByteArray {
    if (size >= length) return this.copyOfRange(size - length, size)
    val buf = PlatformBuffer.allocate(length)
    for (i in 0 until length - size) {
        buf[i] = padByte
    }
    buf.position(length - size)
    buf.writeBytes(this)
    buf.position(0)
    return buf.readByteArray(length)
}

/**
 * 使用指定Byte将ByteArray填充到指定长度，从后端开始填充。
 * @receiver [ByteArray] 原始ByteArray
 * @param length [Int] 需要填充到的长度
 * @param padByte [Byte] 填充物
 * @return [ByteArray] 填充后的ByteArray
 */
@JsName("byteArrayPadEnd")
fun ByteArray.padEnd(length: Int, padByte: Byte = 0x00): ByteArray {
    if (size >= length) return this.copyOfRange(0, length)
    val buf = PlatformBuffer.allocate(length)
    buf.writeBytes(this)
    for (i in 0 until length - size) {
        buf[i + size] = padByte
    }
    buf.position(0)
    return buf.readByteArray(length)
}

/**
 * 获取指定范围的比特位组成新的字节数组
 * @receiver [ByteArray] 原始字节数组
 * @param start [Int] 开始比特位置
 * @param length [Int] 比特位长度
 * @return [ByteArray] 新的字节数组
 */
@JsName("byteArrayGetBitRange")
fun ByteArray.getBitRange(start: Int, length: Int): ByteArray {
    // 缓存区
    val buf = PlatformBuffer.wrap(this)
    return buf.getBitRange(start, length)
}