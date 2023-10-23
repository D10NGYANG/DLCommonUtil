# Coordinate
> 经纬度坐标处理工具

## ① 坐标系转换
> 由于不同的地图使用的坐标系不同，所以需要进行坐标系转换，目前支持的坐标系转换有：
> > WGS84、GCJ02、BD09
> - @receiver [Coordinate] 源坐标
> - @param from [CoordinateSystemType] 源坐标系
> - @param to [CoordinateSystemType] 目标坐标系
> - @return [Coordinate] 转换后坐标

》kotlin:
```kotlin
// WGS84 -> GCJ02
val point = Coordinate(39.908860, 116.397390).convert(CoordinateSystemType.WGS84, CoordinateSystemType.GCJ02)
```
》java:
```java
// WGS84 -> GCJ02
Coordinate point = CoordinateUtils.convert(new Coordinate(39.908860, 116.397390), CoordinateSystemType.WGS84, CoordinateSystemType.GCJ02);
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const Coordinate = DLCoordinate.Coordinate;
const CoordinateSystemType = DLCoordinate.CoordinateSystemType;
// WGS84 -> GCJ02
const point = DLCoordinate.convert(new Coordinate(39.908860, 116.397390), CoordinateSystemType.WGS84, CoordinateSystemType.GCJ02);
```

## ② 判断经度数据是否为东经
> - @receiver [Double] 经度数据
> - @return [Boolean] true: 东经；false: 西经

》kotlin:
```kotlin
var res = 116.397390.isEastLongitude() // true
res = -116.397390.isEastLongitude() // false
res = 230.397390.isEastLongitude() // false
```
》java:
```java
boolean res = CoordinateUtilsKt.isEastLongitude(116.397390); // true
res = CoordinateUtilsKt.isEastLongitude(-116.397390); // false
res = CoordinateUtilsKt.isEastLongitude(230.397390); // false
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.isEastLongitude(116.397390); // true
res = DLCoordinate.isEastLongitude(-116.397390); // false
res = DLCoordinate.isEastLongitude(230.397390); // false
```

## ③ 判断纬度数据是否为北纬
> - @receiver [Double] 纬度数据
> - @return [Boolean] true: 北纬；false: 南纬

》kotlin:
```kotlin
var res = 39.908860.isNorthLatitude() // true
res = -39.908860.isNorthLatitude() // false
res = 130.908860.isNorthLatitude() // false
```
》java:
```java
boolean res = CoordinateUtilsKt.isNorthLatitude(39.908860); // true
res = CoordinateUtilsKt.isNorthLatitude(-39.908860); // false
res = CoordinateUtilsKt.isNorthLatitude(130.908860); // false
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.isNorthLatitude(39.908860); // true
res = DLCoordinate.isNorthLatitude(-39.908860); // false
res = DLCoordinate.isNorthLatitude(130.908860); // false
```

## ④ 将经度转换成不带东西经标记的数据
> > 当数据大于180度或者小于0度表示西经
> - @receiver [Double] 经度数据
> - @return [Double] 返回去除"-"或小于180的数值；eg：输入181.1，返回1.1; 输入-1.1返回1.1;

》kotlin:
```kotlin
var res = 181.1.toLongitudeNoPre() // 1.1
res = -1.1.toLongitudeNoPre() // 1.1
res = 179.1.toLongitudeNoPre() // 179.1
```
》java:
```java
double res = CoordinateUtilsKt.toLongitudeNoPre(181.1); // 1.1
res = CoordinateUtilsKt.toLongitudeNoPre(-1.1); // 1.1
res = CoordinateUtilsKt.toLongitudeNoPre(179.1); // 179.1
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.toLongitudeNoPre(181.1); // 1.1
res = DLCoordinate.toLongitudeNoPre(-1.1); // 1.1
res = DLCoordinate.toLongitudeNoPre(179.1); // 179.1
```

## ⑤ 将纬度转换成不带南北纬标记的数据
> > 当数据大于90度或者小于0度表示南纬
> - @receiver [Double] 纬度数据
> - @return [Double] 返回去除"-"或小于90的数值；eg：输入91.1，返回1.1; 输入-1.1返回1.1;

》kotlin:
```kotlin
var res = 91.1.toLatitudeNoPre() // 1.1
res = -1.1.toLatitudeNoPre() // 1.1
res = 89.1.toLatitudeNoPre() // 89.1
```
》java:
```java
double res = CoordinateUtilsKt.toLatitudeNoPre(91.1); // 1.1
res = CoordinateUtilsKt.toLatitudeNoPre(-1.1); // 1.1
res = CoordinateUtilsKt.toLatitudeNoPre(89.1); // 89.1
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.toLatitudeNoPre(91.1); // 1.1
res = DLCoordinate.toLatitudeNoPre(-1.1); // 1.1
res = DLCoordinate.toLatitudeNoPre(89.1); // 89.1
```

