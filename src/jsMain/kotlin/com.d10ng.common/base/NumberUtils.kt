package com.d10ng.common.base

@JsExport
@JsName("numberToString")
fun Number.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)

@JsExport
@JsName("numberToByteArray")
fun Number.toByteArray(size: Int = 0) = when (this) {
    is Int -> toByteArray(size)
    is Long -> toByteArray(size)
    else -> throw IllegalArgumentException("Number must be Int or Long")
}

@JsExport
@Suppress("NON_EXPORTABLE_TYPE")
fun Number.asLong() = this.toLong()