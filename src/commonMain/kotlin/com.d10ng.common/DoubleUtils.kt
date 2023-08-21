@file:JsExport
package com.d10ng.common

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 Double 类型转字符串并最大保留指定位数的小数
 * @receiver [Double]
 * @param maxDecimalCount [Int] 最大保留小数位数
 * @return [String]
 */
@JsName("toStringByDouble")
fun Double.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)