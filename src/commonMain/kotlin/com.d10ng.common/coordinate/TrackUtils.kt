@file:JsExport
package com.d10ng.common.coordinate

import kotlin.js.JsExport
import kotlin.math.*
import kotlin.random.Random

private const val EARTH_RADIUS_LONG = 6378137.0
private const val EARTH_RADIUS_SHORT = 6356752.314245
private const val EARTH_FLATTENING = 0.003352810664747481
private const val VINCENTY_MAX_ITERATIONS = 100
private const val VINCENTY_TOLERANCE = 1e-12
private const val MAX_RESAMPLED_POINTS = 1_000_000

private fun Coordinate.requireValid(name: String) {
    require(lat.isFinite() && lat in -90.0..90.0) {
        "$name.lat must be finite and in -90.0..90.0, but was $lat"
    }
    require(lng.isFinite() && lng in -180.0..180.0) {
        "$name.lng must be finite and in -180.0..180.0, but was $lng"
    }
}

private fun normalizeBearing(angle: Double): Double = (angle % 360.0 + 360.0) % 360.0

private fun angleDifference(first: Double, second: Double): Double {
    val difference = abs(normalizeBearing(first) - normalizeBearing(second))
    return min(difference, 360.0 - difference)
}

private fun getHaversineDistance(point1: Coordinate, point2: Coordinate): Double {
    val lat1Rad = toRadians(point1.lat)
    val lat2Rad = toRadians(point2.lat)
    val latitudeDifference = lat1Rad - lat2Rad
    val longitudeDifference = toRadians(point1.lng) - toRadians(point2.lng)
    val sinHalfLatitude = sin(latitudeDifference / 2.0)
    val sinHalfLongitude = sin(longitudeDifference / 2.0)
    val haversine = sinHalfLatitude * sinHalfLatitude +
        cos(lat1Rad) * cos(lat2Rad) * sinHalfLongitude * sinHalfLongitude
    return 2.0 * EARTH_RADIUS_LONG * asin(sqrt(haversine.coerceIn(0.0, 1.0)))
}

/**
 * 使用 Vincenty 反解算法计算两点间的距离和方位角。
 *
 * @param point1 起点，使用十进制度。
 * @param point2 终点，使用十进制度。
 * @return 距离和方位角；距离单位为米，方位角单位为度。
 * @throws IllegalArgumentException 当任一坐标不是有限值或超出经纬度范围时。
 * @throws IllegalStateException 当 Vincenty 算法在 100 次迭代内未收敛时。
 */