## ⑥ 将经度转换成带东西经标记的数据
> > eg: 输入经度=110.1，isEast=false，isPositive=false，输出经度=-110.1 \
> > eg: 输入经度=110.1，isEast=false，isPositive=true，输出经度=249.9
> - @receiver Double
> - @param isEast [Boolean] 是否为东经
> - @param isPositive [Boolean] 输出数据是否需要为正值，默认true，如果isEast为false表示西经，则输出的值根据此参数决定正负，如果isEast为true表示东经，则此参数无效，输出的值都为正值
> - @return [Double] 输出经度

》kotlin:
```kotlin
var res = 110.1.toFullLongitude(true) // 110.1
res = 110.1.toFullLongitude(false, false) // -110.1
res = 110.1.toFullLongitude(false, true) // 249.9
```
》java:
```java
double res = CoordinateUtilsKt.toFullLongitude(110.1, true, true); // 110.1
res = CoordinateUtilsKt.toFullLongitude(110.1, false, false); // -110.1
res = CoordinateUtilsKt.toFullLongitude(110.1, false, true); // 249.9
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.toFullLongitude(110.1, true); // 110.1
res = DLCoordinate.toFullLongitude(110.1, false, false); // -110.1
res = DLCoordinate.toFullLongitude(110.1, false, true); // 249.9
```

## ⑦ 将纬度转换成带南北纬标记的数据
> > eg: 输入纬度=10.1，isNorth=false，isPositive=false，输出纬度=-10.1 \
> > eg: 输入纬度=10.1，isNorth=false，isPositive=true，输出纬度=79.9
> - @receiver Double
> - @param isNorth [Boolean] 是否为北纬
> - @param isPositive [Boolean] 输出数据是否需要为正值，默认true，如果isNorth为false表示南纬，则输出的值根据此参数决定正负，如果isNorth为true表示北纬，则此参数无效，输出的值都为正值
> - @return [Double] 输出纬度

》kotlin:
```kotlin
var res = 10.1.toFullLatitude(true) // 10.1
res = 10.1.toFullLatitude(false, false) // -10.1
res = 10.1.toFullLatitude(false, true) // 79.9
```
》java:
```java
double res = CoordinateUtilsKt.toFullLatitude(10.1, true, true); // 10.1
res = CoordinateUtilsKt.toFullLatitude(10.1, false, false); // -10.1
res = CoordinateUtilsKt.toFullLatitude(10.1, false, true); // 79.9
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
let res = DLCoordinate.toFullLatitude(10.1, true); // 10.1
res = DLCoordinate.toFullLatitude(10.1, false, false); // -10.1
res = DLCoordinate.toFullLatitude(10.1, false, true); // 79.9
```

## ⑧ 将经纬度数据转换成度分秒值
> - @receiver [Double] 经纬度数据
> - @param isLongitude [Boolean] 是否为经度数据，true为经度，false为纬度
> - @return [DMS] 度分秒值

》kotlin:
```kotlin
val res = 45.234.toDMS(true) // DMS(degrees=45, minutes=14, seconds=2.4)
```
》java:
```java
DMS res = CoordinateUtilsKt.toDMS(45.234, true); // DMS(degrees=45, minutes=14, seconds=2.4)
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const DMS = DLCoordinate.DMS;
const res = DLCoordinate.toDMS(45.234, true); // DMS(degrees=45, minutes=14, seconds=2.4)
```

## ⑨ 将度分秒值转换成经纬度数据
> - @receiver [DMS] 度分秒值
> - @return [Double] 经纬度数据

》kotlin:
```kotlin
val res = DMS(45, 14, 2.4f).toLatLng() // 45.234
```
》java:
```java
double res = CoordinateUtilsKt.toLatLng(new DMS(45, 14, 2.4f)); // 45.234
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const DMS = DLCoordinate.DMS;
const res = DLCoordinate.toLatLng(new DMS(45, 14, 2.4)); // 45.234
```

## ⑩ 将经纬度转经纬度字符串
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，东经、西经、北纬、南纬
> - - - -
> - @receiver [Double] eg: 103.5863933
> - @param isLongitude [Boolean] 是否为经度
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [String] eg: 东经 E103°35′11.02″

》kotlin:
```kotlin
val res = 103.5863933.toLatLngString(true, "CH Fd°m′S.ss″") // 东经 E103°35′11.02″
```
》java:
```java
String res = CoordinateUtilsKt.toLatLngString(103.5863933, true, "CH Fd°m′S.ss″"); // 东经 E103°35′11.02″
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLatLngString(103.5863933, true, "CH Fd°m′S.ss″"); // 东经 E103°35′11.02″
```

