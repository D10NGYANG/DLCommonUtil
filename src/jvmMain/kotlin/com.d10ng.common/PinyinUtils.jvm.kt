package com.d10ng.common

import com.github.promeg.pinyinhelper.Pinyin

actual fun String.toPinYinDo(separator: String): String {
    return Pinyin.toPinyin(this, separator)
}