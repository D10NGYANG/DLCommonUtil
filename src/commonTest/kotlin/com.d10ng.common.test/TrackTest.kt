package com.d10ng.common.test

import com.d10ng.common.coordinate.*
import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

class TrackTest {

    private fun doubleEquals(a: Double, b: Double) {
        assertTrue { (a - b).absoluteValue < 0.00000001 }
    }

    @Test
    fun testGetDistance() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        doubleEquals(1378.3587308037338, getDistanceOn2Points(p1, p2))
    }

    @Test
    fun testGetDistanceAndBearing() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        val result = getDistanceAndBearing(p1, p2)
        doubleEquals(1379.4438755465214, result.distance)
        doubleEquals(67.26721057457084, result.initialBearing)
        doubleEquals(67.27675731742102, result.finalBearing)
    }

    @Test
    fun testGetDistanceOn2Points() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        var result = getDistanceOn2Points(p1, p2)
        doubleEquals(1378.3587308037338, result)
        result = getDistanceOn2Points(p1, p2, true)
        doubleEquals(1379.4438755465214, result)
        doubleEquals(0.0, getDistanceOn2Points(p1, p1))
        doubleEquals(0.0, getDistanceOn2Points(p2, p2, true))
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
        doubleEquals(67.18629551668528, getAngleOn2Points(p1, p2))
        doubleEquals(67.26721057457084, getAngleOn2Points(p1, p2, true))
        doubleEquals(0.0, getAngleOn2Points(p1, p1))
        doubleEquals(0.0, getAngleOn2Points(p2, p2, true))
        doubleEquals(247.19584225955157, getAngleOn2Points(p2, p1))
        doubleEquals(247.27675731742102, getAngleOn2Points(p2, p1, true))
    }

    @Test
    fun testGetPointOn2Points() {
        val p1 = Coordinate(39.9075, 116.39723)
        val p2 = Coordinate(39.9123, 116.41211)
        val p3 = getPointOn2Points(p1, p2, 0f)
        val p4 = getPointOn2Points(p1, p2, 1f)
        val p5 = getPointOn2Points(p1, p2, 0.25f)
        val p6 = getPointOn2Points(p1, p2, 0.75f)
        assertNotSame(p1, p3)
        assertNotSame(p2, p4)
        doubleEquals(p1.lat, p3.lat)
        doubleEquals(p1.lng, p3.lng)
        doubleEquals(p2.lat, p4.lat)
        doubleEquals(p2.lng, p4.lng)
        val totalDistance = getDistanceOn2Points(p1, p2, true)
        assertTrue((getDistanceOn2Points(p1, p5, true) - totalDistance * 0.25).absoluteValue < 0.01)
        assertTrue((getDistanceOn2Points(p1, p6, true) - totalDistance * 0.75).absoluteValue < 0.01)
        assertTrue(getDistanceOn2Points(p5, p2, true) > getDistanceOn2Points(p6, p2, true))
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
        doubleEquals(2771.036843691702, getTotalDistance(points))
        doubleEquals(0.0, getTotalDistance(arrayOf()))
        doubleEquals(0.0, getTotalDistance(arrayOf(Coordinate(39.9075, 116.39723), Coordinate(39.9075, 116.39723))))
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
        assertEquals(points.first(), result.first())
        assertEquals(points.last(), result.last())
        result.forEachIndexed { index, item ->
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
        assertEquals(points.first(), result.first())
        assertEquals(points.last(), result.last())
        assertTrue(result.size < points.size)
        assertTrue(result.all { it in points })
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
        assertEquals("2,3,1", result.joinToString(","))
    }

    @Test
    fun testGetProjectionPointOnLinePart() {
        val points = arrayOf(
            Coordinate(39.913820, 116.400400),
            Coordinate(39.914110, 116.404970),
        )
        val result = getProjectionPointOnLinePart(Coordinate(39.912570, 116.403880), points)
        val base = Coordinate(39.914030851869235, 116.4037224505048)
        doubleEquals(result.lat, base.lat)
        doubleEquals(result.lng, base.lng)
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
        val base = Coordinate(39.914030851869235, 116.4037224505048)
        doubleEquals(result.lat, base.lat)
        doubleEquals(result.lng, base.lng)
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
        doubleEquals(result[0].lat, 39.906310)
        doubleEquals(result[0].lng, 116.391370)
        doubleEquals(result[1].lat, 39.906490)
        doubleEquals(result[1].lng, 116.400470)
        doubleEquals(result[2].lat, 39.913820)
        doubleEquals(result[2].lng, 116.400400)
        doubleEquals(result[3].lat, 39.914030851869235)
        doubleEquals(result[3].lng, 116.4037224505048)
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
        doubleEquals(result[0].lat, 39.906310)
        doubleEquals(result[0].lng, 116.391370)
        doubleEquals(result[1].lat, 39.906490)
        doubleEquals(result[1].lng, 116.400470)
        doubleEquals(result[2].lat, 39.913820)
        doubleEquals(result[2].lng, 116.400400)
        doubleEquals(result[3].lat, 39.914030851869235)
        doubleEquals(result[3].lng, 116.4037224505048)
    }

    @Test
    fun testTrackBoundaryConditions() {
        val point = Coordinate(39.9075, 116.39723)
        assertTrue(getPointsOnDistance(emptyArray(), 100.0).isEmpty())
        assertEquals(arrayOf(point).toList(), getPointsOnDistance(arrayOf(point), 100.0).toList())
        assertEquals(point, getProjectionPointOnLinePart(point, arrayOf(point, point)))
        assertEquals(point, getRandomPoint(point, 0.0))

        assertFailsWith<IllegalArgumentException> { getPointsOnDistance(arrayOf(point), 0.0) }
        assertFailsWith<IllegalArgumentException> { getPointsOnDistance(arrayOf(point), -1.0) }
        assertFailsWith<IllegalArgumentException> { isPointInCircle(point, point, -1.0) }
        assertFailsWith<IllegalArgumentException> { getRandomPoint(point, Double.NaN) }
        assertFailsWith<IllegalArgumentException> {
            getDistanceOn2Points(Coordinate(91.0, 0.0), point)
        }
        assertFailsWith<IllegalArgumentException> {
            getProjectionPointOnLinePart(point, arrayOf(point))
        }
        assertFailsWith<IllegalArgumentException> {
            getProjectionLineOnLineWithLine(emptyArray(), arrayOf(point))
        }
    }

    @Test
    fun testDestinationNormalizesDateLine() {
        val destination = getPointByBasePoint(Coordinate(0.0, 179.9), 50_000.0, 90.0)
        assertTrue(destination.lng in -180.0..<180.0)
        assertTrue(destination.lng < 0.0)
    }

    @Test
    fun shortSegmentsKeepTheirBearingAndMidpoint() {
        val start = Coordinate(0.0, 0.0)
        for (distance in listOf(0.1, 1.0, 5.0, 10.0)) {
            for (bearing in listOf(0.0, 90.0, 180.0, 270.0)) {
                val end = getPointByBasePoint(start, distance, bearing)
                val actualBearing = getAngleOn2Points(start, end, true)
                val difference = (actualBearing - bearing + 540.0) % 360.0 - 180.0
                assertTrue(difference.absoluteValue < 0.001)
                val midpoint = getPointOn2Points(start, end, 0.5f)
                assertTrue((getDistanceOn2Points(start, midpoint, true) - distance / 2).absoluteValue < 0.001)
                assertTrue((getDistanceOn2Points(midpoint, end, true) - distance / 2).absoluteValue < 0.001)
            }
        }
        val sampled = getPointsOnDistance(arrayOf(start, Coordinate(0.0, 0.0001)), 1.0)
        assertTrue(sampled.size in 12..13)
        assertTrue(sampled.all { it.lat.absoluteValue < 1e-9 })
    }

    @Test
    fun projectionFindsPerpendicularAndClampsEndpoints() {
        val start = Coordinate(0.0, 0.0)
        val end = Coordinate(0.0, 0.001)
        val segment = arrayOf(start, end)
        assertEquals(start, getProjectionPointOnLinePart(Coordinate(0.0, -0.001), segment))
        assertEquals(end, getProjectionPointOnLinePart(Coordinate(0.0, 0.002), segment))
        val projection = getProjectionPointOnLinePart(Coordinate(0.001, 0.0002), segment)
        doubleEquals(0.0, projection.lat)
        doubleEquals(0.0002, projection.lng)
        val reversed = getProjectionPointOnLinePart(Coordinate(0.001, 0.0002), segment.reversedArray())
        doubleEquals(projection.lat, reversed.lat)
        doubleEquals(projection.lng, reversed.lng)
        assertEquals(start, getProjectionPointOnLinePart(start, arrayOf(start, start)))
    }

    @Test
    fun projectionCrossesDateLineAndHandlesAntipodalEndpoints() {
        val projection = getProjectionPointOnLinePart(
            Coordinate(1.0, 180.0), arrayOf(Coordinate(0.0, 179.0), Coordinate(0.0, -179.0)),
        )
        doubleEquals(0.0, projection.lat)
        doubleEquals(180.0, projection.lng.absoluteValue)
        assertEquals(Coordinate(0.0, 0.0), getProjectionPointOnLinePart(
            Coordinate(1.0, 0.0), arrayOf(Coordinate(0.0, 0.0), Coordinate(0.0, 180.0)),
        ))
    }

    @Test
    fun nearestSegmentDoesNotDependOnSegmentLength() {
        val point = Coordinate(0.0, 0.0)
        val line = arrayOf(
            Coordinate(0.0, -0.01), Coordinate(0.0, 0.01),
            Coordinate(0.001, 0.01), Coordinate(0.001, 0.0),
        )
        assertEquals(listOf(0), getNearPartsOnLine(point, line, 10f).toList())
        val projection = getProjectionPointOnLine(point, line)
        assertTrue(getDistanceOn2Points(point, projection) < 0.001)
        val prefix = getProjectionLineOnLineWithPoint(point, line)
        assertEquals(2, prefix.size)
        assertEquals(line.first(), prefix.first())
        assertTrue(getDistanceOn2Points(point, prefix.last()) < 0.001)
        val directed = getProjectionLineOnLineWithLine(arrayOf(Coordinate(0.0, -0.001), point), line)
        assertEquals(2, directed.size)
        assertTrue(getDistanceOn2Points(point, directed.last()) < 0.001)
    }

    @Test
    fun projectionHandlesPolarEndpoints() {
        val point = Coordinate(85.0, 45.0)
        val segment = arrayOf(Coordinate(90.0, 0.0), Coordinate(80.0, 45.0))
        for (part in listOf(segment, segment.reversedArray())) {
            val projection = getProjectionPointOnLinePart(point, part)
            assertTrue(getDistanceOn2Points(point, projection) < 0.001)
        }
    }
}
