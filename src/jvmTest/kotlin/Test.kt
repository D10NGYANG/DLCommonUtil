import com.d10ng.common.*
import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toByteArrayFromHex
import com.d10ng.common.base.toHexString
import com.d10ng.common.base.toUnsignedInt
import kotlin.test.Test

class Test {

    data class CodePoint(val gbkCode: String, val gbkText: String, val uniCode: String, val uniText: String, var less: Int = 0)

    @Test
    fun test() {
        val v = 0xA1 .. 0xA9
        val h = 0xA1 .. 0xFE
        val toHex: (ByteArray) -> String = { it.toHexString(false, uppercase = true) }
        var l = mutableListOf<CodePoint>()
        for (i in v) {
            for (j in h) {
                val gbkCode = byteArrayOf(i.toByte(), j.toByte())
                val gbkText = gbkCode.decodeGBK()
                val uniCode = gbkText.encodeUnicode()
                val uniText = uniCode.decodeUnicodeByByteArray()
                l.add(CodePoint(toHex(gbkCode), gbkText, toHex(uniCode), uniText))
            }
        }

        l = l.sortedBy { it.uniCode }.toMutableList()
        l.forEachIndexed { idx, p ->
            p.less = if (idx == 0) 0 else p.uniCode.toByteArrayFromHex().toUnsignedInt() - l[idx - 1].uniCode.toByteArrayFromHex().toUnsignedInt()
        }
        l.forEach { p ->
            println("GBK编码(${p.gbkCode}) 内容(${p.gbkText}) | Unicode编码(${p.uniCode}) 内容(${p.uniText}) | 差值(${p.less})")
        }

        val sb = StringBuilder()
        l.forEachIndexed { idx, p ->
            if (p.less != 1) {
                sb.append("\\u${p.uniCode}")
            } else if (idx != l.size -1 && l[idx + 1].less != 1) {
                sb.append("-\\u${p.uniCode}")
            } else if (idx == l.size - 1) {
                sb.append("\\u${p.uniCode}")
            }
        }
        println(sb.toString())
    }

    @Test
    fun test1() {
        println("".encodeGBK().toHexString(false, true))
    }

    @Test
    fun test2() {
        val v = 0x20 .. 0x7E
        for (i in v) {
            val asciiCode = byteArrayOf(i.toByte())
            val asciiText = asciiCode.decodeASCII()
            val uniCode = asciiText.encodeUnicode()
            val uniText = uniCode.decodeUnicodeByByteArray()
            val gbkCode = (uniCode.toUnsignedInt() + 0xFEE0).toByteArray().decodeUnicodeByByteArray().encodeGBK()
            val gbkText = gbkCode.decodeGBK()
            println("ASCII编码(${asciiCode.toHexString(false, uppercase = true)}) 内容(${asciiText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText})")
        }
    }

    @Test
    fun test3() {
        chSymbolRegex.findAll("测试123，阿说过的哈是觉得尬，阿嘉豪看帅哥带回家啊点").map { it.value }.joinToString("").let(::println)
    }

    @Test
    fun test4() {
        val v = 0x0080 .. 0x00ff
        for (i in v) {
            val asciiCode = byteArrayOf(i.toByte())
            val asciiText = asciiCode.decodeASCII()
            val uniCode = asciiText.encodeUnicode()
            val uniText = uniCode.decodeUnicodeByByteArray()
            val gbkCode = asciiText.encodeGBK()
            val gbkText = gbkCode.decodeGBK()
            if (gbkCode.size == 2) {
                println("ASCII编码(${asciiCode.toHexString(false, uppercase = true)}) 内容(${asciiText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText})")
            }
        }
    }

    @Test
    fun test5() {
        val h = 0x81 .. 0xFE
        val l = (0x40 .. 0xFE).filter { it != 0x7F }

        for (i in h) {
            for (j in l) {
                val gbkCode = byteArrayOf(i.toByte(), j.toByte())
                val gbkText = gbkCode.decodeGBK()
                val uniCode = gbkText.encodeUnicode()
                val uniText = uniCode.decodeUnicodeByByteArray()
                val less = gbkCode.toUnsignedInt() - uniCode.toUnsignedInt()
                println("GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | 差值(${less})")
            }
        }
    }
}