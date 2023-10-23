package com.d10ng.common.calculate

import kotlin.js.JsExport

// 数字正则表达式
val numberRegex = "[0-9]+".toRegex()
// 字母正则表达式
val letterRegex = "[a-zA-Z]+".toRegex()
// 数字英文正则表达式
val numEnRegex = "[a-zA-Z0-9]+".toRegex()
// 代码正则表达式
val codeRegex = "[a-fA-F0-9]+".toRegex()
// 中文正则表达式
val chineseRegex = "[\u4E00-\u9FA5]+".toRegex()
// 中文数字英文正则表达式
val chEnNumRegex = "[a-zA-Z0-9\u4E00-\u9FA5]+".toRegex()
// 汉字和全角符号正则表达式
val chSymbolRegex = "[\u00A4\u00A7-\u00A8\u00B0-\u00B1\u00B7\u00D7\u00E0-\u00E1\u00E8-\u00EA\u00EC-\u00ED\u00F2-\u00F3\u00F7\u00F9-\u00FA\u00FC\u0101\u0113\u011B\u012B\u0144\u0148\u014D\u016B\u01CE\u01D0\u01D2\u01D4\u01D6\u01D8\u01DA\u01DC\u0251\u0261\u02C7\u02C9\u0391-\u03A1\u03A3-\u03A9\u03B1-\u03C1\u03C3-\u03C9\u0401\u0410-\u044F\u0451\u2014\u2016\u2018-\u2019\u201C-\u201D\u2026\u2030\u2032-\u2033\u203B\u20AC\u2103\u2116\u2160-\u216B\u2170-\u2179\u2190-\u2193\u2208\u220F\u2211\u221A\u221D-\u221E\u2220\u2225\u2227-\u222B\u222E\u2234-\u2237\u223D\u2248\u224C\u2260-\u2261\u2264-\u2265\u226E-\u226F\u2299\u22A5\u2312\u2460-\u2469\u2474-\u249B\u2500-\u254B\u25A0-\u25A1\u25B2-\u25B3\u25C6-\u25C7\u25CB\u25CE-\u25CF\u2605-\u2606\u2640\u2642\u3000-\u3003\u3005\u3008-\u3011\u3013-\u3017\u3041-\u3093\u30A1-\u30F6\u3105-\u3129\u3220-\u3229\uE766-\uE76B\uE76D-\uE7BB\uE7C7-\uE7E1\uE7FE-\uE80F\uFE31\uFE33-\uFE44\uFF01-\uFF5E\uFFE0-\uFFE1\uFFE3\uFFE5\uff00-\uffff\u4E00-\u9FA5]+".toRegex()
// 身份证正则表达式
val idCardRegex = "^[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[0-9Xx]$".toRegex()
// 国内手机号码正则表达式
val mobileNumberRegex = "^1[1-9]\\d{9}$".toRegex()
// 邮箱正则表达式
val emailRegex = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(?:\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\$".toRegex()
// 颜色字符串正则表达式
val colorRegex = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$".toRegex()

/**
 * 利用正则表达式保留字符串
 * @receiver [String] 字符串
 * @param regStr [String] 正则表达式字符串
 * @return [String] 保留后的字符串
 */
@JsExport
fun String.keepByRegexStr(regStr: String) = keep(regStr.toRegex())

/**
 * 利用正则表达式保留字符串
 * @receiver [String]
 * @param reg [Regex]
 * @return [String]
 */
fun String.keep(reg: Regex) = reg.findAll(this).map { it.value }.joinToString("")

/**
 * 利用正则表达式过滤字符串
 * @receiver [String] 字符串
 * @param regStr [String] 正则表达式字符串
 * @return [String] 过滤后的字符串
 */
@JsExport
fun String.filterByRegexStr(regStr: String) = filter(regStr.toRegex())

/**
 * 利用正则表达式过滤字符串
 * @receiver [String]
 * @param reg [Regex]
 * @return [String]
 */
fun String.filter(reg: Regex) = reg.replace(this, "")

/**
 * 判断字符串是否符合身份证规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isIdCard() = idCardRegex.matches(this)

/**
 * 判断字符串是否符合国内手机号码规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isMobileNumber() = mobileNumberRegex.matches(this)

/**
 * 判断字符串是否符合邮箱规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isEmail() = emailRegex.matches(this)

/**
 * 判断字符串是否只有中英文数字，不包含特殊字符
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyChEnNum() = chEnNumRegex.matches(this)

/**
 * 判断字符串是否只有英文数字，不包含特殊字符
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyEnNum() = numEnRegex.matches(this)

/**
 * 判断字符串是否只有汉字
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyChinese() = chineseRegex.matches(this)

/**
 * 判断字符串是否只有字母
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyLetter() = letterRegex.matches(this)

/**
 * 判断字符串是否只有数字
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyNumber() = numberRegex.matches(this)

/**
 * 判断字符串是否只有代码
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyCode() = codeRegex.matches(this)

/**
 * 判断字符串是否只有汉字和全角符号
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isOnlyChSymbol() = chSymbolRegex.matches(this)

/**
 * 判断字符串是否为颜色字符串
 *
 * @receiver [String]
 * @return [Boolean]
 */
@JsExport
fun String.isColor() = colorRegex.matches(this)