# Double
> 双精度浮点数处理工具

## ① 转字符串并最大保留指定位数的小数
> 将 Double 类型转字符串并最大保留指定位数的小数
> - 该方法不会对字符串进行四舍五入，只是简单的截取；
> - 如果字符串中没有小数点，则直接返回原字符串；
> - 如果字符串中小数位数小于指定位数，则直接返回原字符串；

》kotlin:
```kotlin
// 保留2位小数，"3.14"
val str = 3.1415926f.toString(2)
```
》java:
```java
// 保留2位小数，"3.14"
String str = DoubleUtilsKt.toString(3.1415926f, 2);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
// 保留2位小数，"3.14"
const str = DLCommon.toStringByDouble(3.1415926, 2);
```