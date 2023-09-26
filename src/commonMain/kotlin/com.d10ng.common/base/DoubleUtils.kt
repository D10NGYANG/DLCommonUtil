@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 将 Double 类型转字符串并最大保留指定位数的小数
 * > 该方法不会对字符串进行四舍五入，只是简单的截取；
 * > 如果字符串中没有小数点，则直接返回原字符串；
 * > 如果字符串中小数位数小于指定位数，则直接返回原字符串；
 * @receiver [Double] 浮点数，如 1.2345
 * @param maxDecimalCount [Int] 最大保留小数位数，不能小于0
 * @return [String] 字符串，如 "1.23"
 */
@JsName("toStringByDouble")
fun Double.toString(maxDecimalCount: Int) = toString().keep(maxDecimalCount)