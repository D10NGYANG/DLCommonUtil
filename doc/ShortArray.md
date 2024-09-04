# ShortArray
> Short数组与Byte数组的互相转换

## ① Short数组转换为Byte数组
> - @receiver [ShortArray] short数组
> - @return [ByteArray] 字节数组

》kotlin:
```kotlin
val shorts = byteArray.toShortArray()
```
》java:
```java
short[] shorts = byteArray.toShortArray();
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const shorts = DLBase.byteArrayToShortArray(byteArray);
```

## ② Byte数组转换为Short数组
> - @receiver [ByteArray] 字节数组
> - @return [ShortArray] short数组

》kotlin:
```kotlin
val bytes = shortArray.toByteArray()
```
》java:
```java
byte[] bytes = shortArray.toByteArray();
```
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const bytes = DLBase.shortArrayToByteArray(shortArray);
```