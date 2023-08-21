package com.d10ng.common

import org.khronos.webgl.Uint16Array
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.khronos.webgl.set

object GBKHelper {

    // 编码表
    private val table = Uint16Array(65536)

    init {
        val ranges = arrayOf(
            arrayOf(0xA1, 0xA9,  0xA1, 0xFE),
            arrayOf(0xB0, 0xF7,  0xA1, 0xFE),
            arrayOf(0x81, 0xA0,  0x40, 0xFE),
            arrayOf(0xAA, 0xFE,  0x40, 0xA0),
            arrayOf(0xA8, 0xA9,  0x40, 0xA0),
            arrayOf(0xAA, 0xAF,  0xA1, 0xFE),
            arrayOf(0xF8, 0xFE,  0xA1, 0xFE),
            arrayOf(0xA1, 0xA7,  0x40, 0xA0),
        )
        val codes = Uint16Array(23940)
        var i = 0
        for (arr in ranges) {
            for (b2 in arr[2] .. arr[3]) {
                if (b2 != 0x7f) {
                    for (b1 in arr[0] .. arr[1]) {
                        codes[i++] = (b2 shl 8 or b1).toShort()
                    }
                }
            }
        }
        val decoder = js("new TextDecoder('gbk')")
        val str = decoder.decode(codes) as String
        for (j in str.indices) {
            table[str[j].code] = codes[j]
        }
    }

    /**
     * 获取字符的GBK字节数组
     * @param char Char
     * @return ByteArray
     */
    private fun getCharGBKByteArray(char: Char): ByteArray {
        val code = char.code
        return if (code < 0x80) {
            byteArrayOf(code.toByte())
        } else {
            val gbk = table[code].toInt()
            byteArrayOf((gbk and 0xFF).toByte(), (gbk shr 8).toByte())
        }
    }

    /**
     * 获取字符串的GBK字节数组
     * @param str String
     * @return ByteArray
     */
    fun getStringGBKByteArray(str: String): ByteArray {
        var bytes = byteArrayOf()
        for (char in str.toCharArray()) {
            bytes += getCharGBKByteArray(char)
        }
        return bytes
    }

    /**
     * 将GBK字节数组转换为字符串
     * @param byteArray ByteArray
     * @return String
     */
    fun parseGBKByteArray2String(byteArray: ByteArray): String {
        val decoder = js("new TextDecoder('gbk')")
        return decoder.decode(Uint8Array(byteArray.toTypedArray())) as String
    }
}