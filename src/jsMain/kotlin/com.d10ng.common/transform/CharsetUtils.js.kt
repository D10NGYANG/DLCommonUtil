package com.d10ng.common.transform

@JsModule("iconv-lite")
@JsNonModule
private external object IconvLite {
    fun encode(value: String, encoding: String): dynamic
    fun decode(value: dynamic, encoding: String): String
}

@JsModule("buffer")
@JsNonModule
private external object NodeBufferModule {
    val Buffer: NodeBufferConstructor
}

private external interface NodeBufferConstructor {
    fun from(value: ByteArray): dynamic
}

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@JsExport
actual fun String.encodeGBK(): ByteArray {
    return encode("gbk")
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@JsExport
actual fun ByteArray.decodeGBK(): String {
    return decode("gbk")
}

@JsExport
actual fun String.encodeGB18030(): ByteArray {
    return encode("gb18030")
}

@JsExport
actual fun ByteArray.decodeGB18030(): String {
    return decode("gb18030")
}

private fun String.encode(encoding: String): ByteArray {
    val buffer = IconvLite.encode(this, encoding)
    return ByteArray(buffer.length as Int) { index ->
        (buffer[index] as Number).toByte()
    }
}

private fun ByteArray.decode(encoding: String): String =
    IconvLite.decode(NodeBufferModule.Buffer.from(this), encoding)
