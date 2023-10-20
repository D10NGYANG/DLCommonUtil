# ByteArray
> 字节数组处理工具

## ① 转换为二进制字符串
> 将 ByteArray 转为 8*N 二进制字符串 "00110011"。
> - @param space [Boolean] 每个byte中间是否需要空格，默认为false

》kotlin:
```kotlin
// 默认不需要空格，"00110011"
val binaryString = byteArrayOf(0x33, 0x33).toBinString()
// 设置为true表示需要空格，"00110011 00110011"
val binaryString = byteArrayOf(0x33, 0x33).toBinString(true)
```
》java:
```java
String binaryString = ByteArrayUtilsKt.toBinString(new byte[]{0x33, 0x33}, false);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const binaryString = DLCommon.byteArrayToBinString([0x33, 0x33]);
```

## ② 转换为十六进制字符串
> 将 ByteArray 转为 2*N 16进制字符串 "FC13"。
> - @param space [Boolean] 每个byte中间是否需要空格，默认为false
> - @param uppercase [Boolean] 是否需要大写，默认为true

》kotlin:
```kotlin
// 默认不需要空格，大写，"FC13"
val hexString = byteArrayOf(0xfc.toByte(), 0x13).toHexString()
// 设置为true表示需要空格，大写，"FC 13"
val hexString = byteArrayOf(0xfc.toByte(), 0x13).toHexString(true)
// 设置为false表示不需要空格，小写，"fc13"
val hexString = byteArrayOf(0xfc.toByte(), 0x13).toHexString(false, false)
```
》java:
```java
String hexString = ByteArrayUtilsKt.toHexString(new byte[]{0xfc, 0x13}, false, true);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const hexString = DLCommon.byteArrayToHexString([0xfc, 0x13]);
```

## ③ 转换为无符号整数
> 将 ByteArray 0xF1FF 转为 无符号整数 61951。

》kotlin:
```kotlin
val unsignedInt = byteArrayOf(0xF1.toByte(), 0xFF.toByte()).toUnsignedInt()
```
》java:
```java
int unsignedInt = ByteArrayUtilsKt.toUnsignedInt(new byte[]{(byte) 0xF1, (byte) 0xFF});
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const unsignedInt = DLCommon.byteArrayToUnsignedInt([0xF1, 0xFF]);
```

## ④ 转换为无符号长整数
> 将 ByteArray 0x018B0E7D65C8 转为 无符号长整数 1696755181000。

》kotlin:
```kotlin
val unsignedLong = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).toUnsignedLong()
```
》java:
```java
long unsignedLong = ByteArrayUtilsKt.toUnsignedLong(new byte[]{(byte) 0x01, (byte) 0x8B, (byte) 0x0E, (byte) 0x7D, (byte) 0x65, (byte) 0xC8});
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const unsignedLong = DLCommon.byteArrayToUnsignedLong([0x01, 0x8B, 0x0E, 0x7D, 0x65, 0xC8]);
```

## ⑤ 查找指定字节数组所在位置
> 在 ByteArray 中查找指定字节数组所在位置，返回第一个匹配的位置，未找到返回-1。
> - @param bs [ByteArray] 检索ByteArray

》kotlin:
```kotlin
val index = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).indexOf(byteArrayOf(0x0E.toByte(), 0x7D.toByte()))
```
》java:
```java
int index = ByteArrayUtilsKt.indexOf(new byte[]{(byte) 0x01, (byte) 0x8B, (byte) 0x0E, (byte) 0x7D, (byte) 0x65, (byte) 0xC8}, new byte[]{(byte) 0x0E, (byte) 0x7D});
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const index = DLCommon.indexOfByteArray([0x01, 0x8B, 0x0E, 0x7D, 0x65, 0xC8], [0x0E, 0x7D]);
```

## ⑥ 从前端开始填充bytearray
> 使用指定Byte将ByteArray填充到指定长度，从前端开始填充。
> - @param length [Int] 需要填充到的长度
> - @param padByte [Byte] 填充物，默认为0x00

》kotlin:
```kotlin
// 填充物默认为0x00，"00 00 00 00 00 01 8B 0E 7D 65 C8"
val bytes = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).padStart(10)
// 填充物为0xFF，"FF FF FF FF FF 01 8B 0E 7D 65 C8"
val bytes = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).padStart(10, 0xFF.toByte())
```
》java:
```java
// 填充物默认为0x00，"00 00 00 00 00 01 8B 0E 7D 65 C8"
byte[] bytes = ByteArrayUtilsKt.padStart(new byte[]{(byte) 0x01, (byte) 0x8B, (byte) 0x0E, (byte) 0x7D, (byte) 0x65, (byte) 0xC8}, 10, (byte) 0x00);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const bytes = DLCommon.byteArrayPadStart([0x01, 0x8B, 0x0E, 0x7D, 0x65, 0xC8], 10);
```

## ⑦ 从后端开始填充bytearray
> 使用指定Byte将ByteArray填充到指定长度，从后端开始填充。
> - @param length [Int] 需要填充到的长度
> - @param padByte [Byte] 填充物，默认为0x00

》kotlin:
```kotlin
// 填充物默认为0x00，"01 8B 0E 7D 65 C8 00 00 00 00"
val bytes = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).padEnd(10)
// 填充物为0xFF，"01 8B 0E 7D 65 C8 FF FF FF FF"
val bytes = byteArrayOf(0x01.toByte(), 0x8B.toByte(), 0x0E.toByte(), 0x7D.toByte(), 0x65.toByte(), 0xC8.toByte()).padEnd(10, 0xFF.toByte())
```
》java:
```java
// 填充物默认为0x00，"01 8B 0E 7D 65 C8 00 00 00 00"
byte[] bytes = ByteArrayUtilsKt.padEnd(new byte[]{(byte) 0x01, (byte) 0x8B, (byte) 0x0E, (byte) 0x7D, (byte) 0x65, (byte) 0xC8}, 10, (byte) 0x00);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const bytes = DLCommon.byteArrayPadEnd([0x01, 0x8B, 0x0E, 0x7D, 0x65, 0xC8], 10);
```

## ⑧ 获取指定范围的比特位组成新的字节数组
> 从 ByteArray 中获取指定范围的 bit 组成新的 ByteArray。如从 0x53 中获取 3..6 位，返回 0x09。
> - @param start [Int] 开始比特位置
> - @param length [Int] 比特位长度

》kotlin:
```kotlin
// 从 0x53 中获取 3..6 位，返回 0x09
val bytes = byteArrayOf(0x53.toByte()).getBitRange(3, 4)
```
》java:
```java
// 从 0x53 中获取 3..6 位，返回 0x09
byte[] bytes = ByteArrayUtilsKt.getBitRange(new byte[]{(byte) 0x53}, 3, 4);
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const bytes = DLCommon.byteArrayGetBitRange([0x53], 3, 4);
```
