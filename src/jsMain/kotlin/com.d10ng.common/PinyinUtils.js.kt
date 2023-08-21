package com.d10ng.common

import com.d10ng.common.thirdParties.PinyinPro

actual fun String.toPinYinDo(separator: String): String {
    return PinyinPro.pinyin(this, js("{type: 'array', toneType: 'none'}")).joinToString(separator).uppercase()
}