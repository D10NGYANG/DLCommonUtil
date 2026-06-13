package com.d10ng.common.coordinate

import kotlin.js.JsExport

/**
 * 表示角度的度、分、秒。
 *
 * @property degrees 度数。
 * @property minutes 分钟部分，通常应在 `0..59`。
 * @property seconds 秒钟部分，通常应在 `[0, 60)`。
 */
@JsExport
data class DMS(
    var degrees: Int = 0,
    var minutes: Int = 0,
    var seconds: Float = 0f
) {
    /**
     * 返回形如 `118°14′2.84″` 的度分秒文本。
     */
    override fun toString(): String {
        val secondsText = seconds.toString().let {
            if ('.' in it || 'e' in it || 'E' in it) it else "$it.0"
        }
        return "$degrees°$minutes′$secondsText″"
    }
}
