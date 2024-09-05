# Short

> 短整型数据处理工具

## ① 将Short转换为字节数组
> 将 短整型 转 N 个字节的 ByteArray
> > - 字节数设置为null（默认），则根据整型值自动计算字节数；
> > - 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
> - @receiver [Short] 短整型
> - @param size [Int]? 字节数，默认为null，根据整型值自动计算字节数
> - @return [ByteArray] 字节数组

》kotlin:
```kotlin
// 默认为0，自动计算字节数，[0xF1, 0xFF]
val byteArray = 0xF1FF.toByteArray()
// 设置为3，[0x00, 0xF1, 0xFF]
val byteArray = 0xF1FF.toByteArray(3)
```
》java:
```java
// 默认为0，自动计算字节数，[0xF1, 0xFF]
byte[] byteArray = ShortUtilsKt.toByteArray(0xF1FF, 0);
// 设置为3，[0x00, 0xF1, 0xFF]
byte[] byteArray = ShortUtilsKt.toByteArray(0xF1FF, 3);
```
》js:
> 请查看 [Number](./Number.md) 中的 `numberToByteArray` 方法

## ② 将字节数组转换成短整型
> 将 字节数组 转换成 短整型
> - @receiver [ByteArray] 最大长度为2的字节数组
> - @return [Short] 短整型

》kotlin:
```kotlin
// [0x0E, 0x7D]
val value = byteArrayOf(0x0E, 0x7D).toShort()
```
》java:
```java
// [0x0E, 0x7D]
int value = ShortUtilsKt.toShort(new byte[]{(byte) 0x0E, (byte) 0x7D});
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const value = DLBase.byteArrayToShort([0xfc, 0x13]);
```