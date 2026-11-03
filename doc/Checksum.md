# Checksum
> 校验和工具

🔵 校验和计算类型 `ChecksumType`
- SUM：累加校验（按字节取模）
- AND：历史累加校验别名，已弃用，请使用 SUM
- OR：或校验
- XOR：异或校验

## ① 校验校验和
> > 从字节数组中检验校验和，其中最后一位为校验和
> - @receiver [ByteArray] 字节数组
> - @param type [ChecksumType] 计算方式，默认异或校验；
> - @return [Boolean] true:检验成功; false:检验失败;

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
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const ChecksumType = DLCalculate.ChecksumType;
const res = DLCalculate.assertChecksum([0x01, 0x02, 0x03, 0x04, 0x04]); // true
// 使用指定的校验方式
const res = DLCalculate.assertChecksum([0x01, 0x02, 0x03, 0x04, 0x04], ChecksumType.XOR);
```

## ② 计算校验和
> > 计算字节数组的校验和，返回校验和字节。
> - @param type [ChecksumType] 计算方式，默认异或校验；
> - @param start [Int] 开始位置
> - @param length [Int] 长度
> - @return [Byte] 校验和

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
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const ChecksumType = DLCalculate.ChecksumType;
const res = DLCalculate.getChecksum([0x01, 0x02, 0x03, 0x04]); // 0x04
// 使用指定的校验方式
const res = DLCalculate.getChecksum([0x01, 0x02, 0x03, 0x04], ChecksumType.XOR); // 0x04
// 指定开始位置与长度
const res = DLCalculate.getChecksum([0x00, 0x01, 0x02, 0x03, 0x04], ChecksumType.XOR, 1, 4); // 0x04
```

## ③ 添加校验和
> > 在字节数组最后添加校验和。
> - @param type [ChecksumType] 计算方式，默认异或校验；
> - @return [ByteArray] 增加较验和后的字节数组

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
const DLCalculate = require('dl-common-util').com.d10ng.common.calculate;
const ChecksumType = DLCalculate.ChecksumType;
const res = DLCalculate.addChecksum([0x01, 0x02, 0x03, 0x04]); // 0x01, 0x02, 0x03, 0x04, 0x04
// 使用指定的校验方式
const res = DLCalculate.addChecksum([0x01, 0x02, 0x03, 0x04], ChecksumType.XOR); // 0x01, 0x02, 0x03, 0x04, 0x04
```