package com.d10ng.common.transform

import com.github.promeg.pinyinhelper.Pinyin

actual fun String.toPinYinDo(separator: String): String {
    return Pinyin.toPinyin(this, separator).uppercase()
}