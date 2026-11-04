# DLCommonUtil
基于kotlin多平台的常用数据类型转换工具类库，目前支持`Android`、`JVM`、`IOS`、`MacOS`、`JS`、`Linux`；

*最新版本：`1.1.0`*

> `0.7.0`开始，需要使用`kotlin 2.1.10`以上版本。

当前源码使用 Kotlin `2.4.21`、Gradle `9.8.2`，构建需要 JDK 17 或更新版本；JVM 产物仍面向 Java 8。
使用当前源码构建产物的下游项目建议对齐 Kotlin `2.4.21`，历史版本的最低 Kotlin 要求不适用于本次依赖升级。
升级版本、兼容性处理与验证结果见 [依赖升级记录](doc/DependencyReview-2026-11-03.md)。

## ① 安装
Maven 制品由 [maven-repo](https://github.com/D10NGYANG/maven-repo) 提供：

```kotlin
repositories {
    maven("https://raw.githubusercontent.com/D10NGYANG/maven-repo/main/repository") {
        mavenContent { includeGroupAndSubgroups("com.github.D10NGYANG") }
    }
    google()
    mavenCentral()
}

// KMP commonMain；Gradle 自动选择目标平台变体
implementation("com.github.D10NGYANG:DLCommonUtil:1.1.0")
// 纯 JVM 项目可使用 com.github.D10NGYANG:DLCommonUtil-jvm:1.1.0
```

JavaScript：`npm install dl-common-util@1.1.0`。GitHub Release 同时提供 tgz 安装包。

从旧版本升级前请阅读 [1.1.0 升级说明](doc/Upgrade-1.1.0.md)。

## ② 使用
### 1. 基础类型
- [字节 Byte](doc/Byte.md)
- [字节数组 ByteArray](doc/ByteArray.md)
- [字节缓存区 ByteBuffer](doc/ByteBuffer.md)
- [位写入器 BitWriter](doc/BitWriter.md)
- [双精度浮点型 Double](doc/Double.md)
- [单精度浮点型 Float](doc/Float.md)
- [整型 Int](doc/Int.md)
- [长整型 Long](doc/Long.md)
- [短整型 Short](doc/Short.md)
- [数字 Number(JS)](doc/Number.md)
- [短整型数组 ShortArray](doc/ShortArray.md)
- [字符串 String](doc/String.md)

### 2. 计算工具
- [校验和 Checksum](doc/Checksum.md)
- [颜色 Color](doc/Color.md)
- [正则 Regex](doc/Regex.md)

### 3. 转换工具
- [拼音 Pinyin](doc/Pinyin.md)
- [编码 Charset](doc/Charset.md)

### 4. 经纬度工具
- [经纬度坐标 Coordinate](doc/Coordinate.md)
- [轨迹算法 Track](doc/Track.md)

## ③ TODO
- [ ] 增加IOS平台库打包、增加IOS代码示例；
