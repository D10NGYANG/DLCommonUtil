import com.d10ng.common.base.toByteArrayFromHex
import com.d10ng.common.base.toHexString
import com.d10ng.common.base.toUnsignedInt
import com.d10ng.common.base.toUnsignedLong
import com.ditchoom.buffer.JsBuffer
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.wrap
import kotlin.test.Test

class Test {

    @Test
    fun test1() {
        val str = "0001A960DBD8A500006500006400010A003132333435363738"
        val hex = str.toByteArrayFromHex()
        val buf = PlatformBuffer.wrap(hex)
        val messageId = buf.readByteArray(2).toUnsignedInt()
        val cmd = buf.readByte().toHexString()
        println("messageId: $messageId, cmd: $cmd")
        val data = buf.readByteArray(buf.remaining()).copyOf()
        println("data=${data.toHexString()}")
        val buf1 = PlatformBuffer.wrap(data)
        println((buf1 as JsBuffer).buffer.buffer.byteLength)
        println("buf1.readByteArray(4)=${buf1.readByteArray(4).toHexString()}")
        //val timestamp = buf1.readByteArray(4).toUnsignedLong() * 1000L
        //println("timestamp: $timestamp")
    }
}