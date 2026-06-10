package com.d10ng.common.transform

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.set
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.iconv.iconv
import platform.iconv.iconv_close
import platform.iconv.iconv_open
import platform.posix.E2BIG
import platform.posix.EILSEQ
import platform.posix.EINVAL
import platform.posix.errno
import platform.posix.size_tVar

/**
 * 将字符串转换成字节数组，编码格式为GBK
 * @receiver [String] 字符串
 * @return [ByteArray] GBK编码的字节数组
 */
@OptIn(ExperimentalForeignApi::class)
actual fun String.encodeGBK(): ByteArray {
    return encode("GBK")
}

/**
 * 将字节数组转换成字符串，编码格式为GBK
 * @receiver [ByteArray] GBK编码的字节数组
 * @return [String] 字符串
 */
@OptIn(ExperimentalForeignApi::class)
actual fun ByteArray.decodeGBK(): String {
    return decode("GBK")
}

@OptIn(ExperimentalForeignApi::class)
actual fun String.encodeGB18030(): ByteArray {
    return encode("GB18030")
}

@OptIn(ExperimentalForeignApi::class)
actual fun ByteArray.decodeGB18030(): String {
    return decode("GB18030")
}

@OptIn(ExperimentalForeignApi::class)
private fun String.encode(encoding: String): ByteArray =
    convertEncoding(
        input = encodeToByteArray(),
        fromEncoding = "UTF-8",
        toEncoding = encoding,
        replacement = byteArrayOf('?'.code.toByte()),
        invalidSequenceLength = ::utf8SequenceLength,
    )

@OptIn(ExperimentalForeignApi::class)
private fun ByteArray.decode(encoding: String): String =
    convertEncoding(
        input = this,
        fromEncoding = encoding,
        toEncoding = "UTF-8",
        replacement = byteArrayOf(0xEF.toByte(), 0xBF.toByte(), 0xBD.toByte()),
        invalidSequenceLength = { 1 },
    ).decodeToString()

private fun utf8SequenceLength(firstByte: Int): Int = when {
    firstByte and 0x80 == 0 -> 1
    firstByte and 0xE0 == 0xC0 -> 2
    firstByte and 0xF0 == 0xE0 -> 3
    firstByte and 0xF8 == 0xF0 -> 4
    else -> 1
}

@OptIn(ExperimentalForeignApi::class)
private fun convertEncoding(
    input: ByteArray,
    fromEncoding: String,
    toEncoding: String,
    replacement: ByteArray,
    invalidSequenceLength: (Int) -> Int,
): ByteArray {
    if (input.isEmpty()) return byteArrayOf()

    val converter = iconv_open(toEncoding, fromEncoding)
        ?: error("iconv does not support $fromEncoding -> $toEncoding")
    try {
        return memScoped {
            val outputCapacity = input.size * 3 + replacement.size
            val output = allocArray<ByteVar>(outputCapacity)
            val outputPointer = alloc<CPointerVar<ByteVar>>()
            val outputBytesLeft = alloc<size_tVar>()
            outputPointer.value = output
            outputBytesLeft.value = outputCapacity.toULong()

            input.usePinned { pinnedInput ->
                val inputPointer = alloc<CPointerVar<ByteVar>>()
                val inputBytesLeft = alloc<size_tVar>()
                inputPointer.value = pinnedInput.addressOf(0)
                inputBytesLeft.value = input.size.toULong()

                while (inputBytesLeft.value > 0u) {
                    val result = iconv(
                        converter,
                        inputPointer.ptr,
                        inputBytesLeft.ptr,
                        outputPointer.ptr,
                        outputBytesLeft.ptr,
                    )
                    if (result != ULong.MAX_VALUE) continue

                    when (errno) {
                        EILSEQ, EINVAL -> {
                            replacement.forEach {
                                outputPointer.value!![0] = it
                                outputPointer.value = outputPointer.value!! + 1
                            }
                            outputBytesLeft.value -= replacement.size.toULong()
                            val skip = invalidSequenceLength(inputPointer.value!![0].toInt() and 0xFF)
                                .coerceAtMost(inputBytesLeft.value.toInt())
                            inputPointer.value = inputPointer.value!! + skip
                            inputBytesLeft.value -= skip.toULong()
                        }
                        E2BIG -> error("GBK conversion output buffer is unexpectedly full")
                        else -> error("GBK conversion failed with errno $errno")
                    }
                }
            }

            val outputSize = outputCapacity - outputBytesLeft.value.toInt()
            output.readBytes(outputSize)
        }
    } finally {
        iconv_close(converter)
    }
}
