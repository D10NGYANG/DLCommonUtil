package com.d10ng.common

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String]
 * @return [ByteArray]
 */
actual fun String.encodeGBK(): ByteArray {
    return GBKHelper.getStringGBKByteArray(this)
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray]
 * @return [String]
 */
actual fun ByteArray.decodeGBK(): String {
    return GBKHelper.parseGBKByteArray2String(this)
}