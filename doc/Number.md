# Number
> JS中的数字类型处理工具

## ① 转字符串并最大保留指定位数的小数
> 将 Number 类型转字符串并最大保留指定位数的小数
> - 该方法不会对数值进行四舍五入；
> - 如果数值为整数，则直接返回原字符串；
> - 如果数值中小数位数小于指定位数，则直接返回原字符串；

》js:
```js
const DLCommon = require('dl-common-util').com.d10ng.common.base;
// 保留2位小数，"3.14"
const str = DLCommon.numberToString(3.1415926, 2);
```