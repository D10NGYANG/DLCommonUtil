# Byte

> 字节处理工具

## ① 转换为二进制字符串
> > 将 Byte 转为 8位 二进制字符串 "10111101"。
> - @receiver [Byte] 字节
> - @return [String] 二进制字符串

》kotlin:
```kotlin
val binaryString = 0b10111101.toByte().toBinString()
```
》java:
```java
String binaryString = ByteUtilsKt.toBinString((byte) 0b10111101);
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const binaryString = DLBase.byteToBinString(0b10111101);
```

## ② 转换为十六进制字符串
> > 将 Byte 转为 2位 16进制字符串 "BD"。
> - @receiver [Byte] 字节
> - @param uppercase [Boolean] 是否需要大写，默认true需要
> - @return [String] 16进制字符串

》kotlin:
```kotlin
// 默认输出大写，“BD”
val hexString = 0xBD.toByte().toHexString()
// 设置为false表示小写，“bd”
val hexString = 0xBD.toByte().toHexString(false)
```
》java:
```java
// @param uppercase [Boolean] 是否需要大写，默认为true
String hexString = ByteUtilsKt.toHexString((byte) 0xBD, true);
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const hexString = DLBase.byteToHexString(0xBD);
```

## ③ 转换为无符号整数
> > 将 Byte 0xFF 转为 无符号整数 255。
> - @receiver [Byte] 字节，如0xFF
> - @return [Int] 无符号整数，如255

》kotlin:
```kotlin
val unsignedInt = 0xFF.toByte().toUnsignedInt()
```
》java:
```java
int unsignedInt = ByteUtilsKt.toUnsignedInt((byte) 0xFF);
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const unsignedInt = DLBase.byteToUnsignedInt(0xFF);
```

## ④ 获取指定位置的比特值
> > 从 Byte 中获取指定位置的 bit 值。返回值为 0 或 1。
> - @receiver [Byte] 字节
> - @param bitIndex [Int] 0..7
> - @return [Int] 0/1

》kotlin:
```kotlin
val bit = 0b10001101.toByte().getBit(3) // 1
```
》java:
```java
int bit = ByteUtilsKt.getBit((byte) 0b10001101, 3); // 1
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const bit = DLBase.byteGetBit(0b10001101, 3); // 1
```