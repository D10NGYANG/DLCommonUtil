# Track
> 经纬度计算、轨迹算法工具

## ① 获取两点间的距离和方位角
> > 使用Vincenty算法，参考：http://www.movable-type.co.uk/scripts/latlong-vincenty.html
> - @param point1 [Coordinate] 起点
> - @param point2 [Coordinate] 终点
> - @return [DistanceAndBearing] 距离和方位角，单位为米和度

》kotlin:
```kotlin
val res = getDistanceAndBearing(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211))
// DistanceAndBearing(distance=1379.4438755465214, initialBearing=67.26721057457084, finalBearing=67.27675731742102)
```
》java:
```java
DistanceAndBearing res = TrackUtils.getDistanceAndBearing(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211));
// DistanceAndBearing(distance=1379.4438755465214, initialBearing=67.26721057457084, finalBearing=67.27675731742102)
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getDistanceAndBearing(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211));
// DistanceAndBearing(distance=1379.4438755465214, initialBearing=67.26721057457084, finalBearing=67.27675731742102)
```

## ② 获取两点间的距离
> 计算两点之间的距离，单位为米。
> - @param point1 [Coordinate] 起点
> - @param point2 [Coordinate] 终点
> - @param highPrecision [Boolean] 是否使用高精度计算，默认为false，高精度计算会使用Vincenty算法，低精度计算会使用Haversine算法
> - @return [Double] 距离，单位为米

》kotlin:
```kotlin
var res = getDistanceOn2Points(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211))
// 使用高精度计算
res = getDistanceOn2Points(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211), true)
```
》java:
```java
double res = TrackUtils.getDistanceOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), false);
// 使用高精度计算
res = TrackUtils.getDistanceOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), true);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
let res = DLCoordinate.getDistanceOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), false);
// 使用高精度计算
res = DLCoordinate.getDistanceOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), true);
```

## ③ 判断点是否在圆内
> - @param point [Coordinate] 待判断坐标点
> - @param center [Coordinate] 圆圈中心点
> - @param radius [Double] 圆圈半径
> - @return [Boolean] 是否在圆内，true为在圆内，false为不在圆内

》kotlin:
```kotlin
val res = isPointInCircle(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211), 1000.0)
```
》java:
```java
boolean res = TrackUtils.isPointInCircle(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), 1000.0);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.isPointInCircle(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), 1000.0);
```

## ④ 计算两个坐标点之间的夹角
> - @param point1 [Coordinate] 起点
> - @param point2 [Coordinate] 终点
> - @param highPrecision [Boolean] 是否使用高精度计算，默认为false，高精度计算会使用Vincenty算法，低精度计算会使用Haversine算法
> - @return [Double] 夹角，单位度

》kotlin:
```kotlin
var res = getAngleOn2Points(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211))
// 使用高精度计算
res = getAngleOn2Points(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211), true)
```
》java:
```java
double res = TrackUtils.getAngleOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), false);
// 使用高精度计算
res = TrackUtils.getAngleOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), true);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
let res = DLCoordinate.getAngleOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), false);
// 使用高精度计算
res = DLCoordinate.getAngleOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), true);
```

## ⑤ 从两个坐标间根据百分比获取中间坐标
> - @param point1 [Coordinate] 起点
> - @param point2 [Coordinate] 终点
> - @param present [Float] 百分比 0.0～1.0
> - @return [Coordinate] 中间坐标

》kotlin:
```kotlin
val res = getPointOn2Points(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211), 0.5f)
```
》java:
```java
Coordinate res = TrackUtils.getPointOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), 0.5f);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getPointOn2Points(new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211), 0.5);
```

## ⑥ 计算一段轨迹的总距离
> - @param points [Array<Coordinate>] 轨迹
> - @return [Double] 总距离，单位为米

》kotlin:
```kotlin
val res = getDistanceOnTrack(arrayOf(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211)))
```
》java:
```java
double res = TrackUtils.getDistanceOnTrack(new Coordinate[]{new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)});
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getDistanceOnTrack([new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)]);
```

## ⑦ 从一段轨迹中获取间隔指定距离的坐标点重新组成的轨迹
> - @param points [Array<Coordinate>] 原始轨迹
> - @param distance [Double] 间隔距离，单位为米
> - @return [Array<Coordinate>] 新的轨迹

