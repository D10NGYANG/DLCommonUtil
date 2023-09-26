@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport

/**
 * 将 Double/Float 类型的字符串最大保留指定位数的小数
 * > 该方法不会对字符串进行四舍五入，只是简单的截取；
 * > 如果字符串中没有小数点，则直接返回原字符串；
 * > 如果字符串中小数位数小于指定位数，则直接返回原字符串；
 * @receiver String
 * @param maxDecimalCount Int
 * @return String
 */
internal fun String.keep(maxDecimalCount: Int): String {
    if (maxDecimalCount < 0) throw IllegalArgumentException("maxDecimalCount must be greater than or equal to 0")
    val pointIndex = this.indexOf(".")
    return if (pointIndex == -1) this
    else if (maxDecimalCount == 0) substring(0, pointIndex)
    else {
        val decimalCount = this.length - pointIndex - 1
        if (decimalCount > maxDecimalCount) substring(0, pointIndex + maxDecimalCount + 1) else this
    }
}

/**
 * 将 Float 类型转字符串并最大保留指定位数的小数
 * > 该方法不会对字符串进行四舍五入，只是简单的截取；
 * > 如果字符串中没有小数点，则直接返回原字符串；
 * > 如果字符串中小数位数小于指定位数，则直接返回原字符串；
 * @receiver [Float] 浮点数，如 1.2345f
 * @param maxDecimalCount [Int] 最大保留小数位数，不能小于0
 * @return [String] 字符串，如 "1.23"
 */
fun Float.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)