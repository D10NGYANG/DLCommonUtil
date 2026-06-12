# ByteBuffer
> 字节缓冲区处理工具类

## ① 获取指定范围的比特位组成新的字节数组
> > 从 ByteBuffer 中获取指定范围的 bit 组成新的 ByteArray。如从 0x53 中获取 3..6 位，返回 0x09。
> - @receiver [ByteBuffer] 原始字节数组
> - @param start [Int] 开始比特位置
> - @param length [Int] 比特位长度
> - @param paddingHigh [Boolean] 是否向高位补 0，默认为 true；false 表示向低位补 0
> - @return [ByteArray] 新的字节数组

》kotlin:
```kotlin
// 从 0x53 中获取 3..6 位，返回 0x09
val buf = ByteBuffer.wrap(byteArrayOf(0x53.toByte()))
val bytes = buf.getBitRange(3, 4)
val lowPaddedBytes = buf.getBitRange(3, 4, paddingHigh = false)
```
》java:
```java
// 从 0x53 中获取 3..6 位，返回 0x09
ByteBuffer buf = ByteBuffer.wrap(new byte[]{(byte) 0x53});
byte[] bytes = ByteBufferUtilsKt.getBitRange(buf, 3, 4);
byte[] lowPaddedBytes = ByteBufferUtilsKt.getBitRange(buf, 3, 4, false);
```
》js:
> 暂无
