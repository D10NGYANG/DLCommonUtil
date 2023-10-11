# Float

> 浮点数处理工具

## ① 转字符串并最大保留指定位数的小数
> 将 Float 类型转字符串并最大保留指定位数的小数
> - 该方法不会对数值进行四舍五入；
> - 如果数值为整数，则直接返回原字符串；
> - 如果数值中小数位数小于指定位数，则直接返回原字符串；

》kotlin:
```kotlin
// 保留2位小数，"3.14"
val str = 3.1415926f.toString(2)
```
》java:
```java
// 保留2位小数，"3.14"
String str = FloatUtilsKt.toString(3.1415926f, 2);
```
》js:
> 请查看 [Number](./Number.md) 中的 `numberToString` 方法