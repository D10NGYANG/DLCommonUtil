# Int

> 整型数据处理工具

## ① 转换为字节数组
> 将 无符号整型 转 N 个字节的 ByteArray；
> > 字节数设置为小于等于0，则根据整型值自动计算字节数（默认设置为0）; \
> > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
> - @receiver [Int] 整型
> - @param size [Int] 字节数，默认为0
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
byte[] byteArray = IntUtilsKt.toByteArray(0xF1FF, 0);
// 设置为3，[0x00, 0xF1, 0xFF]
byte[] byteArray = IntUtilsKt.toByteArray(0xF1FF, 3);
```
》js:
> 请查看 [Number](./Number.md) 中的 `numberToByteArray` 方法