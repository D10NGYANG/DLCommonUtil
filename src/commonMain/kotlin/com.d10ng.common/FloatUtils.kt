@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport

/**
 * 将 Double/Float 类型的字符串最大保留指定位数的小数
 * @receiver String
 * @param maxDecimalCount Int
 * @return String
 */
internal fun String.keep(maxDecimalCount: Int): String {
    val pointIndex = this.indexOf(".")
    return if (pointIndex == -1) this
    else {
        val decimalCount = this.length - pointIndex - 1
        if (decimalCount > maxDecimalCount) substring(0, pointIndex + maxDecimalCount + 1) else this
    }
}

/**
 * 将 Float 类型转字符串并最大保留指定位数的小数
 * @receiver [Float]
 * @param maxDecimalCount [Int] 最大保留小数位数
 * @return [String]
 */
fun Float.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)