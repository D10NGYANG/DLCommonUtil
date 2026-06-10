package com.d10ng.common.transform

import java.nio.charset.Charset
/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
actual fun String.encodeGBK(): ByteArray {
    return encode("GBK")
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
actual fun ByteArray.decodeGBK(): String {
    return decode("GBK")
}

actual fun String.encodeGB18030(): ByteArray {
    return encode("GB18030")
}

actual fun ByteArray.decodeGB18030(): String {
    return decode("GB18030")
}

private fun String.encode(charsetName: String): ByteArray =
    toByteArray(Charset.forName(charsetName))

private fun ByteArray.decode(charsetName: String): String =
    toString(Charset.forName(charsetName))
