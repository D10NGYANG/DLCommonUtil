package com.d10ng.common.coordinate

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

/**
 * 两个坐标之间的距离和方位角计算结果。
 *
 * @property distance 两点间的椭球测地线距离，单位为米。
 * @property initialBearing 从起点出发时的初始方位角，单位为度。
 * @property finalBearing 到达终点时的最终方位角，单位为度。
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
data class DistanceAndBearing(
    var distance: Double = 0.0,
    var initialBearing: Double = 0.0,
    var finalBearing: Double = 0.0,
)
