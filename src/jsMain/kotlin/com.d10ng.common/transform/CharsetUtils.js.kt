package com.d10ng.common.transform

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@JsExport
actual fun String.encodeGBK(): ByteArray {
    return GBK.encode(this)
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@JsExport
actual fun ByteArray.decodeGBK(): String {
    return GBK.decode(this)
}