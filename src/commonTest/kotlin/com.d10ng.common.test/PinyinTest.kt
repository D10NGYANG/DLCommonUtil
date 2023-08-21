package com.d10ng.common.test

import com.d10ng.common.toPinYin
import kotlin.test.Test
import kotlin.test.assertEquals

class PinyinTest {

    @Test
    fun test() {
        val content = "中文拼音测试"
        val pinyin = content.toPinYin()
        assertEquals(pinyin, "ZHONGWENPINYINCESHI")
    }
}