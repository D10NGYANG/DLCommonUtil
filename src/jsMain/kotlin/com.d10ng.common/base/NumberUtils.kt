package com.d10ng.common.base

@JsExport
@JsName("numberToString")
fun Number.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)

@JsExport
@JsName("numberToByteArray")
fun Number.toByteArray(size: Int? = null): ByteArray {
    if (this is Long) {
        require(this in Long.MIN_VALUE..Long.MAX_VALUE) { "BigInt must fit in a signed 64-bit Long" }
        return longToByteArray(this, size)
    }
    val value = toDouble()
    require(value.isFinite() && value % 1.0 == 0.0 && kotlin.math.abs(value) <= 9_007_199_254_740_991.0) {
        "Number must be a safe integer; use BigInt for larger values"
    }
    // JS erases Short/Int/Double types to number, so dispatch by value and requested width.
    return if (value in Int.MIN_VALUE.toDouble()..Int.MAX_VALUE.toDouble() && (size == null || size <= 4)) {
        intToByteArray(value.toInt(), size)
    } else {
        longToByteArray(value.toLong(), size)
    }
}

@JsExport
fun Number.asLong() = this.toLong()