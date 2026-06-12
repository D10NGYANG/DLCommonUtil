# BitWriter
> MSB First 位写入工具

`BitWriter` 用于构造包含非整字节字段的协议数据。位位置 `0` 对应第一个字节的最高有效位，
整数写入时只取指定数量的低位，并按高位优先写入。

## 创建与写入

》kotlin:
```kotlin
val bytes = BitWriter.allocate(2)
    .writeBits(0b101, 3)
    .writeBits(0b11, 2)
    .writeBits(0b10010, 5)
    .toByteArray()
// BC 80
```

》java:
```java
byte[] bytes = BitWriter.Companion.allocate(2)
    .writeBits(0b101, 3)
    .writeBits(0b11, 2)
    .writeBits(0b10010, 5)
    .toByteArray();
```

》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const bytes = DLBase.BitWriter.Companion.allocate(2)
    .writeIntBits(0b101, 3)
    .writeIntBits(0b11, 2)
    .writeIntBits(0b10010, 5)
    .toByteArray();
```

## 修改已有内容

`wrap` 会复制输入数组，避免外部修改破坏写入器状态。通过 `positionBits` 跳转到目标位后写入，
其中写入 `0` 会清除原有位。

```kotlin
val bytes = BitWriter.wrap(byteArrayOf(0xff.toByte()))
    .positionBits(3)
    .writeBits(0, 5)
    .toByteArray()
// E0
```

## 位置与容量

- `positionBits`：下一个写入位的位置。
- `capacityBits`：总位容量。
- `remainingBits`：剩余可写位数。
- `rewind()`：位置归零，保留内容。
- `clear()`：内容和位置全部归零。

写入长度非法时抛出 `IllegalArgumentException`，空间不足时抛出
`BufferOverflowException`。检查发生在写入前，不会留下部分写入的数据。
