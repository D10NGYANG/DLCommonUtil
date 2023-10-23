package com.d10ng.common.test

import com.d10ng.common.coordinate.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TrackTest {

    @Test
    fun testGetDistanceAndBearing() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        val result = getDistanceAndBearing(p1, p2)
        assertEquals(1379.4438755465214, result.distance)
        assertEquals(67.26721057457084, result.initialBearing)
        assertEquals(67.27675731742102, result.finalBearing)
    }

    @Test
    fun testGetDistanceOn2Points() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        var result = getDistanceOn2Points(p1, p2)
        assertEquals(1378.3587308037338, result)
        result = getDistanceOn2Points(p1, p2, true)
        assertEquals(1379.4438755465214, result)
        assertEquals(0.0, getDistanceOn2Points(p1, p1))
        assertEquals(0.0, getDistanceOn2Points(p2, p2, true))
    }

    @Test
    fun testIsPointInCircle() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        assertEquals(true, isPointInCircle(p1, p2, 2000.0))
        assertEquals(false, isPointInCircle(p1, p2, 1000.0))
    }

    @Test
    fun testGetAngleOn2Points() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        assertEquals(67.18629551668528, getAngleOn2Points(p1, p2))
        assertEquals(67.27675731742102, getAngleOn2Points(p1, p2, true))
        assertEquals(0.0, getAngleOn2Points(p1, p1))
        assertEquals(0.0, getAngleOn2Points(p2, p2, true))
        assertEquals(247.19584225955157, getAngleOn2Points(p2, p1))
        assertEquals(247.26721057457084, getAngleOn2Points(p2, p1, true))
    }

    @Test
    fun testGetPointOn2Points() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        assertEquals(p1.toString(), getPointOn2Points(p1, p2, 0f).toString())
        assertEquals(p2.toString(), getPointOn2Points(p1, p2, 1f).toString())
        assertEquals(Coordinate(39.908699702028436, 116.40095006487836).toString(), getPointOn2Points(p1, p2, 0.25f).toString())
        assertEquals(Coordinate(39.91109874730818, 116.40839058397074).toString(), getPointOn2Points(p1, p2, 0.75f).toString())
    }

    @Test
    fun testGetTotalDistance() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        assertEquals(2771.036843691702, getTotalDistance(points))
        assertEquals(0.0, getTotalDistance(arrayOf()))
        assertEquals(0.0, getTotalDistance(arrayOf(Coordinate(39.9075, 116.39723), Coordinate(39.9075, 116.39723))))
    }

    @Test
    fun testGetPointsOnDistance() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        val result = getPointsOnDistance(points, 200.0)
        val points1 = arrayOf(
            Coordinate(39.90631, 116.39137),
            Coordinate(39.90635619690078, 116.3937113506557),
            Coordinate(39.90640245744489, 116.39605270057083),
            Coordinate(39.90644879031719, 116.39839404973934),
            Coordinate(39.90669364574791, 116.4004680552202),
            Coordinate(39.9084902288706, 116.40045089777996),
            Coordinate(39.91028681154947, 116.40043374048969),
            Coordinate(39.9120833936364, 116.40041658342761),
            Coordinate(39.91382494362581, 116.40047792933832),
            Coordinate(39.91397301116708, 116.40281234454812),
            Coordinate(39.9141099976926, 116.40514735403939),
            Coordinate(39.91410994917232, 116.40748974494588),
            Coordinate(39.912560301187256, 116.40772743592362),
            Coordinate(39.910765169012556, 116.40763179513232),
            Coordinate(39.90923, 116.40755),
        )
        result.forEachIndexed { index, item ->
            assertEquals(points1[index].toString(), item.toString())
            if (index < result.size - 1) {
                assertTrue(getDistanceOn2Points(result[index], result[index + 1]) in (150.0..250.0))
            }
        }
    }

    @Test
    fun testCompressTrack() {
        val points = arrayOf(
            Coordinate(39.90631, 116.39137),
            Coordinate(39.90635619690078, 116.3937113506557),
            Coordinate(39.90640245744489, 116.39605270057083),
            Coordinate(39.90644879031719, 116.39839404973934),
            Coordinate(39.90669364574791, 116.4004680552202),
            Coordinate(39.9084902288706, 116.40045089777996),
            Coordinate(39.91028681154947, 116.40043374048969),
            Coordinate(39.9120833936364, 116.40041658342761),
            Coordinate(39.91382494362581, 116.40047792933832),
            Coordinate(39.91397301116708, 116.40281234454812),
            Coordinate(39.9141099976926, 116.40514735403939),
            Coordinate(39.91410994917232, 116.40748974494588),
            Coordinate(39.912560301187256, 116.40772743592362),
            Coordinate(39.910765169012556, 116.40763179513232),
            Coordinate(39.90923, 116.40755),
        )
        val result = compressTrack(points)
        val points1 = arrayOf(
            Coordinate(39.90631, 116.39137),
            Coordinate(39.90644879031719, 116.39839404973934),
            Coordinate(39.90669364574791, 116.4004680552202),
            Coordinate(39.9120833936364, 116.40041658342761),
            Coordinate(39.91382494362581, 116.40047792933832),
            Coordinate(39.91410994917232, 116.40748974494588),
            Coordinate(39.912560301187256, 116.40772743592362),
            Coordinate(39.90923, 116.40755),
        )
        result.forEachIndexed { index, item ->
            assertEquals(points1[index].toString(), item.toString())
        }
    }

    @Test
    fun testGetPointByBasePoint() {
        val p1 = Coordinate(39.9075, 116.39723)
        val result = getPointByBasePoint(p1, 1000.0, 45.0)
        assertEquals(Coordinate(39.91386814794339, 116.40550015299338).toString(), result.toString())
    }

    @Test
    fun testGetRandomPoint() {
        val p1 = Coordinate(39.9075, 116.39723)
        val result = getRandomPoint(p1, 10.0)
        val distance = getDistanceOn2Points(p1, result)
        assertTrue(distance in (0.0..10.0))
    }

    @Test
    fun testGetNearPartsOnLine() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        val result = getNearPartsOnLine(Coordinate(39.912570, 116.403880), points, 1000f)
        assertEquals("2,3,4", result.joinToString(","))
    }

    @Test
    fun testGetProjectionPointOnLinePart() {
        val points = arrayOf(
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
        )
        val result = getProjectionPointOnLinePart(Coordinate(39.912570, 116.403880), points)
        assertEquals(Coordinate(39.91400178062274, 116.4032660675244).toString(), result.toString())
    }

    @Test
    fun testGetProjectionPointOnLine() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        val result = getProjectionPointOnLine(Coordinate(39.912570, 116.403880), points)
        assertEquals(Coordinate(39.91400178062274, 116.4032660675244).toString(), result.toString())
    }

    @Test
    fun testGetProjectionLineOnLineWithPoint() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        val result = getProjectionLineOnLineWithPoint(Coordinate(39.912570, 116.403880), points)
        assertEquals("Coordinate(lat=39.90631, lng=116.39137),Coordinate(lat=39.90649, lng=116.40047),Coordinate(lat=39.91382, lng=116.4004),Coordinate(lat=39.91400178062274, lng=116.4032660675244)", result.joinToString(","))
    }

    @Test
    fun testGetProjectionLineOnLineWithLine() {
        val points = arrayOf(
            Coordinate(39.906310, 116.391370),
            Coordinate(39.906490, 116.400470),
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
            Coordinate(39.914110, 116.407810),
            Coordinate(39.909230, 116.407550),
        )
        val line = arrayOf(
            Coordinate(39.912570, 116.403880),
        )
        val result = getProjectionLineOnLineWithLine(line, points)
        assertEquals("Coordinate(lat=39.90631, lng=116.39137),Coordinate(lat=39.90649, lng=116.40047),Coordinate(lat=39.91382, lng=116.4004),Coordinate(lat=39.91400178062274, lng=116.4032660675244)", result.joinToString(","))
    }
}