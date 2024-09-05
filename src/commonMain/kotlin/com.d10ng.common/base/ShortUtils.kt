package com.d10ng.common.base

/**
 * 将 短整型 转 N 个字节的 ByteArray
 * > 字节数设置为null（默认），则根据整型值自动计算字节数；
 * > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
 * @receiver [Short] 短整型
 * @param size [Int]? 字节数，默认为null，根据整型值自动计算字节数
 * @return [ByteArray] 字节数组
 */
fun Short.toByteArray(size: Int? = null): ByteArray {
    val length = when {
        size != null && size > 0 -> size
        this in 0..0xff -> 1
        else -> 2
    }
    val intValue = this.toInt()
    return ByteArray(length) { i ->
        (intValue shr ((length - 1 - i) * 8) and 0xFF).toByte()
    }
}

/**
 * 将 字节数组 转换成 短整型
 * @receiver [ByteArray] 最大长度为2的字节数组
 * @return [Short] 短整型
 */
fun ByteArray.toShort(): Short {
    if (this.isEmpty()) throw IllegalArgumentException("ByteArray must not be empty")
    if (size > 2) throw IllegalArgumentException("ByteArray must not be greater than 2 bytes")
    if (size == 1) return this[0].toShort()
    return ((this[0].toInt() and 0xff) shl 8 or (this[1].toInt() and 0xff)).toShort()
}