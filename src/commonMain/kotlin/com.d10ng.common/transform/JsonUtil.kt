package com.d10ng.common.transform

import kotlinx.serialization.json.Json

/**
 * 自定义JSON解析工具
 */
val json by lazy {
    Json {
        // 忽略JSON字符串里有但data class中没有的key
        ignoreUnknownKeys = true
        // 如果接收到的JSON字符串的value为null，但是data class中的对应属性不能为null，那就使用属性的默认值
        coerceInputValues = true
        // 如果创建data class实例时有些属性没有赋值，那就使用默认值进行转换成JSON字符串
        encodeDefaults = true
        // 属性放宽
        isLenient = true
    }
}