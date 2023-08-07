package com.d10ng.common

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.CoreFoundation.CFStringConvertEncodingToNSStringEncoding
import platform.CoreFoundation.kCFStringEncodingGB_18030_2000
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.posix.memcpy

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String]
 * @return [ByteArray]
 */
actual fun String.encodeGBK(): ByteArray {
    val gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000.convert())
    val str = this as NSString
    val nsData = str.dataUsingEncoding(gbkEncoding)
    return if (nsData == null) byteArrayOf()
    else ByteArray(nsData.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), nsData.bytes, nsData.length)
        }
    }
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray]
 * @return [String]
 */
actual fun ByteArray.decodeGBK(): String {
    val gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000.convert())
    return this.usePinned {
        NSString.create(bytes = it.addressOf(0), length = this.size.convert(), encoding = gbkEncoding) as String
    }
}