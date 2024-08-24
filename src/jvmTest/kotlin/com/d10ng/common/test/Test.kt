package com.d10ng.common.test

import com.d10ng.common.base.toUnsignedInt
import com.d10ng.common.transform.decodeGBK
import com.d10ng.common.transform.decodeUnicode
import com.d10ng.common.transform.encodeUnicode
import com.github.promeg.pinyinhelper.Pinyin
import java.io.File
import kotlin.test.Test

class Test {

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
                //println("GBK编码(${gbkCode.toHexString(false, uppercase = true)}) 内容(${gbkText}) | Unicode编码(${uniCode.toHexString(false, uppercase = true)}) 内容(${uniText}) | 差值(${less})")
            }
        }
    }

    @Test
    fun test7() {
        val v = 0x81 .. 0xFE
        val l = (0x40 .. 0xFE).filter { it != 0x7F }

        val sb = StringBuilder()
        val uniCodeList = mutableListOf<Int>()
        val gbkCodeList = mutableListOf<Int>()
        val pyList = mutableListOf<String>()
        var index = 1
        for (i in v) {
            for (j in l) {
                val gbkCode = byteArrayOf(i.toByte(), j.toByte())
                val char = gbkCode.decodeGBK()[0]
                val uniCode = char.code
                val py = Pinyin.toPinyin(char).uppercase()
                if (py.isEmpty()) throw Exception("找不到拼音:$char")
                if (uniCode != 65533) {
                    //println("${index ++} | $uniCode to byteArrayOf(${i}.toByte(), ${j}.toByte()) // $char, py: $py")
                    sb.appendLine("$uniCode to byteArrayOf(${i}.toByte(), ${j}.toByte()), // $char, py: $py")
                    uniCodeList.add(uniCode)
                    gbkCodeList.add(gbkCode.toUnsignedInt())
                    pyList.add(py)
                }
            }
        }
        // 写入文件
        val file = File("/Users/d10ng/Downloads/gbk_unicode.txt")
        //file.writeText(sb.toString())
        // 写入文件
        val file2 = File("/Users/d10ng/Downloads/uniCode.txt")
        //file2.writeText(uniCodeList.joinToString(","))
        // 写入文件
        val file3 = File("/Users/d10ng/Downloads/gbkCode.txt")
        //file3.writeText(gbkCodeList.joinToString(","))
    }

    @Test
    fun testPY() {
        val v = 0x81 .. 0xFE
        val l = (0x40 .. 0xFE).filter { it != 0x7F }
        var index = 0
        val map = mutableMapOf<String, List<Char>>()
        for (i in v) {
            for (j in l) {
                val gbkCode = byteArrayOf(i.toByte(), j.toByte())
                val char = gbkCode.decodeGBK()[0]
                val uniCode = char.code
                if (uniCode != 65533) {
                    val py = Pinyin.toPinyin(char).uppercase()
                    if (py.isEmpty() || py[0] == char) {}
                    else {
                        //println("${index ++} -> $char -> $uniCode -> $py")
                        var ls = map.getOrPut(py) { mutableListOf() }
                        ls = ls + char
                        map[py] = ls
                    }
                }
            }
        }
        // map按照List<Char>的长度排序
        val li = map.toSortedMap().map {
            "${it.key}:${it.value.joinToString("")}"
        }.joinToString(",")
        println(li)
    }
}