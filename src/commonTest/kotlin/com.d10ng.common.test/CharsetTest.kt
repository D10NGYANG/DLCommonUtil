package com.d10ng.common.test

import com.d10ng.common.base.toByteArrayFromHex
import com.d10ng.common.base.toHexString
import com.d10ng.common.transform.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CharsetTest {

    @Test
    fun testEncodeGBK() {
        val map = mapOf(
            "" to "",
            "中文汉字编码测试" to "D6D0CEC4BABAD7D6B1E0C2EBB2E2CAD4",
            "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm" to "51574552545955494F504153444647484A4B4C5A584356424E4D71776572747975696F706173646667686A6B6C7A786376626E6D",
            "1234567890" to "31323334353637383930",
            "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/" to "7E6021402324255E262A28295F2D2B3D7B5B7D5D7C5C3A3B22273C2C3E2E3F2F",
            "～·！@#%……&*（）-=——+【】「」｜、：；\"'《，》。？/" to "A1ABA1A4A3A1402325A1ADA1AD262AA3A8A3A92D3DA1AAA1AA2BA1BEA1BFA1B8A1B9A3FCA1A2A3BAA3BB2227A1B6A3ACA1B7A1A3A3BF2F",
        )
        map.forEach { item ->
            val hex = item.key.encodeGBK().toHexString()
            assertEquals(hex, item.value)
            val str = item.value.toByteArrayFromHex().decodeGBK()
            assertEquals(str, item.key)
        }
    }

    @Test
    fun testEncodeUTF8() {
        val map = mapOf(
            "" to "",
            "中文汉字编码测试" to "E4B8ADE69687E6B189E5AD97E7BC96E7A081E6B58BE8AF95",
            "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm" to "51574552545955494F504153444647484A4B4C5A584356424E4D71776572747975696F706173646667686A6B6C7A786376626E6D",
            "1234567890" to "31323334353637383930",
            "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/" to "7E6021402324255E262A28295F2D2B3D7B5B7D5D7C5C3A3B22273C2C3E2E3F2F",
            "～·！@#%……&*（）-=——+【】「」｜、：；\"'《，》。？/" to "EFBD9EC2B7EFBC81402325E280A6E280A6262AEFBC88EFBC892D3DE28094E280942BE38090E38091E3808CE3808DEFBD9CE38081EFBC9AEFBC9B2227E3808AEFBC8CE3808BE38082EFBC9F2F",
        )
        map.forEach { item ->
            val hex = item.key.encodeUTF8().toHexString()
            assertEquals(hex, item.value)
            val str = item.value.toByteArrayFromHex().decodeUTF8()
            assertEquals(str, item.key)
        }
    }

    @Test
    fun testEncodeUnicode() {
        val map = mapOf(
            "" to "",
            "中文汉字编码测试" to "4E2D65876C495B577F1678016D4B8BD5",
            "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm" to "00510057004500520054005900550049004F0050004100530044004600470048004A004B004C005A0058004300560042004E004D00710077006500720074007900750069006F0070006100730064006600670068006A006B006C007A0078006300760062006E006D",
            "1234567890" to "0031003200330034003500360037003800390030",
            "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/" to "007E006000210040002300240025005E0026002A00280029005F002D002B003D007B005B007D005D007C005C003A003B00220027003C002C003E002E003F002F",
            "～·！@#%……&*（）-=——+【】「」｜、：；\"'《，》。？/" to "FF5E00B7FF01004000230025202620260026002AFF08FF09002D003D20142014002B30103011300C300DFF5C3001FF1AFF1B00220027300AFF0C300B3002FF1F002F",
        )
        map.forEach { item ->
            val hex = item.key.encodeUnicode().toHexString()
            assertEquals(hex, item.value)
            val str = item.value.toByteArrayFromHex().decodeUnicode()
            assertEquals(str, item.key)
        }
    }

    @Test
    fun testEncodeASCII() {
        val map = mapOf(
            "" to "",
            "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm" to "51574552545955494F504153444647484A4B4C5A584356424E4D71776572747975696F706173646667686A6B6C7A786376626E6D",
            "1234567890" to "31323334353637383930",
            "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/" to "7E6021402324255E262A28295F2D2B3D7B5B7D5D7C5C3A3B22273C2C3E2E3F2F"
        )
        map.forEach { item ->
            val hex = item.key.encodeASCII().toHexString()
            assertEquals(hex, item.value)
            val str = item.value.toByteArrayFromHex().decodeASCII()
            assertEquals(str, item.key)
        }
    }
}