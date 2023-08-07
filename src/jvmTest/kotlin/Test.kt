import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

class Test {

    @Test
    fun test() {
        println(10.01 - 10.0)
        val a = BigDecimal.valueOf(10.01)
        val b = BigDecimal.valueOf(10.0)
        println(a.divide(b).toDouble())
        println(BigDecimal("10.05").setScale(1, RoundingMode.UP).toDouble())
    }
}