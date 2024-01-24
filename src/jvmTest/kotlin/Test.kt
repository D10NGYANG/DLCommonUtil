import com.d10ng.common.base.toByteArray
import com.d10ng.common.base.toByteArrayFromHex
import com.d10ng.common.base.toHexString
import com.d10ng.common.base.toUnsignedInt
import com.d10ng.common.calculate.chSymbolRegex
import com.d10ng.common.calculate.getChecksum
import com.d10ng.common.transform.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.*
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
                val uniText = uniCode.decodeUnicode()
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
        println("CCTXA,你".encodeGBK().toHexString())
        println("CCTXA,你".encodeGBK().getChecksum().toHexString())
    }

    @Test
    fun test2() {
        val v = 0x20 .. 0x7E
        val sb = StringBuilder("str")
        for (i in v) {
            val asciiCode = byteArrayOf(i.toByte())
            val asciiText = asciiCode.decodeASCII()
            val uniCode = asciiText.encodeUnicode()
            val uniText = uniCode.decodeUnicode()
            val gbkCode = (uniCode.toUnsignedInt() + 0xFEE0).toByteArray().decodeUnicode().encodeGBK()
            val gbkText = gbkCode.decodeGBK()
            if (gbkCode.size == 2) {
                println("ASCII编码(${asciiCode.toHexString()}) 内容(${asciiText}) | Unicode编码(${uniCode.toHexString()}) 内容(${uniText}) | GBK编码(${gbkCode.toHexString()}) 内容(${gbkText})")
                sb.appendLine(".replace(\"${asciiText}\", \"${gbkText}\")")
            }
        }
        println(sb.toString())
    }

    @Test
    fun test3() {
        chSymbolRegex.findAll("测试123，阿说过的哈是觉得尬，阿嘉豪看帅哥带回家啊点").map { it.value }.joinToString("").let(::println)
    }

    @Test
    fun test4() {
        val v = 0x0080 .. 0x00ff
        val sb = StringBuilder("str")
        for (i in v) {
            val asciiCode = byteArrayOf(i.toByte())
            val asciiText = asciiCode.decodeASCII()
            val uniCode = asciiText.encodeUnicode()
            val uniText = uniCode.decodeUnicode()
            val gbkCode = asciiText.encodeGBK()
            val gbkText = gbkCode.decodeGBK()
            if (gbkCode.size == 2) {
                println("ASCII编码(${asciiCode.toHexString(false, uppercase = true)}) 内容(${asciiText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText})")
                sb.appendLine(".replace(\"${asciiText}\", \"${gbkText}\")")
            }
        }
        println(sb.toString())
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
                val uniText = uniCode.decodeUnicode()
                val less = gbkCode.toUnsignedInt() - uniCode.toUnsignedInt()
                println("GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | 差值(${less})")
            }
        }
    }

    @Test
    fun test6() {
        val str = "中文测试,https://baidu.com,hello?"
        println(str.toFullWidth())
    }

    fun String.toFullWidth(): String {
        val sb = StringBuilder()
        for (c in this) {
            if (c.code in 33..126) {
                sb.append((c.code + 65248).toChar())
            } else if (c.code == 32) {
                sb.append("　")
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    @Test
    fun test1111(): Unit = runBlocking {
        CoroutineScope(Dispatchers.IO).launch {
            val s = 8
            for (i in 0x0000 .. 0xFFFF step s) {
                val data = ByteArray(s + 2)
                for (j in i until i + s) {
                    data[j - i] = j.toByte()
                }
                data[s] = 0x0D
                data[s + 1] = 0x0A
                TestBuffer.write(data)
                println("写入完成,${data.toHexString()}")
                delay(1)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val buffer = try {
                    TestBuffer.read()
                } catch (e: Exception) {
                    e.printStackTrace()
                    println("读取完成")
                    break
                }
                if (buffer.isNotEmpty()) println(buffer.toHexString())
            }
        }
        delay(1000)
    }
}

object TestBuffer {

    val buf = Buffer()

    private val mutex = Mutex()

    suspend fun write(data: ByteArray) {
        mutex.withLock {
            buf.write(data)
        }
    }

    suspend fun read(): ByteArray {
        val data = Buffer()
        var last = 0x00.toByte()
        while (true) {
            val b = mutex.withLock {
                if (buf.size == 0L) return@withLock null
                buf.readByte()
            }
            if (b == null) {
                delay(1)
                continue
            }
            data.writeByte(b)
            if (b == 0x0A.toByte() && last == 0x0D.toByte()) break
            last = b
        }
        return data.readByteArray()
    }
}