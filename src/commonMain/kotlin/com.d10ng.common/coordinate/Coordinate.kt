package com.d10ng.common.coordinate

import kotlin.js.JsExport

/**
 * 坐标
 */
@JsExport
data class Coordinate(
    /**
     * 纬度
     */
    var lat: Double = 0.0,

    /**
     * 经度
     */
    var lng: Double = 0.0,
)
