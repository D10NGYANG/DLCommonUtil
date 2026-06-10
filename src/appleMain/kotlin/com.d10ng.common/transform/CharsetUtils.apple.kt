package com.d10ng.common.transform

import kotlinx.cinterop.*
import platform.CoreFoundation.CFStringConvertEncodingToNSStringEncoding
import platform.CoreFoundation.CFStringEncodings
import platform.CoreFoundation.kCFStringEncodingGB_18030_2000
import platform.CoreFoundation.kCFStringEncodingGBK_95
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun String.encodeGBK(): ByteArray {
    return encode(kCFStringEncodingGBK_95)
}

actual fun ByteArray.decodeGBK(): String {
    return decode(kCFStringEncodingGBK_95)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun String.encodeGB18030(): ByteArray {
    return encode(kCFStringEncodingGB_18030_2000)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun ByteArray.decodeGB18030(): String {
    return decode(kCFStringEncodingGB_18030_2000)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun String.encode(encoding: CFStringEncodings): ByteArray {
    val nsEncoding = CFStringConvertEncodingToNSStringEncoding(encoding.convert())
    val str = NSString.create(string = this)
    val nsData = str.dataUsingEncoding(nsEncoding)
    if (nsData?.length?.toLong() == 0L) return byteArrayOf()
    return nsData?.toByteArray() ?: byteArrayOf()
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.decode(encoding: CFStringEncodings): String {
    if (this.isEmpty()) return ""
    val nsEncoding = CFStringConvertEncodingToNSStringEncoding(encoding.convert())
    return this.usePinned {
        NSString.create(bytes = it.addressOf(0), length = this.size.convert(), encoding = nsEncoding).toString()
    }
}