fun getDistanceAndBearing(point1: Coordinate, point2: Coordinate): DistanceAndBearing {
    point1.requireValid("point1")
    point2.requireValid("point2")
    if (point1 == point2) return DistanceAndBearing()

    val radLon1 = toRadians(point1.lng)
    val radLat1 = toRadians(point1.lat)
    val radLon2 = toRadians(point2.lng)
    val radLat2 = toRadians(point2.lat)
    val L = radLon2 - radLon1
    val tanU1 = (1 - EARTH_FLATTENING) * tan(radLat1)
    val cosU1 = 1 / sqrt((1 + tanU1 * tanU1))
    val sinU1 = tanU1 * cosU1
    val tanU2 = (1 - EARTH_FLATTENING) * tan(radLat2)
    val cosU2 = 1 / sqrt((1 + tanU2 * tanU2))
    val sinU2 = tanU2 * cosU2
    val antipodal = abs(L) > PI / 2 || abs(radLat2 - radLat1) > PI / 2
    var f9 = L
    var sinF9: Double
    var cosF9: Double
    var fq = if (antipodal) PI else 0.0
    var sinFq = 0.0
    var cosFq = if (antipodal) -1.0 else 1.0
    var sinSFqFa: Double
    var cos2Fqm = 1.0
    var cosSFqFa = 1.0
    var iterations = 0
    var f91: Double
    var converged = false
    do {
        sinF9 = sin(f9)
        cosF9 = cos(f9)
        sinSFqFa = (cosU2 * sinF9) * (cosU2 * sinF9) + (cosU1 * sinU2 - sinU1 * cosU2 * cosF9) * (cosU1 * sinU2 - sinU1 * cosU2 * cosF9)
        if (abs(sinSFqFa) < 1e-24) {
            converged = true
            break
        }
        sinFq = sqrt(sinSFqFa)
        cosFq = sinU1 * sinU2 + cosU1 * cosU2 * cosF9
        fq = atan2(sinFq, cosFq)
        val sinFa = cosU1 * cosU2 * sinF9 / sinFq
        cosSFqFa = 1 - sinFa * sinFa
        cos2Fqm = if (cosSFqFa != 0.0) cosFq - 2 * sinU1 * sinU2 / cosSFqFa else 0.0
        val C = (EARTH_FLATTENING / 16) * cosSFqFa * (4 + EARTH_FLATTENING * (4 - 3 * cosSFqFa))
        f91 = f9
        f9 = L + (1 - C) * EARTH_FLATTENING * sinFa * (fq + C * sinFq * (cos2Fqm + C * cosFq * (-1 + 2 * cos2Fqm * cos2Fqm)))
        val iterationCheck = if (antipodal) abs(f9) - PI else abs(f9)
        check(iterationCheck <= PI) { "Vincenty inverse formula produced an invalid longitude difference" }
        converged = abs(f9 - f91) <= VINCENTY_TOLERANCE
        iterations++
    } while (!converged && iterations < VINCENTY_MAX_ITERATIONS)
    check(converged) { "Vincenty inverse formula failed to converge" }
    val uSq = cosSFqFa * (EARTH_RADIUS_LONG * EARTH_RADIUS_LONG - EARTH_RADIUS_SHORT * EARTH_RADIUS_SHORT) / (EARTH_RADIUS_SHORT * EARTH_RADIUS_SHORT)
    val A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)))
    val B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)))
    val deltaFq = B * sinFq * (cos2Fqm + B / 4 * (cosFq * (-1 + 2 * cos2Fqm * cos2Fqm) - B / 6 * cos2Fqm * (-3 + 4 * sinFq * sinFq) * (-3 + 4 * cos2Fqm * cos2Fqm)))
    val s = EARTH_RADIUS_SHORT * A * (fq - deltaFq)
    val alpha1 = if (abs(sinSFqFa) < 1e-12) 0.0 else atan2(cosU2 * sinF9, cosU1 * sinU2 - sinU1 * cosU2 * cosF9)
    val alpha2 = if (abs(sinSFqFa) < 1e-12) PI else atan2(cosU1 * sinF9, -sinU1 * cosU2 + cosU1 * sinU2 * cosF9)
    return DistanceAndBearing(s, toDegrees(alpha1), toDegrees(alpha2))
}

/**
 * 计算两点之间的距离。
 *
 * @param point1 起点，使用十进制度。
 * @param point2 终点，使用十进制度。
 * @param highPrecision 为 `true` 时使用 Vincenty 椭球算法，否则使用 Haversine 球面算法。
 * @return 两点间距离，单位为米。
 * @throws IllegalArgumentException 当任一坐标不是有限值或超出经纬度范围时。
 * @throws IllegalStateException 当高精度算法未收敛时。
 */
fun getDistanceOn2Points(point1: Coordinate, point2: Coordinate, highPrecision: Boolean = false): Double {
    if (highPrecision) return getDistanceAndBearing(point1, point2).distance
    point1.requireValid("point1")
    point2.requireValid("point2")
    return getHaversineDistance(point1, point2)
}

/**
 * 将角度转换为弧度。
 *
 * @param degree 角度。
 * @return 对应的弧度。
 */
fun toRadians(degree: Double): Double = degree * PI / 180.0

/**
 * 将弧度转换为角度。
 *
 * @param radian 弧度。
 * @return 对应的角度。
 */
fun toDegrees(radian: Double): Double = radian * 180.0 / PI

/**
 * 判断点是否位于指定圆形区域内或边界上。
 *
 * @param point 待判断坐标点。
 * @param center 圆心坐标。
 * @param radius 圆半径，单位为米，必须为非负有限值。
 * @return 位于圆内或边界上时返回 `true`。
 * @throws IllegalArgumentException 当半径无效或坐标无效时。
 */
