# DLCommonUtil 1.1.0

本版本修复轨迹计算、缓冲区读取和 Apple 平台边界问题，并更新构建与运行时依赖。

## 安装与升级

Maven 仓库地址：https://raw.githubusercontent.com/D10NGYANG/maven-repo/main/repository

```kotlin
implementation("com.github.D10NGYANG:DLCommonUtil:1.1.0")
```

纯 JVM 可用 `com.github.D10NGYANG:DLCommonUtil-jvm:1.1.0`；JS 使用 `npm install dl-common-util@1.1.0`，也可安装 GitHub Release 的 tgz 附件。
建议下游项目对齐 Kotlin 2.4.21。本项目构建使用 Gradle 9.8.2，JVM 产物仍面向 Java 8，Android minSdk 仍为24。

## 修复

- 修正米级短线段的方位角，避免插值与重采样向错误方向移动。
- 轨迹投影改为球面有限大圆弧段上的最近点；越界时取最近端点，候选线段按实际最近距离排序。
- JVM/Android `java.nio.ByteBuffer.getBitRange` 尊重视图偏移和 limit，支持 slice、direct、read-only，保持 position/mark。
- Apple 日志不再将消息内容作为 NSLog 格式串；空 NSData 转换正常返回空数组。
- 坐标文本的整数域解析与最小输出宽度保持一致。
- 修正 JS number 被误识别为 Short 的转换错误，防止大数截断和三/四字节转换失败。
- 更新 Kotlin、Java、JS 示例和 API 文档。

## 兼容性须知

- **保留 Apple `encodeGBK` / `decodeGBK` 的历史 GB18030 超集行为**，继续支持 emoji、扩展汉字的四字节编码，不收紧为严格 GBK。
- Apple 解码非法或截断的 GBK/GB18030 字节时，现在抛出 `IllegalArgumentException`，不再返回字面量 `"null"`；处理不可信字节的调用方应捕获此异常。
- 投影结果与候选线段顺序可能与旧版不同；对跖端点没有唯一弧段，返回较近端点。距离模型为球面，非椭球最短线。
- JS `numberToByteArray` 拒绝小数、NaN、Infinity、不安全整数；更大整数请使用有符号64位 bigint。未指定长度时普通负整数在32位范围内使用4字节，bigint使用8字节；协议字段应显式传入长度（1到8）。
- serialization 1.12.0 调整反序列化异常包装为 JsonException；依赖特定异常类型的下游调用方应检查 catch 分支。
- 本次编译器与元数据基线为 Kotlin 2.4.21，旧 README 的历史最低版本说明不代表1.1.0的消费兼容保证。

## 依赖与构建

- Gradle 9.1.0 → 9.8.2，包含官方构建安全修复。
- Kotlin multiplatform/serialization 插件 2.3.21 → 2.4.21。
- kotlinx-serialization-json 1.11.0 → 1.12.0；iconv-lite 0.6.3 → 0.7.3。
- versions 插件升级0.67.0并使用新插件ID，删除未使用的kotlinx-io测试依赖。
- 保留 AGP 9.0.1、coroutines 1.11.0、npm-publish 3.5.3。
- Mocha 12 与 Kotlin reporter 不兼容，采用默认 Mocha 11.7.6并覆盖安全传递依赖；适配 npm-publish 的新Kotlin Node路径API。

## 验证

JVM 146、JS 142、macOS Arm64 144项测试通过；Android、iOS Arm64/Simulator Arm64/X64、Linux X64/Arm64编译通过。npm包安装和TypeScript严格类型检查通过。GBK/GB18030新旧iconv版本131,080次字符对照一致，Apple兼容测试通过；JS依赖安全公告查询零命中。

Gradle9.8.2超出Kotlin2.4.21官方完全支持矩阵上界9.7.0，以上为本项目实测范围。未运行Android/iOS/Linux目标运行时测试。完整依赖评估见 [依赖升级记录](DependencyReview-2026-11-03.md)。

## 发布渠道

1.1.0 可通过 Maven 仓库和 npm registry（latest）安装。Maven 使用本文所列的 GitHub raw 仓库地址。GitHub tgz 附件与 npm registry 的安装包一致。
