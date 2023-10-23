package com.d10ng.common.transform

import kotlin.js.JsExport

/**
 * 字符串转拼音字符串，并按照全大写的形式输出
 * @receiver [String] 字符串
 * @param separator [String] 分隔符，默认为空字符串
 * @return [String] 拼音字符串
 */
@JsExport
fun String.toPinYin(separator: String = ""): String = toPinYinDo(separator)

expect fun String.toPinYinDo(separator: String): String