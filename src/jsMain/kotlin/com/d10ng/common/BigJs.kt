package com.d10ng.common

@JsModule("big.js")
@JsNonModule
external class Big {
    constructor(string: String)
    constructor(double: Double)
    constructor(big: Big)

    fun minus(other: Big): Big

    fun toNumber(): Double
}

