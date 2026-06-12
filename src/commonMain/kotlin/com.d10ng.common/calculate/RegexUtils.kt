package com.d10ng.common.calculate

import kotlin.js.JsExport

/** 匹配一个或多个 ASCII 数字。 */
val numberRegex = "[0-9]+".toRegex()

/** 匹配一个或多个 ASCII 英文字母。 */
val letterRegex = "[a-zA-Z]+".toRegex()

/** 匹配一个或多个 ASCII 英文字母或数字。 */
val numEnRegex = "[a-zA-Z0-9]+".toRegex()

/** 匹配一个或多个十六进制字符。 */
val codeRegex = "[a-fA-F0-9]+".toRegex()

/** 匹配一个或多个基本 CJK 统一汉字。 */
val chineseRegex = "[\u4E00-\u9FA5]+".toRegex()

/** 匹配一个或多个 ASCII 英文字母、数字或基本 CJK 统一汉字。 */
val chEnNumRegex = "[a-zA-Z0-9\u4E00-\u9FA5]+".toRegex()

/** 匹配项目兼容范围内的汉字、全角符号及其他东亚字符。 */
val chSymbolRegex = "[\u00A4\u00A7-\u00A8\u00B0-\u00B1\u00B7\u00D7\u00E0-\u00E1\u00E8-\u00EA\u00EC-\u00ED\u00F2-\u00F3\u00F7\u00F9-\u00FA\u00FC\u0101\u0113\u011B\u012B\u0144\u0148\u014D\u016B\u01CE\u01D0\u01D2\u01D4\u01D6\u01D8\u01DA\u01DC\u0251\u0261\u02C7\u02C9\u0391-\u03A1\u03A3-\u03A9\u03B1-\u03C1\u03C3-\u03C9\u0401\u0410-\u044F\u0451\u2014\u2016\u2018-\u2019\u201C-\u201D\u2026\u2030\u2032-\u2033\u203B\u20AC\u2103\u2116\u2160-\u216B\u2170-\u2179\u2190-\u2193\u2208\u220F\u2211\u221A\u221D-\u221E\u2220\u2225\u2227-\u222B\u222E\u2234-\u2237\u223D\u2248\u224C\u2260-\u2261\u2264-\u2265\u226E-\u226F\u2299\u22A5\u2312\u2460-\u2469\u2474-\u249B\u2500-\u254B\u25A0-\u25A1\u25B2-\u25B3\u25C6-\u25C7\u25CB\u25CE-\u25CF\u2605-\u2606\u2640\u2642\u3000-\u3003\u3005\u3008-\u3011\u3013-\u3017\u3041-\u3093\u30A1-\u30F6\u3105-\u3129\u3220-\u3229\uE766-\uE76B\uE76D-\uE7BB\uE7C7-\uE7E1\uE7FE-\uE80F\uFE31\uFE33-\uFE44\uFF01-\uFF5E\uFFE0-\uFFE1\uFFE3\uFFE5\uff00-\uffff\u4E00-\u9FA5]+".toRegex()

/** 匹配 18 位中国大陆居民身份证号码格式，不校验日期真实性或校验码。 */
val idCardRegex =
    "^[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[0-9Xx]$".toRegex()

/** 匹配以 `1` 开头的 11 位中国大陆手机号码格式。 */
val mobileNumberRegex = "^1[1-9]\\d{9}$".toRegex()

/** 匹配项目兼容规则下的邮箱地址格式。 */
val emailRegex =
    "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(?:\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\$".toRegex()

/** 匹配 `#RGB` 或 `#RRGGBB` 格式的十六进制颜色。 */
val colorRegex = "^#(?:[0-9a-fA-F]{6}|[0-9a-fA-F]{3})$".toRegex()

/**
 * 保留动态正则表达式匹配到的所有非重叠内容。
 *
 * 每次调用都会编译 [regStr]。重复使用同一表达式时，应预先构造 [Regex] 并调用 [keep]。
 * 正则表达式应来自可信来源，避免使用可能产生灾难性回溯的模式处理超长输入。
 *
 * @receiver 待处理的字符串
 * @param regStr 正则表达式字符串
 * @return 按匹配顺序拼接后的字符串
 * @throws IllegalArgumentException 当 [regStr] 不是有效正则表达式时
 */
