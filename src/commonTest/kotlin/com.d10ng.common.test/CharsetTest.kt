package com.d10ng.common.test

import com.d10ng.common.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CharsetTest {

    @Test
    fun testEncodeGBK() {
        val map = mapOf(
            "中文汉字编码测试" to "D6D0CEC4BABAD7D6B1E0C2EBB2E2CAD4",
            "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm" to "51574552545955494F504153444647484A4B4C5A584356424E4D71776572747975696F706173646667686A6B6C7A786376626E6D",
            "1234567890" to "31323334353637383930",
            "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/" to "7E6021402324255E262A28295F2D2B3D7B5B7D5D7C5C3A3B22273C2C3E2E3F2F",
            "～·！@#%……&*（）-=——+【】「」｜、：；\"'《，》。？/" to "A1ABA1A4A3A1402325A1ADA1AD262AA3A8A3A92D3DA1AAA1AA2BA1BEA1BFA1B8A1B9A3FCA1A2A3BAA3BB2227A1B6A3ACA1B7A1A3A3BF2F",
        )
        map.forEach { item ->
            println(item)
            val hex = item.key.encodeGBK().toHexString(false, true)
            assertEquals(hex, item.value)
        }
    }

    @Test
    fun test() {
        //println((0xfc).toByte().toHexString())
        //println((0xff).toByte().toHexString())
//        val hex = "你好123".encodeGBK()
//        println(hex.toHexString(false, true))
//        println(hex.decodeGBK())
    }
}