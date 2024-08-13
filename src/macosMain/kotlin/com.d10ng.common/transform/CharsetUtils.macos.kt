package com.d10ng.common.transform

import kotlinx.cinterop.*
import platform.CoreFoundation.CFStringConvertEncodingToNSStringEncoding
import platform.CoreFoundation.kCFStringEncodingGB_18030_2000
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun String.encodeGBKDo(): ByteArray {
    val gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000.convert())
    val str = NSString.create(string = this)
    val nsData = str.dataUsingEncoding(gbkEncoding)
    if (nsData?.length?.toLong() == 0L) return byteArrayOf()
    return nsData?.toByteArray() ?: byteArrayOf()
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun ByteArray.decodeGBKDo(): String {
    if (this.isEmpty()) return ""
    val gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000.convert())
    return this.usePinned {
        NSString.create(bytes = it.addressOf(0), length = this.size.convert(), encoding = gbkEncoding).toString()
    }
}