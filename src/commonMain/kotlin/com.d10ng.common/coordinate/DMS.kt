package com.d10ng.common.coordinate

import kotlin.js.JsExport

/**
 * 经纬度的度分秒数据
 */
@JsExport
data class DMS(
    // 度
    var degrees: Int = 0,
    // 分
    var minutes: Int = 0,
    // 秒
    var seconds: Float = 0f
) {
    override fun toString(): String {
        return "$degrees°$minutes′$seconds″"
    }
}