@JsExport
fun String.keepByRegexStr(regStr: String): String = keep(compileRegex(regStr))

/**
 * 保留正则表达式匹配到的所有非重叠内容。
 *
 * @receiver 待处理的字符串
 * @param reg 用于查找保留内容的预编译正则表达式
 * @return 按匹配顺序拼接后的字符串
 */
fun String.keep(reg: Regex): String = buildString(length) {
    reg.findAll(this@keep).forEach { append(it.value) }
}

/**
 * 删除动态正则表达式匹配到的所有内容。
 *
 * @receiver 待处理的字符串
 * @param regStr 正则表达式字符串
 * @return 删除匹配内容后的字符串
 * @throws IllegalArgumentException 当 [regStr] 不是有效正则表达式时
 */
@Deprecated(
    message = "Use Kotlin's Regex.replace(input, replacement) instead.",
    replaceWith = ReplaceWith("Regex(regStr).replace(this, \"\")"),
)
@JsExport
fun String.filterByRegexStr(regStr: String): String = compileRegex(regStr).replace(this, "")

/**
 * 删除正则表达式匹配到的所有内容。
 *
 * @receiver 待处理的字符串
 * @param reg 用于查找待删除内容的正则表达式
 * @return 删除匹配内容后的字符串
 */
@Deprecated(
    message = "Use Kotlin's Regex.replace(input, replacement) instead.",
    replaceWith = ReplaceWith("reg.replace(this, \"\")"),
)
fun String.filter(reg: Regex): String = reg.replace(this, "")

/**
 * 判断字符串是否符合 18 位中国大陆居民身份证号码的外观格式。
 *
 * 此函数不验证真实日历日期、行政区划代码或身份证校验码，不应单独用于身份认证。
 *
 * @receiver 待检查的字符串
 * @return 符合格式时返回 `true`
 */
@JsExport
fun String.isIdCard(): Boolean = idCardRegex.matches(this)

/**
 * 判断字符串是否符合中国大陆 11 位手机号码格式。
 *
 * @receiver 待检查的字符串
 * @return 符合格式时返回 `true`
 */
@JsExport
fun String.isMobileNumber(): Boolean = mobileNumberRegex.matches(this)

/**
 * 判断字符串是否符合项目兼容的邮箱地址格式。
 *
 * 该规则不是完整的 RFC 邮箱验证器，不应代替邮件确认流程。
 *
 * @receiver 待检查的字符串
 * @return 符合格式时返回 `true`
 */
@JsExport
fun String.isEmail(): Boolean = emailRegex.matches(this)

/**
 * 判断字符串是否仅包含 ASCII 英文字母、数字或基本 CJK 统一汉字。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyChEnNum(): Boolean = chEnNumRegex.matches(this)

/**
 * 判断字符串是否仅包含 ASCII 英文字母或数字。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyEnNum(): Boolean = numEnRegex.matches(this)

/**
 * 判断字符串是否仅包含 `U+4E00..U+9FA5` 范围内的汉字。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyChinese(): Boolean = chineseRegex.matches(this)

/**
 * 判断字符串是否仅包含 ASCII 英文字母。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyLetter(): Boolean = letterRegex.matches(this)

/**
 * 判断字符串是否仅包含 ASCII 数字。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyNumber(): Boolean = numberRegex.matches(this)

/**
 * 判断字符串是否仅包含十六进制字符。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyCode(): Boolean = codeRegex.matches(this)

/**
 * 判断字符串是否仅包含项目兼容范围内的汉字、全角符号及其他东亚字符。
 *
 * @receiver 待检查的字符串
 * @return 字符串非空且全部符合规则时返回 `true`
 */
@JsExport
fun String.isOnlyChSymbol(): Boolean = chSymbolRegex.matches(this)

/**
 * 判断字符串是否为 `#RGB` 或 `#RRGGBB` 格式的十六进制颜色。
 *
 * @receiver 待检查的字符串
 * @return 符合颜色格式时返回 `true`
 */
@JsExport
fun String.isColor(): Boolean = colorRegex.matches(this)

private fun compileRegex(pattern: String): Regex {
    return try {
        Regex(pattern)
    } catch (cause: Throwable) {
        throw IllegalArgumentException("Invalid regular expression: $pattern", cause)
    }
}
