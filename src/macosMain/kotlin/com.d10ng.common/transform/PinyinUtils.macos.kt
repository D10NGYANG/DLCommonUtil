package com.d10ng.common.transform

import kotlinx.cinterop.*
import platform.CoreFoundation.*

actual fun String.toPinYinDo(separator: String): String {
    return this.map { it.toPinYin() }.joinToString(separator = separator)
}

@OptIn(ExperimentalForeignApi::class)
private fun Char.toPinYin(): String {
    val cfString = CFStringCreateWithCString(null, "$this", kCFStringEncodingUTF8)
    val mutableString = CFStringCreateMutableCopy(null, 0 , cfString)
    CFStringTransform(mutableString, null, kCFStringTransformToLatin, false)
    CFStringTransform(mutableString, null, kCFStringTransformStripDiacritics, false)

    return mutableString?.let { string ->
        memScoped {
            val bufferSize = CFStringGetLength(string) + 1
            val buffer = allocArray<ByteVar>(bufferSize)
            CFStringGetCString(string, buffer, bufferSize, kCFStringEncodingUTF8)
            buffer.toKString().uppercase()
        }
    }?: ""
}