# String
> 字符串类型处理工具类

## ① 将二进制字符串转为Byte
> 将 8位 二进制字符串 "00110011" 转为 Byte

》kotlin:
```kotlin
val byte = "00110011".toByteFromBin()
```
》java:
```java
byte byte = StringUtilsKt.toByteFromBin("00110011");
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const byte = DLCommon.binStringToByte("00110011");
```

## ② 将二进制字符串转为ByteArray
> 将 8*N 二进制字符串 "00110011" 转为 ByteArray

》kotlin:
```kotlin
val byteArray = "0011001100110011".toByteArrayFromBin()
```
》java:
```java
byte[] byteArray = StringUtilsKt.toByteArrayFromBin("0011001100110011");
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const byteArray = DLCommon.binStringToByteArray("0011001100110011");
```

## ③ 将十六进制字符串转为Byte
> 将 2位 16进制字符串 "fc" 转为 Byte

》kotlin:
```kotlin
val byte = "fc".toByteFromHex()
```
》java:
```java
byte byte = StringUtilsKt.toByteFromHex("fc");
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const byte = DLCommon.hexStringToByte("fc");
```

## ④ 将十六进制字符串转为ByteArray
> 将 2*N 16进制字符串 "fcfc" 转为 ByteArray

》kotlin:
```kotlin
val byteArray = "fcfc".toByteArrayFromHex()
```
》java:
```java
byte[] byteArray = StringUtilsKt.toByteArrayFromHex("fcfc");
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const byteArray = DLCommon.hexStringToByteArray("fcfc");
```

## ⑤ 计算字符串的字节长度
> 以字节单位获得字符串的长度，其中全角字符算两个字节，半角字符算一个字节

》kotlin:
```kotlin
val length = "我是中国人123ABC".getByteLength() // 16
```
》java:
```java
int length = StringUtilsKt.getByteLength("我是中国人123ABC"); // 16
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const length = DLCommon.getStringByteLength("我是中国人123ABC"); // 16
```

## ⑥ 获取字符串的第一个字并转换成大写的

》kotlin:
```kotlin
val firstChar = "abc".getFirstUpperCase() // "A"
```
》java:
```java
String firstChar = StringUtilsKt.getFirstUpperCase("abc"); // "A"
```
》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
const firstChar = DLCommon.getStringFirstUpperCase("abc"); // "A"
```