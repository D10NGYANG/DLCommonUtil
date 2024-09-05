# Double
> 双精度浮点数处理工具

## ① 转字符串并最大保留指定位数的小数
> 将 Double 类型转字符串并最大保留指定位数的小数
> > - 该方法不会对数值进行四舍五入；
> > - 如果数值为整数，则直接返回原字符串；
> > - 如果数值中小数位数小于指定位数，则直接返回原字符串；
> - @receiver [Double] 浮点数，如 1.2345
> - @param maxDecimalCount [Int] 最大保留小数位数，不能小于0
> - @return [String] 字符串，如 "1.23"

》kotlin:
```kotlin
// 保留2位小数，"3.14"
val str = 3.1415926.toString(2)
```
》java:
```java
// 保留2位小数，"3.14"
String str = DoubleUtilsKt.toString(3.1415926, 2);
```
》js:
> 请查看 [Number](./Number.md) 中的 `numberToString` 方法

## ② 将Double转换为字节数组
> 将 Double 类型转为 字节数组
> - @receiver [Double] 双精度浮点数
> - @return [ByteArray] 长度为8的字节数组

》kotlin:
```kotlin
val bytes = 3.1415926.toByteArray()
```
》java:
```java
byte[] bytes = DoubleUtilsKt.toByteArray(3.1415926);
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const bytes = DLBase.doubleToByteArray(3.1415926);
```

## ③ 将字节数组转换为Double
> 将 字节数组 转为 Double
> - @receiver [ByteArray] 长度为8的字节数组
> - @return [Double] 双精度浮点数

》kotlin:
```kotlin
val value = bytes.toDouble()
```
》java:
```java
double value = DoubleUtilsKt.toDouble(bytes);
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const value = DLBase.byteArrayToDouble(bytes);
```