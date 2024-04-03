import com.d10ng.common.base.*
import kotlin.test.Test

class Test {

    @Test
    fun test1() {
        val value = 0xFFFF

        println("int=$value")

        val hex = value.toString(radix = 16)

        println("hex=$hex")
    }
}