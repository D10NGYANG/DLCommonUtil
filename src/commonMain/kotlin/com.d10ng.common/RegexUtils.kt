package com.d10ng.common

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
val chSymbolRegex = "[\u3000-\u301e\u3021-\u303f\u3040-\u309f\u30a0-\u30ff\uff00-\uffef\u4E00-\u9FA5]+".toRegex()

/**
 * 利用正则表达式保留字符串
 * @receiver [String]
 * @param reg [Regex]
 * @return [String]
 */
fun String.keep(reg: Regex): String {
    val ls = reg.findAll(this).map { it.value }
    val text = StringBuilder("")
    ls.forEach { text.append(it) }
    return text.toString()
}

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
fun String.isIdCard(): Boolean = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)".toRegex().matches(this)

/**
 * 判断字符串是否符合国内手机号码规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isMobileNumber(): Boolean = "^1[1-9]\\d{9}$".toRegex().matches(this)

/**
 * 判断字符串是否符合邮箱规则
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isEmail(): Boolean = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex().matches(this)

/**
 * 判断字符串是否只有中英文数字，不包含特殊字符
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyChEnNum(): Boolean = chEnNumRegex.matches(this)

/**
 * 判断字符串是否只有英文数字，不包含特殊字符
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyEnNum(): Boolean = numEnRegex.matches(this)

/**
 * 判断字符串是否只有汉字
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyChinese(): Boolean = chineseRegex.matches(this)

/**
 * 判断字符串是否只有字母
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyLetter(): Boolean = letterRegex.matches(this)

/**
 * 判断字符串是否只有数字
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyNumber(): Boolean = numberRegex.matches(this)

/**
 * 判断字符串是否只有代码
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyCode(): Boolean = codeRegex.matches(this)

/**
 * 判断字符串是否只有汉字和全角符号
 *
 * @receiver [String]
 * @return [Boolean]
 */
fun String.isOnlyChSymbol(): Boolean = chSymbolRegex.matches(this)