》kotlin:
```kotlin
val res = getPointsOnDistance(arrayOf(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211)), 100.0)
```
》java:
```java
Coordinate[] res = TrackUtils.getPointsOnDistance(new Coordinate[]{new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)}, 100.0);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getPointsOnDistance([new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)], 100.0);
```

## ⑧ 压缩轨迹
> > 压缩原理为，对于近乎在一条直线上的点进行合并，减少对整体轨迹效果显示作用不大的位置点。
> - @param points [Array<Coordinate>] 原始轨迹
> - @return [Array<Coordinate>] 新的轨迹

》kotlin:
```kotlin
val res = compressTrack(arrayOf(Coordinate(39.9075, 116.39723), Coordinate(39.9123, 116.41211)))
```
》java:
```java
Coordinate[] res = TrackUtils.compressTrack(new Coordinate[]{new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)});
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.compressTrack([new Coordinate(39.9075, 116.39723), new Coordinate(39.9123, 116.41211)]);
```

## ⑨ 根据距离与角度从一个坐标点获取另一个坐标点
> > 使用Vincenty direct算法，参考：http://www.movable-type.co.uk/scripts/latlong-vincenty.html
> - @param point [Coordinate] 基准点
> - @param distance [Double] 距离，单位为米
> - @param angle [Double] 角度，单位为弧度
> - @return [Coordinate] 新的坐标点

》kotlin:
```kotlin
val res = getPointByBasePoint(Coordinate(39.9075, 116.39723), 100.0, 45.0)
```
》java:
```java
Coordinate res = TrackUtils.getPointByBasePoint(new Coordinate(39.9075, 116.39723), 100.0, 45.0);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getPointByBasePoint(new Coordinate(39.9075, 116.39723), 100.0, 45.0);
```

## ⑩ 基于一个中心点的半径范围获取一个随机坐标点
> - @param point [Coordinate] 中心点
> - @param radius [Double] 半径
> - @return [Coordinate] 随机坐标点

》kotlin:
```kotlin
val res = getRandomPoint(Coordinate(39.9075, 116.39723), 100.0)
```
》java:
```java
Coordinate res = TrackUtils.getRandomPoint(new Coordinate(39.9075, 116.39723), 100.0);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const res = DLCoordinate.getRandomPoint(new Coordinate(39.9075, 116.39723), 100.0);
```

## ⑪ 获取一个位置点在一条轨迹上最靠近的几个线段
> > 获取一个位置点在一条轨迹上最靠近的几个线段，即这个位置点最有可能吸附到的几个线段，最少会有一个线段，最多会有三个线段
> - @param point [Coordinate] 位置点
> - @param line [Array<Coordinate>] 轨迹，至少两个点，最好是换算成相同间隔的轨迹（如间隔100米一个点），可以使用函数getPointsOnDistance进行换算
> - @param offset [Float] 误差范围，单位为米，即返回的最靠近的线段列表中第一个线段的距离和最后一个线段的距离之差不能超过这个值
> - @return [Array<Int>] 最靠近的线段的索引列表，索引从0开始，假设返回的是[0, 1, 2]，则表示最靠近的线段是line[0]～line[1]，次靠近的线段是line[1]～line[2]，然后线段是line[2]～line[3]

》kotlin:
```kotlin
val points = arrayOf(
    Coordinate(39.906310, 116.391370),
    Coordinate(39.906490, 116.400470),
    Coordinate(39.913820, 116.400400),
    Coordinate(39.914110, 116.404970),
    Coordinate(39.914110, 116.407810),
    Coordinate(39.909230, 116.407550),
)
val result = getNearPartsOnLine(Coordinate(39.912570, 116.403880), points, 1000f)
```
》java:
```java
Coordinate[] points = new Coordinate[]{
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
};
int[] result = TrackUtils.getNearPartsOnLine(new Coordinate(39.912570, 116.403880), points, 1000f);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const points = [
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
];
const result = DLCoordinate.getNearPartsOnLine(new Coordinate(39.912570, 116.403880), points, 1000);
```

## ⑫ 获取一个位置点在一条线段上的投影点
> - @param point [Coordinate] 位置点
> - @param part [Array<Coordinate>] 线段，必须是两个点
> - @return [Coordinate] 投影点

