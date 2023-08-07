import com.d10ng.common.Big
import kotlin.test.Test

class Test {

    @Test
    fun test() {
        println(10.01 - 10.0)
        val a = Big("10.01")
        val b = Big("10.0")
        println(a.minus(b).toNumber())
    }
}