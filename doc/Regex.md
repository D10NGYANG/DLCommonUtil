# Regex
> 正则表达式工具

## ① 保留正则表达式匹配的内容
> 利用正则表达式保留字符串。
> - @param regStr [String] 正则表达式字符串

》kotlin:
```kotlin
val str = "123ABC".keepByRegexStr("[0-9]+") // 123
```
》java:
```java
String str = RegexUtilsKt.keepByRegexStr("123ABC", "[0-9]+"); // 123
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.calculate;
const str = transform.keepByRegexStr("123ABC", "[0-9]+"); // 123
```

## ② 过滤正则表达式匹配的内容
> 利用正则表达式过滤字符串。
> - @param regStr [String] 正则表达式字符串

》kotlin:
```kotlin
val str = "123ABC".filterByRegexStr("[0-9]+") // ABC
```
》java:
```java
String str = RegexUtilsKt.filterByRegexStr("123ABC", "[0-9]+"); // ABC
```
》js:
```js
const transform = require('dl-common-util').com.d10ng.common.calculate;
const str = transform.filterByRegexStr("123ABC", "[0-9]+"); // ABC
```

## ③ 判断字符串是否符合身份证规则

》kotlin:
```kotlin
val isIdCard = "123456789012345678".isIdCard() // false
```
》java:
```java
boolean isIdCard = RegexUtilsKt.isIdCard("123456789012345678"); // false
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isIdCard = regex.isIdCard("123456789012345678"); // false
```

## ④ 判断字符串是否符合手机号规则

》kotlin:
```kotlin
val isMobileNumber = "13106673302".isMobileNumber() // true
```
》java:
```java
boolean isMobileNumber = RegexUtilsKt.isMobileNumber("13106673302"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isMobileNumber = regex.isMobileNumber("13106673302"); // true
```

## ⑤ 判断字符串是否符合邮箱规则

》kotlin:
```kotlin
val isEmail = "1111@163.com".isEmail() // true
```
》java:
```java
boolean isEmail = RegexUtilsKt.isEmail("1111@163.com"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isEmail = regex.isEmail("1111@163.com"); // true
```

## ⑥ 判断字符串是否只有中英文数字，不包含特殊字符

》kotlin:
```kotlin
val isOnlyChEnNum = "中文123ABC".isOnlyChEnNum() // true
```
》java:
```java
boolean isOnlyChEnNum = RegexUtilsKt.isOnlyChEnNum("中文123ABC"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyChEnNum = regex.isOnlyChEnNum("中文123ABC"); // true
```

## ⑦ 判断字符串是否只有英文数字，不包含特殊字符

》kotlin:
```kotlin
val isOnlyEnNum = "123ABC".isOnlyEnNum() // true
```
》java:
```java
boolean isOnlyEnNum = RegexUtilsKt.isOnlyEnNum("123ABC"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyEnNum = regex.isOnlyEnNum("123ABC"); // true
```

## ⑧ 判断字符串是否只有汉字

》kotlin:
```kotlin
val isOnlyChinese = "中文".isOnlyChinese() // true
```
》java:
```java
boolean isOnlyChinese = RegexUtilsKt.isOnlyChinese("中文"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyChinese = regex.isOnlyChinese("中文"); // true
```

## ⑨ 判断字符串是否只有字母

》kotlin:
```kotlin
val isOnlyLetter = "ABC".isOnlyLetter() // true
```
》java:
```java
boolean isOnlyLetter = RegexUtilsKt.isOnlyLetter("ABC"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyLetter = regex.isOnlyLetter("ABC"); // true
```

## ⑩ 判断字符串是否只有数字

》kotlin:
```kotlin
val isOnlyNumber = "123".isOnlyNumber() // true
```
》java:
```java
boolean isOnlyNumber = RegexUtilsKt.isOnlyNumber("123"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyNumber = regex.isOnlyNumber("123"); // true
```

## ⑪ 判断字符串是否只有代码

》kotlin:
```kotlin
val isOnlyCode = "123ABCabc".isOnlyCode() // true
```
》java:
```java
boolean isOnlyCode = RegexUtilsKt.isOnlyCode("123ABCabc"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyCode = regex.isOnlyCode("123ABCabc"); // true
```

## ⑫ 判断字符串是否只有汉字和全角符号

》kotlin:
```kotlin
val isOnlyChSymbol = "中文，。".isOnlyChSymbol() // true
```
》java:
```java
boolean isOnlyChSymbol = RegexUtilsKt.isOnlyChSymbol("中文，。"); // true
```
》js:
```js
const regex = require('dl-common-util').com.d10ng.common.calculate;
const isOnlyChSymbol = regex.isOnlyChSymbol("中文，。"); // true
```