package com.d10ng.common

import kotlin.js.JsExport

/**
 * 字符串转拼音字符串，并按照全大写的形式输出
 * @receiver [String]
 * @param separator [String] 分隔符
 * @return [String]
 */
@JsExport
fun String.toPinYin(separator: String = ""): String = toPinYinDo(separator)

expect fun String.toPinYinDo(separator: String): String