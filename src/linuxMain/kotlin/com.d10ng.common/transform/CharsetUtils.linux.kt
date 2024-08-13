package com.d10ng.common.transform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.posix.wcstombs

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@OptIn(ExperimentalForeignApi::class)
actual fun String.encodeGBKDo(): ByteArray {
    val charset = "GBK"
    val size = wcstombs(null, this, 0)
    if (size == -1L) {
        throw IllegalArgumentException("Invalid string")
    }
    val array = ByteArray(size.toInt())
    val len = wcstombs(array.refTo(0), this, size)
    if (len == -1L) {
        throw IllegalArgumentException("Invalid string")
    }
    return array
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
actual fun ByteArray.decodeGBKDo(): String {
    TODO("Not yet implemented")
}