## ⑪ 将经度转经度字符串
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，东经、西经
> - - - -
> - @receiver [Double] eg: 103.5863933
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [String] eg: 东经 E103°35′11.02″

》kotlin:
```kotlin
val res = 103.5863933.toLongitudeString("CH Fd°m′S.ss″") // 东经 E103°35′11.02″
```
》java:
```java
String res = CoordinateUtilsKt.toLongitudeString(103.5863933, "CH Fd°m′S.ss″"); // 东经 E103°35′11.02″
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLongitudeString(103.5863933, "CH Fd°m′S.ss″"); // 东经 E103°35′11.02″
```

## ⑫ 将纬度转纬度字符串
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，北纬、南纬
> - - - -
> - @receiver [Double] eg: 29.73784595
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [String] eg: 北纬 N29°44′16.25″

》kotlin:
```kotlin
val res = 29.73784595.toLatitudeString("CH Fd°m′S.ss″") // 北纬 N29°44′16.25″
```
》java:
```java
String res = CoordinateUtilsKt.toLatitudeString(29.73784595, "CH Fd°m′S.ss″"); // 北纬 N29°44′16.25″
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLatitudeString(29.73784595, "CH Fd°m′S.ss″"); // 北纬 N29°44′16.25″
```

## ⑬ 将经纬度字符串转经纬度
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，东经、西经、北纬、南纬
> - - - -
> - @receiver [String] eg: 东经 E103°35′11.02″
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [Double] eg: 103.5863933

》kotlin:
```kotlin
val res = "东经 E103°35′11.02″".toLatLng("CH Fd°m′S.ss″") // 103.5863933
```
》java:
```java
double res = CoordinateUtilsKt.toLatLng("东经 E103°35′11.02″", "CH Fd°m′S.ss″"); // 103.5863933
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLatLngByString("东经 E103°35′11.02″", "CH Fd°m′S.ss″"); // 103.5863933
```

## ⑭ 将经度字符串转经度
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，东经、西经
> - - - -
> - @receiver [String] eg: 东经 E103°35′11.02″
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [Double] eg: 103.5863933

》kotlin:
```kotlin
val res = "东经 E103°35′11.02″".toLongitude("CH Fd°m′S.ss″") // 103.5863933
```
》java:
```java
double res = CoordinateUtilsKt.toLongitude("东经 E103°35′11.02″", "CH Fd°m′S.ss″"); // 103.5863933
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLongitude("东经 E103°35′11.02″", "CH Fd°m′S.ss″"); // 103.5863933
```

## ⑮ 将纬度字符串转纬度
> - d: 度
> - m: 分
> - S: 秒的整数部分
> - s: 秒的小数部分
> - F: 英文方向，E、W、N、S
> - CH: 中文方向，北纬、南纬
> - - - -
> - @receiver [String] eg: 北纬 N29°44′16.25″
> - @param pattern [String] eg: "CH Fd°m′S.ss″"
> - @return [Double] eg: 29.73784595

》kotlin:
```kotlin
val res = "北纬 N29°44′16.25″".toLatitude("CH Fd°m′S.ss″") // 29.73784595
```
》java:
```java
double res = CoordinateUtilsKt.toLatitude("北纬 N29°44′16.25″", "CH Fd°m′S.ss″"); // 29.73784595
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.toLatitude("北纬 N29°44′16.25″", "CH Fd°m′S.ss″"); // 29.73784595
```

## ⑯ 将ddmm.mmmm格式的经纬度转换成dd.dddddd格式的经纬度
> > 北斗协议里的专用转换
> - @receiver [Double] eg: 11301.8789
> - @return [Double] eg: 113.03131499999999

》kotlin:
```kotlin
val res = 11301.8789.ddmmpmmmm2LatLng() // 113.03131499999999
```
》java:
```java
double res = CoordinateUtilsKt.ddmmpmmmm2LatLng(11301.8789); // 113.03131499999999
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.ddmmpmmmm2LatLng(11301.8789); // 113.03131499999999
```

## ⑰ 将dd.dddddd格式的经纬度转换成ddmm.mmmm格式的经纬度
> > 北斗协议里的专用转换
> - @receiver [Double] eg: 113.03131
> - @return [Double] eg: 11301.8789

》kotlin:
```kotlin
val res = 113.03131.latLng2ddmmpmmmm() // 11301.8789
```
》java:
```java
double res = CoordinateUtilsKt.latLng2ddmmpmmmm(113.03131); // 11301.8789
```
》js:
```js
const DLCoordinate = require('dl-common-util').com.d10ng.common.coordinate;
const res = DLCoordinate.latLng2ddmmpmmmm(113.03131); // 11301.8789
```