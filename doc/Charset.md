# Charset
> 字符串格式化工具

## ① 将字符串转换为GBK编码的字节数组

》kotlin:
```kotlin
val res = "测试".encodeGBK() // 0xB2, 0xE2, 0xCA, 0xD4
```
》java:
```java
byte[] res = CharsetUtilsKt.encodeGBK("测试");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.encodeGBK('测试'); // 0xB2, 0xE2, 0xCA, 0xD4
```

## ② 将GBK编码的字节数组转换为字符串

》kotlin:
```kotlin
val res = byteArrayOf(0xB2.toByte(), 0xE2.toByte(), 0xCA.toByte(), 0xD4.toByte()).decodeGBK() // 测试
```
》java:
```java
String res = CharsetUtilsKt.decodeGBK(new byte[]{0xB2, 0xE2, 0xCA, 0xD4});
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.decodeGBK([0xB2, 0xE2, 0xCA, 0xD4]); // 测试
```

## ③ 将字符串转换为UTF-8编码的字节数组

》kotlin:
```kotlin
val res = "测试".encodeUTF8() // 0xE6, 0xB5, 0x8B, 0xE8, 0xAF, 0x95
```
》java:
```java
byte[] res = CharsetUtilsKt.encodeUTF8("测试");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.encodeUTF8('测试'); // 0xE6, 0xB5, 0x8B, 0xE8, 0xAF, 0x95
```

## ④ 将UTF-8编码的字节数组转换为字符串

》kotlin:
```kotlin
val res = byteArrayOf(0xE6.toByte(), 0xB5.toByte(), 0x8B.toByte(), 0xE8.toByte(), 0xAF.toByte(), 0x95.toByte()).decodeUTF8() // 测试
```
》java:
```java
String res = CharsetUtilsKt.decodeUTF8(new byte[]{0xE6, 0xB5, 0x8B, 0xE8, 0xAF, 0x95});
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.decodeUTF8([0xE6, 0xB5, 0x8B, 0xE8, 0xAF, 0x95]); // 测试
```

## ⑤ 将字符串转换为Unicode编码的字节数组

》kotlin:
```kotlin
val res = "测试".encodeUnicode() // 0x6D 0x4B 0x8B 0xD5
```
》java:
```java
byte[] res = CharsetUtilsKt.encodeUnicode("测试");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.encodeUnicode('测试'); // 0x6D 0x4B 0x8B 0xD5
```

## ⑥ 将字符串转换为Unicode编码的字符串，如：\u6D4B\u8BD5

》kotlin:
```kotlin
val res = "测试".encodeUnicodeString() // \u6D4B\u8BD5
```
》java:
```java
String res = CharsetUtilsKt.encodeUnicodeString("测试");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.encodeUnicodeString('测试'); // \u6D4B\u8BD5
```

## ⑦ 将Unicode编码的字节数组转换为字符串

》kotlin:
```kotlin
val res = byteArrayOf(0x6D.toByte(), 0x4B.toByte(), 0x8B.toByte(), 0xD5.toByte()).decodeUnicode() // 测试
```
》java:
```java
String res = CharsetUtilsKt.decodeUnicode(new byte[]{0x6D, 0x4B, 0x8B, 0xD5});
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.decodeUnicode([0x6D, 0x4B, 0x8B, 0xD5]); // 测试
```

## ⑧ 将Unicode编码的字符串转换为字符串

》kotlin:
```kotlin
val res = "\\u6D4B\\u8BD5".decodeUnicodeString() // 测试
```
》java:
```java
String res = CharsetUtilsKt.decodeUnicodeString("\\u6D4B\\u8BD5");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.decodeUnicodeString('\\u6D4B\\u8BD5'); // 测试
```

## ⑨ 将ASCII字符串转换为字节数组

》kotlin:
```kotlin
val res = "test".encodeASCII() // 0x74, 0x65, 0x73, 0x74
```
》java:
```java
byte[] res = CharsetUtilsKt.encodeASCII("test");
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.encodeASCII('test'); // 0x74, 0x65, 0x73, 0x74
```

## ⑩ 将字节数组转换为ASCII字符串

》kotlin:
```kotlin
val res = byteArrayOf(0x74.toByte(), 0x65.toByte(), 0x73.toByte(), 0x74.toByte()).decodeASCII() // test
```
》java:
```java
String res = CharsetUtilsKt.decodeASCII(new byte[]{0x74, 0x65, 0x73, 0x74});
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.transform;
const res = transform.decodeASCII([0x74, 0x65, 0x73, 0x74]); // test
```