fun isPointInCircle(point: Coordinate, center: Coordinate, radius: Double): Boolean {
    require(radius.isFinite() && radius >= 0.0) {
        "radius must be finite and non-negative, but was $radius"
    }
    return getDistanceOn2Points(point, center) <= radius
}

/**
 * 计算从起点指向终点的方位角。
 *
 * @param point1 起点。
 * @param point2 终点。
 * @param highPrecision 为 `true` 时使用 Vincenty 算法，否则使用球面算法。
 * @return `[0, 360)` 范围内的方位角，单位为度；两点重合时返回 `0.0`。
 * @throws IllegalArgumentException 当任一坐标无效时。
 * @throws IllegalStateException 当高精度算法未收敛时。
 */
fun getAngleOn2Points(point1: Coordinate, point2: Coordinate, highPrecision: Boolean = false): Double {
    if (highPrecision) {
        return normalizeBearing(getDistanceAndBearing(point1, point2).initialBearing)
    }
    point1.requireValid("point1")
    point2.requireValid("point2")
    if (point1 == point2) return 0.0
    val lat1 = toRadians(point1.lat)
    val lon1 = toRadians(point1.lng)
    val lat2 = toRadians(point2.lat)
    val lon2 = toRadians(point2.lng)
    val dLon = lon2 - lon1
    val y = sin(dLon) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLon)
    return normalizeBearing(toDegrees(atan2(y, x)))
}

/**
 * 沿两点间的椭球测地线获取指定比例位置的坐标。
 *
 * @param point1 起点。
 * @param point2 终点。
 * @param present 距离比例，必须为 `[0, 1]` 范围内的有限值。
 * @return 对应比例位置的坐标。
 * @throws IllegalArgumentException 当比例或坐标无效时。
 */
fun getPointOn2Points(point1: Coordinate, point2: Coordinate, present: Float): Coordinate {
    require(present.isFinite() && present in 0.0f..1.0f) {
        "present must be finite and in 0.0..1.0, but was $present"
    }
    point1.requireValid("point1")
    point2.requireValid("point2")
    if (present == 0.0f) return point1.copy()
    if (present == 1.0f) return point2.copy()
    val distanceAndBearing = getDistanceAndBearing(point1, point2)
    val newDistance = distanceAndBearing.distance * present
    return getPointByBasePoint(point1, newDistance, distanceAndBearing.initialBearing)
}

/**
 * 计算轨迹中相邻坐标之间的总距离。
 *
 * @param points 轨迹坐标；空数组或单点轨迹的距离为 `0.0`。
 * @return 总距离，单位为米。
 * @throws IllegalArgumentException 当任一坐标无效时。
 */
fun getTotalDistance(points: Array<Coordinate>): Double {
    points.forEachIndexed { index, point -> point.requireValid("points[$index]") }
    var total = 0.0
    for (i in 0 until points.lastIndex) {
        total += getHaversineDistance(points[i], points[i + 1])
    }
    return total
}

/**
 * 按固定距离对轨迹进行重采样。
 *
 * 首尾点始终保留；相邻采样点沿原轨迹的间隔尽量接近 [distance]。
 *
 * @param points 原始轨迹。
 * @param distance 采样间隔，单位为米，必须为正有限值。
 * @return 重采样后的轨迹；空轨迹返回空数组。
 * @throws IllegalArgumentException 当间隔或任一坐标无效时。
 */
