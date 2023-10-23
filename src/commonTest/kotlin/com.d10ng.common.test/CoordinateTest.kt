package com.d10ng.common.test

import com.d10ng.common.coordinate.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateTest {

    @Test
    fun testConvert() {
        val point = Coordinate(39.908720, 116.397500)
        assertEquals(point.toString(), point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.WGS84).toString())
        assertEquals(Coordinate(39.91012350021168, 116.40374357520177).toString(), point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.GCJ02).toString())
        assertEquals(Coordinate(39.91646274462449, 116.41011657186905).toString(), point.convert(CoordinateSystemType.WGS84, CoordinateSystemType.BD09).toString())
        assertEquals(point.toString(), point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.GCJ02).toString())
        assertEquals(Coordinate(39.90731649978832, 116.39125642479821).toString(), point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.WGS84).toString())
        assertEquals(Coordinate(39.91506334508605, 116.40387295666915).toString(), point.convert(CoordinateSystemType.GCJ02, CoordinateSystemType.BD09).toString())
        assertEquals(point.toString(), point.convert(CoordinateSystemType.BD09, CoordinateSystemType.BD09).toString())
        assertEquals(Coordinate(39.90100884486498, 116.38486891344253).toString(), point.convert(CoordinateSystemType.BD09, CoordinateSystemType.WGS84).toString())
        assertEquals(Coordinate(39.902409805050404, 116.39110934566948).toString(), point.convert(CoordinateSystemType.BD09, CoordinateSystemType.GCJ02).toString())
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
    fun testToDMS() {
        assertEquals(0.0.toDMS(true).toString(), "0°0′0.0″")
        assertEquals(180.0.toDMS(true).toString(), "180°0′0.0″")
        assertEquals(118.234123.toDMS(true).toString(), "118°14′2.8428″")
        assertEquals(45.234.toDMS(false).toString(), "45°14′2.4″")
    }

    @Test
    fun testToLatLng() {
        assertEquals(DMS(0, 0, 0.0f).toLatLng().toString(), "0.0")
        assertEquals(DMS(180, 0, 0.0f).toLatLng().toString(), "180.0")
        assertEquals(DMS(118, 14, 2.8428f).toLatLng().toString(), "118.23412300745646")
        assertEquals(DMS(45, 14, 2.4f).toLatLng().toString(), "45.233999999364215")
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