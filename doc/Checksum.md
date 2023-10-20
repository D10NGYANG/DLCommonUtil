# Checksum
> 校验和工具

🔵 校验和计算类型 `ChecksumType`
- AND：与校验
- OR：或校验
- XOR：异或校验

## ① 校验校验和
> 从字节数组中检验校验和，其中最后一位为校验和。
> - @param type [ChecksumType] 计算方式，默认异或校验；

》kotlin:
```kotlin
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x04).assertChecksum() // true
// 使用指定的校验方式
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x04).assertChecksum(ChecksumType.XOR)
```
》java:
```java
boolean res = ChecksumUtilsKt.assertChecksum(new byte[]{0x01, 0x02, 0x03, 0x04, 0x04}, ChecksumType.XOR);
```
》js:
```js
const calculate = require('dl-common-util').com.d10ng.common.calculate;
const res = calculate.assertChecksum([0x01, 0x02, 0x03, 0x04, 0x04]); // true
// 使用指定的校验方式
const res = calculate.assertChecksum([0x01, 0x02, 0x03, 0x04, 0x04], calculate.ChecksumType.XOR);
```

## ② 计算校验和
> 计算字节数组的校验和，返回校验和字节。
> - @param type [ChecksumType] 计算方式，默认异或校验；
> - @param start [Int] 开始位置
> - @param length [Int] 长度

》kotlin:
```kotlin
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04).getChecksum() // 0x04
// 使用指定的校验方式
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04).getChecksum(ChecksumType.XOR) // 0x04
// 指定开始位置与长度
val res = byteArrayOf(0x00, 0x01, 0x02, 0x03, 0x04).getChecksum(ChecksumType.XOR, 1, 4) // 0x04
```
》java:
```java
byte res = ChecksumUtilsKt.getChecksum(new byte[]{0x01, 0x02, 0x03, 0x04}, ChecksumType.XOR, 0, 4);
```
》js:
```js
const calculate = require('dl-common-util').com.d10ng.common.calculate;
const res = calculate.getChecksum([0x01, 0x02, 0x03, 0x04]); // 0x04
// 使用指定的校验方式
const res = calculate.getChecksum([0x01, 0x02, 0x03, 0x04], calculate.ChecksumType.XOR); // 0x04
// 指定开始位置与长度
const res = calculate.getChecksum([0x00, 0x01, 0x02, 0x03, 0x04], calculate.ChecksumType.XOR, 1, 4); // 0x04
```

## ③ 添加校验和
> 在字节数组最后添加校验和。
> - @param type [ChecksumType] 计算方式，默认异或校验；

》kotlin:
```kotlin
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04).addChecksum() // 0x01, 0x02, 0x03, 0x04, 0x04
// 使用指定的校验方式
val res = byteArrayOf(0x01, 0x02, 0x03, 0x04).addChecksum(ChecksumType.XOR) // 0x01, 0x02, 0x03, 0x04, 0x04
```
》java:
```java
byte[] res = ChecksumUtilsKt.addChecksum(new byte[]{0x01, 0x02, 0x03, 0x04}, ChecksumType.XOR);
```
》js:
```js
const calculate = require('dl-common-util').com.d10ng.common.calculate;
const res = calculate.addChecksum([0x01, 0x02, 0x03, 0x04]); // 0x01, 0x02, 0x03, 0x04, 0x04
// 使用指定的校验方式
const res = calculate.addChecksum([0x01, 0x02, 0x03, 0x04], calculate.ChecksumType.XOR); // 0x01, 0x02, 0x03, 0x04, 0x04
```