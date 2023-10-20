package com.d10ng.common.test

import com.d10ng.common.transform.toPinYin
import kotlin.test.Test
import kotlin.test.assertEquals

class PinyinTest {

    @Test
    fun test() {
        assertEquals("中文拼音测试".toPinYin(), "ZHONGWENPINYINCESHI")
        assertEquals("中文".toPinYin(","), "ZHONG,WEN")
        assertEquals("abc".toPinYin(), "ABC")
        assertEquals("abc".toPinYin(","), "A,B,C")
        assertEquals("ABC".toPinYin(), "ABC")
        assertEquals("123".toPinYin(), "123")
        assertEquals("!@#".toPinYin(), "!@#")
        assertEquals("中123".toPinYin(), "ZHONG123")
        assertEquals("中Ab123".toPinYin(), "ZHONGAB123")
        assertEquals("中Ab123".toPinYin(" "), "ZHONG A B 1 2 3")
    }
}