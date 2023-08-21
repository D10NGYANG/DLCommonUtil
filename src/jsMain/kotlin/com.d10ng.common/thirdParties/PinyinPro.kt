package com.d10ng.common.thirdParties

@JsModule("pinyin-pro")
@JsNonModule
external object PinyinPro {

    interface BasicOptions {
        var toneType: String?
    }

    interface OptionsReturnArray: BasicOptions {
        var type: String?
    }

    fun pinyin(word: String, options: OptionsReturnArray?): Array<String>
}