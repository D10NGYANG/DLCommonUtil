package com.d10ng.common.base

@JsExport
@JsName("numberToString")
fun Number.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)

@JsExport
@JsName("numberToByteArray")
fun Number.toByteArray(size: Int? = null): ByteArray = when (this) {
    is Short -> shortToByteArray(this, size)
    is Int -> intToByteArray(this, size)
    is Long -> longToByteArray(this, size)
    else -> throw IllegalArgumentException("Number must be Int or Long")
}

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
fun Number.asLong() = this.toLong()