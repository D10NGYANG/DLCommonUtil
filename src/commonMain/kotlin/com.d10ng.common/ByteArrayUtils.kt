package com.d10ng.common

/**
 * 将两个字节的 byte 数组合并成无符号整型
 * @receiver [ByteArray]
 * @return [Int]
 */
fun ByteArray.toInt(): Int {
    val ba = fillLength(2)
    return ba[0].toUByte().toInt() shl 8 or ba[1].toUByte().toInt()
}

/**
 * 将8个字节的 Byte 合并成无符号 Long
 * - Long 的最大值为 0x7FFFFFFFFFFFFFFF
 * @receiver [ByteArray]
 * @return [Long]
 */
fun ByteArray.toLong(): Long {
    val ba = fillLength(8)
    return (ba[0].toUByte().toLong() shl 56) or
            (ba[1].toUByte().toLong() shl 48) or
            (ba[2].toUByte().toLong() shl 40) or
            (ba[3].toUByte().toLong() shl 32) or
            (ba[4].toUByte().toLong() shl 24) or
            (ba[5].toUByte().toLong() shl 16) or
            (ba[6].toUByte().toLong() shl 8) or
            (ba[7].toUByte().toLong())
}

/**
 * 将 ByteArray 转为 8*N 二进制字符串 "00110011"
 * @receiver [ByteArray]
 * @param space [Boolean] 每个byte中间是否需要空格
 * @return [String]
 */
fun ByteArray.toBinString(space: Boolean = false): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toBinString())
        if (space) builder.append(" ")
    }
    return builder.toString()
}

/**
 * 将 ByteArray 转为 2*N 16进制字符串 "fcfc"
 * @receiver [ByteArray]
 * @param space [Boolean] 是否需要空格
 * @param uppercase [Boolean] 是否需要大写
 * @return [String]
 */
fun ByteArray.toHexString(space: Boolean = false, uppercase: Boolean = false): String {
    val builder = StringBuilder()
    for (byte in this) {
        builder.append(byte.toHexString())
        if (space) builder.append(" ")
    }
    val result = builder.toString()
    return if (uppercase) result.uppercase() else result.lowercase()
}

/**
 * 从ByteArray中查找特定ByteArray的位置
 * # 如果没有找到就返回-1
 * @receiver [ByteArray] 输入ByteArray
 * @param bs [ByteArray] 检索ByteArray
 * @return [Int] 首个位置index
 */
fun ByteArray.indexOf(bs: ByteArray): Int {
    var index = 0
    while (true) {
        val temp = this.copyOfRange(index, this.size)
        val i = temp.indexOf(bs[0])
        if (i < 0) return -1
        index += (i + 1)
        if (i + bs.size > temp.size) return -1
        if (temp.copyOfRange(i, i + bs.size).contentEquals(bs)) {
            return index -1
        }
    }
}

/**
 * Byte数组填充到指定长度
 * @receiver [ByteArray]
 * @param length [Int] 需要填充到的长度
 * @param filler [Byte] 填充物
 * @param isInStart [Boolean] 是否从开头处填充，
 *                            true：开头；
 *                            false：结尾
 * @param isForced [Boolean] 是否强制性要只保留指定长度数组，
 *                           true：如果ByteArray本身已经比输入长度[length]长，也要只截取其中的部分；
 *                           false：如果ByteArray本身已经比输入长度[length]长，那也不管；
 * @return [ByteArray]
 */
fun ByteArray.fillLength(
    length: Int,
    filler: Byte = 0x00,
    isInStart: Boolean = true,
    isForced: Boolean = true
): ByteArray {
    val list = mutableListOf<Byte>()
    if (!isInStart) list.addAll(this.toList())
    if (this.size < length) {
        for (i in 0 until length - this.size) {
            list.add(filler)
        }
    }
    if (isInStart) list.addAll(this.toList())
    return if (isForced) {
        if (isInStart) list.subList(list.size - length, list.size).toByteArray()
        else list.subList(0, length).toByteArray()
    } else list.toByteArray()
}