》kotlin:
```kotlin
val points = arrayOf(
    Coordinate(39.913820, 116.400400),
    Coordinate(39.914110, 116.404970),
)
val result = getProjectionPointOnLinePart(Coordinate(39.912570, 116.403880), points)
```
》java:
```java
Coordinate[] points = new Coordinate[]{
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
};
Coordinate result = TrackUtils.getProjectionPointOnLinePart(new Coordinate(39.912570, 116.403880), points);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const points = [
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
];
const result = DLCoordinate.getProjectionPointOnLinePart(new Coordinate(39.912570, 116.403880), points);
```

## ⑬ 获取一个位置点在一条轨迹上的投影点
> - @param point [Coordinate] 位置点
> - @param line [Array<Coordinate>] 轨迹，至少两个点，最好是换算成相同间隔的轨迹（如间隔100米一个点），可以使用函数getPointsOnDistance进行换算
> - @return [Coordinate] 投影点

》kotlin:
```kotlin
val points = arrayOf(
    Coordinate(39.906310, 116.391370),
    Coordinate(39.906490, 116.400470),
    Coordinate(39.913820, 116.400400),
    Coordinate(39.914110, 116.404970),
    Coordinate(39.914110, 116.407810),
    Coordinate(39.909230, 116.407550),
)
val result = getProjectionPointOnLine(Coordinate(39.912570, 116.403880), points)
```
》java:
```java
Coordinate[] points = new Coordinate[]{
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
};
Coordinate result = TrackUtils.getProjectionPointOnLine(new Coordinate(39.912570, 116.403880), points);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const points = [
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
];
const result = DLCoordinate.getProjectionPointOnLine(new Coordinate(39.912570, 116.403880), points);
```

## ⑭ 获取一个位置点在一条轨迹上的投影线，从起点开始算
> > 目前适用于马拉松比赛中的收尾车的轨迹拟合到活动线路来
> - @param point [Coordinate] 位置点
> - @param targetLine [Array<Coordinate>] 目标线路，最好是换算成相同间隔的轨迹（如间隔100米一个点），可以使用函数getPointsOnDistance进行换算
> - @return [Array<Coordinate>] 投影线

》kotlin:
```kotlin
val points = arrayOf(
    Coordinate(39.906310, 116.391370),
    Coordinate(39.906490, 116.400470),
    Coordinate(39.913820, 116.400400),
    Coordinate(39.914110, 116.404970),
    Coordinate(39.914110, 116.407810),
    Coordinate(39.909230, 116.407550),
)
val result = getProjectionLineOnLineWithPoint(Coordinate(39.912570, 116.403880), points)
```
》java:
```java
Coordinate[] points = new Coordinate[]{
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
};
Coordinate[] result = TrackUtils.getProjectionLineOnLineWithPoint(new Coordinate(39.912570, 116.403880), points);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const points = [
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
];
const result = DLCoordinate.getProjectionLineOnLineWithPoint(new Coordinate(39.912570, 116.403880), points);
```

## ⑮ 获取线路在另一条线路上的投影线，从起点开始算
> > 目前适用于马拉松比赛中的收尾车的轨迹拟合到活动线路来
> - @param line [Array<Coordinate>] 线路
> - @param targetLine [Array<Coordinate>] 目标线路，起码有两个点，最好是换算成相同间隔的轨迹（如间隔100米一个点），可以使用函数getPointsOnDistance进行换算
> - @param offset [Float] 误差范围，单位为米，计算的最靠近的线段列表中第一个线段的距离和最后一个线段的距离之差不能超过这个值
> - @return [Array<Coordinate>] 投影线

》kotlin:
```kotlin
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
```
》java:
```java
Coordinate[] points = new Coordinate[]{
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
};
Coordinate[] line = new Coordinate[]{
    new Coordinate(39.912570, 116.403880),
};
Coordinate[] result = TrackUtils.getProjectionLineOnLineWithLine(line, points);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const points = [
    new Coordinate(39.906310, 116.391370),
    new Coordinate(39.906490, 116.400470),
    new Coordinate(39.913820, 116.400400),
    new Coordinate(39.914110, 116.404970),
    new Coordinate(39.914110, 116.407810),
    new Coordinate(39.909230, 116.407550),
];
const line = [
    new Coordinate(39.912570, 116.403880),
];
const result = DLCoordinate.getProjectionLineOnLineWithLine(line, points);
```
