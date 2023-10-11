package com.d10ng.common.base

@JsExport
@JsName("numberToString")
fun Number.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)