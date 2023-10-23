# Color
> 颜色工具

## ① 判断颜色是否为深色
> > 通过颜色字符串 如“#FFFF00” 判断颜色是否为深色。
> - @param color [String] 颜色值，如#45216B
> - @return [Boolean] true为深色，false为浅色

》kotlin:
```kotlin
val isDark = isDarkColor("#45216B") // true
```
》java:
```java
boolean isDark = ColorUtilsKt.isDarkColor("#45216B"); // true
```
》js:
```js
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const isDark = DLCalculate.isDarkColor("#45216B"); // true
```

## ② 获取颜色值的相关色
> > 获取颜色的下一个等级颜色，或深色或浅色。
> - @param color [String] 颜色值，如#45216B
> - @param level [Double] -1.0～1.0，当为-1.0时，返回纯黑色，当为1.0时，返回纯白色
> - @return [String] 颜色值，如#45216B

》kotlin:
```kotlin
val color = getNextColor("#45216B", 0.5) // #a290b5
```
》java:
```java
String color = ColorUtilsKt.getNextColor("#45216B", 0.5); // #a290b5
```
》js:
```js
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const color = DLCalculate.getNextColor("#45216B", 0.5); // #a290b5
```

## ③ 从两个颜色间根据比例获得中间颜色
> - @param color1 [String] 16进制颜色值，如#000000、#000
> - @param color2 [String] 16进制颜色值，如#000000、#000
> - @param present [Float] 比例，0-1
> - @return [String] 16进制颜色值，如#000000、#000

》kotlin:
```kotlin
val color = getMiddleColor("#45216B", "#FFFFFF", 0.5) // #a290b5
```
》java:
```java
String color = ColorUtilsKt.getMiddleColor("#45216B", "#FFFFFF", 0.5); // #a290b5
```
》js:
```js
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const color = DLCalculate.getMiddleColor("#45216B", "#FFFFFF", 0.5); // #a290b5
```

## ④ 获取颜色的红绿蓝值
> > 从16进制颜色字符串值中提取红绿蓝的值，每个值范围为0-255。
> - @param str [String] 16进制颜色值，如#000000、#000
> - @return [Array<Int>] [red, green, blue]，如果不是16进制颜色值则返回[0, 0, 0]，每个颜色值范围为0-255

》kotlin:
```kotlin
val rgb = getRgbValueArrayFromHexColorString("#45216B") // [69, 33, 107]
```
》java:
```java
int[] rgb = ColorUtilsKt.getRgbValueArrayFromHexColorString("#45216B"); // [69, 33, 107]
```
》js:
```js
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const rgb = DLCalculate.getRgbValueArrayFromHexColorString("#45216B"); // [69, 33, 107]
```

## ⑤ 从红绿蓝的值获取16进制颜色值
> > 将 [red, green, blue] 数组的颜色值转换为16进制颜色值 #45216B。
> - @param rgb [Array<Int>] [red, green, blue]，每个颜色值范围为0-255
> - @return [String] 16进制颜色值，如#000000

》kotlin:
```kotlin
val color = getHexColorStringFromRgbValueArray(arrayOf(69, 33, 107)) // #45216B
```
》java:
```java
String color = ColorUtilsKt.getHexColorStringFromRgbValueArray(new int[]{69, 33, 107}); // #45216B
```
》js:
```js
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const color = DLCalculate.getHexColorStringFromRgbValueArray([69, 33, 107]); // #45216B
```