fun getPointsOnDistance(points: Array<Coordinate>, distance: Double): Array<Coordinate> {
    require(distance.isFinite() && distance > 0.0) {
        "distance must be finite and positive, but was $distance"
    }
    if (points.isEmpty()) return emptyArray()
    points.forEachIndexed { index, point -> point.requireValid("points[$index]") }
    if (points.size == 1) return arrayOf(points[0].copy())
    var totalDistance = 0.0
    for (index in 0 until points.lastIndex) {
        totalDistance += getHaversineDistance(points[index], points[index + 1])
    }
    val estimatedPointCount = totalDistance / distance
    require(estimatedPointCount <= MAX_RESAMPLED_POINTS) {
        "distance would produce more than $MAX_RESAMPLED_POINTS sampled points"
    }

    val result = mutableListOf(points[0].copy())
    var distanceSinceLastSample = 0.0
    for (index in 0 until points.lastIndex) {
        var segmentStart = points[index]
        val segmentEnd = points[index + 1]
        var segmentLength = getHaversineDistance(segmentStart, segmentEnd)

        while (segmentLength > 0.0 && distanceSinceLastSample + segmentLength >= distance) {
            val distanceToSample = distance - distanceSinceLastSample
            val fraction = (distanceToSample / segmentLength).toFloat().coerceIn(0.0f, 1.0f)
            val sample = getPointOn2Points(segmentStart, segmentEnd, fraction)
            check(sample != segmentStart) {
                "distance is below the coordinate precision supported by this track"
            }
            result.add(sample)
            segmentStart = sample
            segmentLength = getHaversineDistance(segmentStart, segmentEnd)
            distanceSinceLastSample = 0.0
        }
        distanceSinceLastSample += segmentLength
    }
    if (result.last() != points.last()) result.add(points.last().copy())
    return result.toTypedArray()
}

/**
 * 根据相邻线段距离和转向角压缩轨迹。
 *
 * @param points 原始轨迹。
 * @return 压缩后的轨迹；十个点及以下的轨迹仅复制数组。
 * @throws IllegalArgumentException 当任一坐标无效时。
 */
fun compressTrack(points: Array<Coordinate>): Array<Coordinate> {
    points.forEachIndexed { index, point -> point.requireValid("points[$index]") }
    if (points.size <= 10) return points.copyOf()
    val result = mutableListOf<Coordinate>()
    var a = points[0]
    var b = points[1]
    for (i in points.indices) {
        if (i < 2) {
            result.add(points[i])
            continue
        }
        val c = points[i]
        val distance = getHaversineDistance(a, b) + getHaversineDistance(b, c)
        val x = if (distance <= 10) 90 else if (distance > 50) 5 else max(((1 - distance / 50) * 90).toInt(), 5)
        val j1 = getAngleOn2Points(a, b)
        val j2 = getAngleOn2Points(b, c)
        if (angleDifference(j1, j2) < x) {
            result.removeLast()
        } else {
            a = b
        }
        result.add(c)
        b = c
    }
    return result.toTypedArray()
}

/**
 * 使用 Vincenty 正解算法，根据距离和方位角计算目标坐标。
 *
 * @param point 起点。
 * @param distance 距离，单位为米，必须为非负有限值。
 * @param angle 方位角，单位为度，北为 `0`，顺时针增加。
 * @return 目标坐标，经度规范化到 `[-180, 180)`。
 * @throws IllegalArgumentException 当参数无效时。
 * @throws IllegalStateException 当 Vincenty 算法在 100 次迭代内未收敛时。
 */
fun getPointByBasePoint(point: Coordinate, distance: Double, angle: Double): Coordinate {
    point.requireValid("point")
    require(distance.isFinite() && distance >= 0.0) {
        "distance must be finite and non-negative, but was $distance"
    }
    require(angle.isFinite()) { "angle must be finite, but was $angle" }
    if (distance == 0.0) return point.copy()
    val radLat = toRadians(point.lat)
    val radLng = toRadians(point.lng)
    val radAngle = toRadians(angle)
    val sinRadAngle = sin(radAngle)
    val cosRadAngle = cos(radAngle)
    val tanU1 = (1 - EARTH_FLATTENING) * tan(radLat)
    val cosU1 = 1 / sqrt((1 + tanU1 * tanU1))
    val sinU1 = tanU1 * cosU1
    val sigma1 = atan2(tanU1, cosRadAngle)
    val sinAlpha = cosU1 * sinRadAngle
    val cosSqAlpha = 1 - sinAlpha * sinAlpha
    val uSq = cosSqAlpha * (EARTH_RADIUS_LONG * EARTH_RADIUS_LONG - EARTH_RADIUS_SHORT * EARTH_RADIUS_SHORT) / (EARTH_RADIUS_SHORT * EARTH_RADIUS_SHORT)
    val A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)))
    val B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)))
    var sigma = distance / (EARTH_RADIUS_SHORT * A)
    var sinSigma: Double
    var cosSigma: Double
    var cos2SigmaM: Double
    var sigmaP: Double
    var iterations = 0
    var converged: Boolean
    do {
        cos2SigmaM = cos(2 * sigma1 + sigma)
        sinSigma = sin(sigma)
        cosSigma = cos(sigma)
        val deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)))
        sigmaP = sigma
        sigma = distance / (EARTH_RADIUS_SHORT * A) + deltaSigma
        iterations++
        converged = abs(sigma - sigmaP) <= VINCENTY_TOLERANCE
    } while (!converged && iterations < VINCENTY_MAX_ITERATIONS)
    check(converged) { "Vincenty direct formula failed to converge" }
    val x = sinU1 * sinSigma - cosU1 * cosSigma * cosRadAngle
    val lat2 = atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosRadAngle, (1 - EARTH_FLATTENING) * sqrt(sinAlpha * sinAlpha + x * x))
    val lambda = atan2(sinSigma * sinRadAngle, cosU1 * cosSigma - sinU1 * sinSigma * cosRadAngle)
    val C = EARTH_FLATTENING / 16 * cosSqAlpha * (4 + EARTH_FLATTENING * (4 - 3 * cosSqAlpha))
    val L = lambda - (1 - C) * EARTH_FLATTENING * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)))
    val lng2 = (radLng + L + 3 * PI) % (2 * PI) - PI
    return Coordinate(toDegrees(lat2), toDegrees(lng2))
}

