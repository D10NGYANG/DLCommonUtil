# Number
> JS中的数字类型处理工具

显式字节数必须为1到8，越界会抛出 `IllegalArgumentException`。普通 JS number 必须为安全整数；更大的整数请传入有符号64位范围内的 bigint。未指定长度时，非负数使用最少字节；负数在32位范围内使用4字节，其余及 bigint 使用8字节。

## ① 转字符串并最大保留指定位数的小数
> 将 Number 类型转字符串并最大保留指定位数的小数
> - 该方法不会对数值进行四舍五入；
> - 如果数值为整数，则直接返回原字符串；
> - 如果数值中小数位数小于指定位数，则直接返回原字符串；

》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
// 保留2位小数，"3.14"
const str = DLBase.numberToString(3.1415926, 2);
```

## ② 转换为字节数组
> 将 整型 转 N 个字节的 ByteArray；
> > 字节数省略或设置为null，则根据整型值自动计算字节数; \
> > 字节数设置为大于0，则根据设置的字节数进行转换，如果字节数不足，则在前面补0，如果字节数过多，则截取前面的字节；
> - @param size [Int] 字节数，默认为null

》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
// 默认为null，自动计算字节数，[0xF1, 0xFF]
const byteArray = DLBase.numberToByteArray(0xF1FF);
// 设置为3，[0x00, 0xF1, 0xFF]
const byteArray = DLBase.numberToByteArray(0xF1FF, 3);
```

## ③ 转换为长整形
》js:
```js
const DLBase = require('dl-common-util').com.d10ng.common.base;
const longValue = DLBase.asLong(0xF1FF);
```