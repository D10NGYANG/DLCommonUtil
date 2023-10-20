# ByteBuffer
> 字节缓冲区处理工具类

## ① 获取指定范围的比特位组成新的字节数组
> 从 ByteBuffer 中获取指定范围的 bit 组成新的 ByteArray。如从 0x53 中获取 3..6 位，返回 0x09。
> - @param start [Int] 开始比特位置
> - @param length [Int] 比特位长度

》kotlin:
```kotlin
// 从 0x53 中获取 3..6 位，返回 0x09
val buf = ByteBuffer.wrap(byteArrayOf(0x53.toByte()))
val bytes = buf.getBitRange(3, 4)
```
》java:
```java
// 从 0x53 中获取 3..6 位，返回 0x09
ByteBuffer buf = ByteBuffer.wrap(new byte[]{(byte) 0x53});
byte[] bytes = ByteBufferUtilsKt.getBitRange(buf, 3, 4);
```
》js:
> 暂无