/**
 * 在以指定坐标为圆心的圆形区域内生成均匀分布的随机坐标。
 *
 * @param point 圆心。
 * @param radius 半径，单位为米，必须为非负有限值。
 * @return 圆内的随机坐标。
 * @throws IllegalArgumentException 当参数无效时。
 */
fun getRandomPoint(point: Coordinate, radius: Double): Coordinate {
    require(radius.isFinite() && radius >= 0.0) {
        "radius must be finite and non-negative, but was $radius"
    }
    val randomAngle = Random.nextDouble(360.0)
    val randomDistance = sqrt(Random.nextDouble()) * radius
    return getPointByBasePoint(point, randomDistance, randomAngle)
}

/**
 * 获取轨迹上最接近指定坐标的线段索引。
 *
 * 使用点到线段两个端点的距离和作为排序指标，最多返回三个候选线段。
 *
 * @param point 待匹配坐标。
 * @param line 至少包含两个点的轨迹。
 * @param offset 候选线段相对最优线段允许增加的距离，单位为米。
 * @return 按距离升序排列的线段起点索引。
 * @throws IllegalArgumentException 当轨迹、偏差或坐标无效时。
 */
fun getNearPartsOnLine(point: Coordinate, line: Array<Coordinate>, offset: Float = 10f): Array<Int> {
    require(line.size >= 2) { "line must contain at least two points" }
    require(offset.isFinite() && offset >= 0.0f) {
        "offset must be finite and non-negative, but was $offset"
    }
    point.requireValid("point")
    line.forEachIndexed { index, coordinate -> coordinate.requireValid("line[$index]") }

    val candidates = ArrayList<Pair<Int, Double>>(3)
    var bestDistance = Double.POSITIVE_INFINITY
    var previousDistance = getHaversineDistance(point, line[0])
    for (index in 0 until line.lastIndex) {
        val currentDistance = getHaversineDistance(point, line[index + 1])
        val distance = previousDistance + currentDistance
        previousDistance = currentDistance

        if (distance < bestDistance) {
            bestDistance = distance
            candidates.removeAll { it.second > bestDistance + offset }
        }
        if (distance > bestDistance + offset) continue

        val insertionIndex = candidates.indexOfFirst { distance < it.second }
            .let { if (it < 0) candidates.size else it }
        candidates.add(insertionIndex, index to distance)
        if (candidates.size > 3) candidates.removeAt(candidates.lastIndex)
    }
    return Array(candidates.size) { candidates[it].first }
}

/**
 * 获取指定坐标在线段上的近似投影点。
 *
 * @param point 待投影坐标。
 * @param part 恰好包含起点和终点的线段。
 * @return 位于线段范围内的投影点；退化线段返回起点副本。
 * @throws IllegalArgumentException 当线段长度不为两个点或坐标无效时。
 */
