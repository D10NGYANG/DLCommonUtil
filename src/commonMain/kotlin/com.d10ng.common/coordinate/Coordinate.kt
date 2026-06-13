package com.d10ng.common.coordinate

import kotlin.js.JsExport

/**
 * 表示一个经纬度坐标。
 *
 * 该类型不绑定具体坐标系；调用坐标转换或距离计算 API 时，调用方应确保坐标系一致。
 *
 * @property lat 纬度，单位为度。
 * @property lng 经度，单位为度。
 */
@JsExport
data class Coordinate(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
)
