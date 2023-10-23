# Pinyin
> 拼音工具

## ① 字符串转拼音
> > 字符串转拼音字符串，并按照全大写的形式输出。
> - @receiver [String] 字符串
> - @param separator [String] 分隔符，默认为空字符串
> - @return [String] 拼音字符串

》kotlin:
```kotlin
val res = "你好".toPinyin() // NIHAO
// 指定分隔符
val res = "你好".toPinyin(separator = " ") // NI HAO
```
》java:
```java
String res = PinyinUtilsKt.toPinyin("你好", " "); // NI HAO
```
》js:
```js
const DLTransform = require('dl-common-util').com.d10ng.common.transform;
const res = DLTransform.toPinyin('你好'); // NIHAO
// 指定分隔符
const res = DLTransform.toPinyin('你好', ' '); // NI HAO
```