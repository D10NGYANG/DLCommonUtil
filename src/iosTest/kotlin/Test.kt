import platform.Foundation.NSDecimalNumber
import kotlin.test.Test

class Test {

    @Test
    fun test() {
        println(10.01 - 10.0)
        NSDecimalNumber()
        val a = NSDecimalNumber("10.01")
        val b = NSDecimalNumber("10.0")
        println(a.decimalNumberByDividingBy(b).doubleValue)
    }
}