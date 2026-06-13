package com.d10ng.common.coordinate

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

/**
 * 支持的地理坐标系。
 */
@OptIn(ExperimentalJsExport::class)
@JsExport
enum class CoordinateSystemType {

    /**
     * WGS 84 地球坐标系。
     *
     * GPS、北斗等卫星定位设备通常输出该坐标系。
     */
    WGS84,

    /**
     * GCJ-02 坐标系，也称火星坐标系。
     *
     * 该坐标系由 WGS 84 坐标经过偏移得到，常用于中国大陆互联网地图。
     */
    GCJ02,

    /**
     * BD-09 百度坐标系。
     *
     * 该坐标系由 GCJ-02 坐标进一步偏移得到。
     */
    BD09
}