fun getProjectionPointOnLinePart(point: Coordinate, part: Array<Coordinate>): Coordinate {
    require(part.size == 2) { "part must contain exactly two points" }
    val start = part[0]
    val end = part[1]
    point.requireValid("point")
    start.requireValid("part[0]")
    end.requireValid("part[1]")
    if (start == end) return start.copy()

    val p2s = getDistanceOn2Points(point, start)
    val p2e = getDistanceOn2Points(point, end)
    val s2e = getDistanceOn2Points(start, end)
    return if ((p2s - p2e) > s2e) end.copy()
    else if ((p2e - p2s) > s2e) start.copy()
    else getPointOn2Points(start, end, (p2s / (p2s + p2e)).toFloat())
}

/**
 * 获取指定坐标在轨迹候选线段上的投影点。
 *
 * @param point 待投影坐标。
 * @param line 至少包含两个点的轨迹。
 * @return 最接近候选线段上的投影点。
 * @throws IllegalArgumentException 当轨迹或坐标无效时。
 */
fun getProjectionPointOnLine(point: Coordinate, line: Array<Coordinate>): Coordinate {
    val nearParts = getNearPartsOnLine(point, line)
    return getProjectionPointOnLinePart(point, arrayOf(line[nearParts[0]], line[nearParts[0] + 1]))
}

/**
 * 返回目标轨迹起点至指定坐标投影点之间的轨迹。
 *
 * @param point 待投影坐标。
 * @param targetLine 至少包含两个点的目标轨迹。
 * @return 目标轨迹前缀以及末尾投影点。
 * @throws IllegalArgumentException 当目标轨迹或坐标无效时。
 */
fun getProjectionLineOnLineWithPoint(point: Coordinate, targetLine: Array<Coordinate>): Array<Coordinate> {
    // 获取位置点在轨迹上最靠近的几个线段
    val nearParts = getNearPartsOnLine(point, targetLine)
    val nearIndex = nearParts[0]
    val projectionPoint = getProjectionPointOnLinePart(point, arrayOf(targetLine[nearIndex], targetLine[nearIndex + 1]))
    return targetLine.copyOfRange(0, nearIndex + 1).plus(projectionPoint)
}

/**
 * 将一条运动轨迹的最新位置投影到目标轨迹，并返回目标轨迹前缀。
 *
 * @param line 待投影运动轨迹；空数组返回目标轨迹首点。
 * @param targetLine 至少包含两个点的目标轨迹。
 * @param offset 候选线段相对最优线段允许增加的距离，单位为米。
 * @return 目标轨迹前缀以及末尾投影点。
 * @throws IllegalArgumentException 当目标轨迹、偏差或坐标无效时。
 */
fun getProjectionLineOnLineWithLine(line: Array<Coordinate>, targetLine: Array<Coordinate>, offset: Float = 10f): Array<Coordinate> {
    require(targetLine.size >= 2) { "targetLine must contain at least two points" }
    require(offset.isFinite() && offset >= 0.0f) {
        "offset must be finite and non-negative, but was $offset"
    }
    targetLine.forEachIndexed { index, point -> point.requireValid("targetLine[$index]") }
    if (line.isEmpty()) return arrayOf(targetLine[0].copy())
    val compressLine = compressTrack(line)
    val newPoint = compressLine[compressLine.size - 1]
    val nearParts = getNearPartsOnLine(newPoint, targetLine, offset)
    val nearIndex = if (compressLine.size == 1 || nearParts.size == 1) 0
    else {
        val lastPoint = compressLine[compressLine.size - 2]
        val newAngle = getAngleOn2Points(lastPoint, newPoint)
        val nearAngles = nearParts.map { getAngleOn2Points(targetLine[it], targetLine[it + 1]) }
        val nearAngle = nearAngles.minBy { angleDifference(it, newAngle) }
        nearAngles.indexOf(nearAngle)
    }
    val part = arrayOf(targetLine[nearParts[nearIndex]], targetLine[nearParts[nearIndex] + 1])
    val projectionPoint = getProjectionPointOnLinePart(newPoint, part)
    return targetLine.copyOfRange(0, nearParts[nearIndex] + 1).plus(projectionPoint)
}
