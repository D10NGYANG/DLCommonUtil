package com.d10ng.common.test

import com.d10ng.common.coordinate.*
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CoordinateTest {

    @Test
    fun testConvert() {
        val doubleEqual:(Double, Double) -> Unit = { a, b -> assertTrue { (a - b).absoluteValue < 0.0000001 } }
        val point = Coordinate(39.908720, 116.397500)
        val wgs84ToWgs84 = point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.WGS84)
        doubleEqual(wgs84ToWgs84.lat, point.lat)
        doubleEqual(wgs84ToWgs84.lng, point.lng)
        val wgs84ToGcj02Base = Coordinate(39.91012350021168, 116.40374357520177)
        val wgs84ToGcj02 = point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.GCJ02)
        doubleEqual(wgs84ToGcj02.lat, wgs84ToGcj02Base.lat)
        doubleEqual(wgs84ToGcj02.lng, wgs84ToGcj02Base.lng)
        val wgs84ToBd09Base = Coordinate(39.91646274462449, 116.41011657186905)
        val wgs84ToBd09 = point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.BD09)
        doubleEqual(wgs84ToBd09.lat, wgs84ToBd09Base.lat)
        doubleEqual(wgs84ToBd09.lng, wgs84ToBd09Base.lng)
        val gcj02ToGcj02 = point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.GCJ02)
        doubleEqual(gcj02ToGcj02.lat, point.lat)
        doubleEqual(gcj02ToGcj02.lng, point.lng)
        val gcj02ToWgs84Base = Coordinate(39.90731649978832, 116.39125642479821)
        val gcj02ToWgs84 = point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.WGS84)
        doubleEqual(gcj02ToWgs84.lat, gcj02ToWgs84Base.lat)
        doubleEqual(gcj02ToWgs84.lng, gcj02ToWgs84Base.lng)
        val gcj02ToBd09Base = Coordinate(39.91506334508605, 116.40387295666915)
        val gcj02ToBd09 = point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.BD09)
        doubleEqual(gcj02ToBd09.lat, gcj02ToBd09Base.lat)
        doubleEqual(gcj02ToBd09.lng, gcj02ToBd09Base.lng)
        val bd09ToBd09 = point.convert(CoordinateSystemType.BD09, CoordinateSystemType.BD09)
        doubleEqual(bd09ToBd09.lat, point.lat)
        doubleEqual(bd09ToBd09.lng, point.lng)
        val bd09ToWgs84Base = Coordinate(39.90100884486498, 116.38486891344253)
        val bd09ToWgs84 = point.convert(CoordinateSystemType.BD09, CoordinateSystemType.WGS84)
        doubleEqual(bd09ToWgs84.lat, bd09ToWgs84Base.lat)
        doubleEqual(bd09ToWgs84.lng, bd09ToWgs84Base.lng)
        val bd09ToGcj02Base = Coordinate(39.902409805050404, 116.39110934566948)
        val bd09ToGcj02 = point.convert(CoordinateSystemType.BD09, CoordinateSystemType.GCJ02)
        doubleEqual(bd09ToGcj02.lat, bd09ToGcj02Base.lat)
        doubleEqual(bd09ToGcj02.lng, bd09ToGcj02Base.lng)
    }

    @Test
    fun testIsEastLongitude() {
        assertEquals(0.0.isEastLongitude(), true)
        assertEquals(180.0.isEastLongitude(), true)
        assertEquals(360.0.isEastLongitude(), false)
        assertEquals(90.0.isEastLongitude(), true)
        assertEquals(270.0.isEastLongitude(), false)
        assertEquals((-45.0).isEastLongitude(), false)
    }

    @Test
    fun testIsNorthLatitude() {
        assertEquals(0.0.isNorthLatitude(), true)
        assertEquals(90.0.isNorthLatitude(), true)
        assertEquals(180.0.isNorthLatitude(), false)
        assertEquals(360.0.isNorthLatitude(), false)
        assertEquals(270.0.isNorthLatitude(), false)
        assertEquals((-45.0).isNorthLatitude(), false)
    }

    @Test
    fun testToLongitudeNoPre() {
        assertEquals(0.0.toLongitudeNoPre(), 0.0)
        assertEquals(180.0.toLongitudeNoPre(), 180.0)
        assertEquals(360.0.toLongitudeNoPre(), 0.0)
        assertEquals(90.0.toLongitudeNoPre(), 90.0)
        assertEquals(270.0.toLongitudeNoPre(), 90.0)
        assertEquals((-45.0).toLongitudeNoPre(), 45.0)
    }

    @Test
    fun testToLatitudeNoPre() {
        assertEquals(0.0.toLatitudeNoPre(), 0.0)
        assertEquals(180.0.toLatitudeNoPre(), 0.0)
        assertEquals(135.0.toLatitudeNoPre(), 45.0)
        assertEquals(90.0.toLatitudeNoPre(), 90.0)
        assertEquals((-45.0).toLatitudeNoPre(), 45.0)
    }

    @Test
    fun testToFullLongitude() {
        assertEquals(0.0.toFullLongitude(true), 0.0)
        assertEquals(180.0.toFullLongitude(true), 180.0)
        assertEquals(113.1.toFullLongitude(true), 113.1)
        assertEquals(113.1.toFullLongitude(false), 246.9)
        assertEquals(113.1.toFullLongitude(false, isPositive = false), -113.1)
    }

    @Test
    fun testToFullLatitude() {
        assertEquals(0.0.toFullLatitude(true), 0.0)
        assertEquals(90.0.toFullLatitude(true), 90.0)
        assertEquals(113.1.toFullLatitude(true), 113.1)
        assertEquals(113.1.toFullLatitude(true, false), -66.9)
        assertEquals(10.1.toFullLatitude(false), 169.9)
        assertEquals(10.1.toFullLatitude(false, isPositive = false), -10.1)
    }

    @Test
    fun testToDMS() {
        assertEquals(0.0.toDMS(true).toString(), "0°0′0.0″")
        assertEquals(180.0.toDMS(true).toString(), "180°0′0.0″")
        assertEquals(118.234123.toDMS(true).toString(), "118°14′2.84″")
        assertEquals(45.234.toDMS(false).toString(), "45°14′2.4″")
    }

    @Test
    fun testToLatLng() {
        assertEquals(DMS(0, 0, 0.0f).toLatLng(), 0.0)
        assertEquals(DMS(180, 0, 0.0f).toLatLng(), 180.0)
        assertTrue { DMS(118, 14, 2.8428f).toLatLng() in 118.234122 .. 118.234124 }
        assertTrue { DMS(45, 14, 2.4f).toLatLng() in 45.233999 .. 45.234001}
    }

    @Test
    fun testToLatLngString() {
        assertEquals(118.234123.toLatLngString(true), "东经E118°14′2.84″")
        assertEquals(45.234.toLatLngString(false), "北纬N45°14′2.40″")
        assertEquals(118.234123.toLatLngString(true, "d°m′S.ss″"), "118°14′2.84″")
        assertEquals(45.234.toLatLngString(false, "d°m′S.ss″"), "45°14′2.40″")
        assertEquals(118.234123.toLatLngString(true, "Fd°m′S.ss″"), "E118°14′2.84″")
        assertEquals(45.234.toLatLngString(false, "Fd°m′S.ss″"), "N45°14′2.40″")
        assertEquals(118.234123.toLatLngString(true, "CHd°m′S.ss″"), "东经118°14′2.84″")
        assertEquals(45.234.toLatLngString(false, "CHd°m′S.ss″"), "北纬45°14′2.40″")
    }

    @Test
    fun testToLongitudeString() {
        assertEquals(118.234123.toLongitudeString(), "东经E118°14′2.84″")
    }

    @Test
    fun testToLatitudeString() {
        assertEquals(45.234.toLatitudeString(), "北纬N45°14′2.40″")
    }

    @Test
    fun testToLatLngByString() {
        assertEquals("东经E118°14′2.84″".toLatLng("CHFd°m′S.ss″"), 118.23412222222223)
        assertEquals("北纬N45°14′2.40″".toLatLng("CHFd°m′S.ss″"), 45.234)
        assertEquals("东经118°14′2.84″".toLatLng("CHd°m′S.ss″"), 118.23412222222223)
        assertEquals("北纬45°14′2.40″".toLatLng("CHd°m′S.ss″"), 45.234)
    }

    @Test
    fun testToLongitude() {
        assertEquals("东经E118°14′2.84″".toLongitude("CHFd°m′S.ss″"), 118.23412222222223)
        assertEquals("东经118°14′2.84″".toLongitude("CHd°m′S.ss″"), 118.23412222222223)
        assertEquals("118°14′2.84″".toLongitude("d°m′S.ss″"), 118.23412222222223)
    }

    @Test
    fun testToLatitude() {
        assertEquals("北纬N45°14′2.40″".toLatitude("CHFd°m′S.ss″"), 45.234)
        assertEquals("北纬45°14′2.40″".toLatitude("CHd°m′S.ss″"), 45.234)
        assertEquals("45°14′2.40″".toLatitude("d°m′S.ss″"), 45.234)
    }

    @Test
    fun testddmmpmmmm2LatLng() {
        assertEquals(11301.8789.ddmmpmmmm2LatLng(), 113.03131499999999)
    }

    @Test
    fun testlatLng2ddmmpmmmm() {
        assertEquals(113.03131499999999.latLng2ddmmpmmmm(), 11301.8789)
    }
}