# DLCommonUtil 依赖评估与升级记录

检查与执行日期：2026-11-03。下方保留升级前的评估依据，最终采用版本和验证结果以本节为准。

## 已完成的升级

| 项目 | 升级前 | 最终版本/处理 |
|---|---|---|
| Gradle Wrapper | 9.1.0 | 9.8.2，分发包及 Wrapper JAR 均核对官方 SHA-256 |
| Kotlin multiplatform / serialization 插件 | 2.3.21 | 2.4.21，两者同步 |
| versions 插件 | com.github.ben-manes.versions 0.54.0 | io.github.ben-manes.versions 0.67.0，使用新插件 ID |
| kotlinx-serialization-json | 1.11.0 | 1.12.0 |
| iconv-lite | 0.6.3 | 0.7.3 |
| kotlinx-io-core 测试依赖 | 0.9.0 | 删除，源码无使用且移除后测试通过 |
| Mocha | 11.7.5 | 11.7.6，采用 Kotlin 2.4.21 默认版本 |
| kotlin-web-helpers / TypeScript | 3.0.0 / 5.9.3 | 3.3.0 / 6.0.3，随 Kotlin 工具链升级 |
| glob / minimatch | 10.4.5 / 9.0.5 | 14.0.3 / 11.0.4，通过 Yarn resolution |
| diff / serialize-javascript | 7.0.0 / 6.0.2 | 9.0.0 / 7.1.4，通过 Yarn resolution |
| js-yaml / brace-expansion | 4.1.0 / 2.0.1 | 4.3.2 / 5.0.12，锁文件重新解析 |

按评估保留 AGP 9.0.1、coroutines 1.11.0、npm-publish 3.5.3、TinyPinyin 2.0.3.RELEASE。没有发布新版本或提交 Git。

### 实际遇到并解决的兼容问题

- Mocha 12.0.3 实测导致 Kotlin reporter 报 `Class constructor Base cannot be invoked without 'new'`。Kotlin 内置 kotlin-web-helpers 3.3.0 和查询到的最新版 3.5.4 都仍使用 `Base.call`，因此采用受支持的 Mocha 11.7.6，并将其有漏洞的传递依赖升级。没有通过跳过测试或修改 node_modules 规避问题。
- npm-publish 3.5.3 的 Node 路径自动推断调用了 Kotlin 2.4 已移除的 `NodeJsSetupTask.getDestination()`。在 `npmPublish.nodeHome` 显式绑定新 `destinationProvider`，保留插件的本地打包和发布配置；不依赖本机硬编码 Node 路径。
- 四项 Yarn resolution 跨越了 Mocha 的默认依赖范围。除了完整 Kotlin/JS 测试，还验证了 YAML 配置、glob 文件发现、minimatch 排除、断言失败 diff 展示及并行 worker 序列化，均通过。上游依赖范围包含安全修复后应移除这些覆盖配置。

### 验证结果

- JVM 146 项、JS 142 项、macOS Arm64 144 项测试通过，共 432 项，失败/错误/跳过均为 0。
- Android、iOS Arm64、iOS Simulator Arm64、iOS X64、Linux X64、Linux Arm64 均编译通过。
- 保留 Apple GBK 的 GB18030 超集行为；Apple 专属四字节兼容测试通过。
- 对 iconv-lite 0.6.3 与 0.7.3 比较完整 BMP 字符及四个补充平面字符的 GBK/GB18030 编解码，共 131,080 次对照，结果一致。
- `packJsPackage` 成功；独立临时目录安装实际生成的 tgz 后，编码、BigInt/数字字节转换、方位和投影接口验证通过。TypeScript 6.0.3 严格模式消费该包通过。
- JVM、Android、KMP 的 POM 和 Gradle module metadata 生成成功，运行时依赖版本正确，不含测试依赖；JVM class major version 为 52，仍为 Java 8。
- 最终安装的 npm 依赖集合为 64 个包，npm 安全公告查询返回 `{}`；原 6 个包的 23 条命中已消除。此结果不等于对全部生态传递依赖作了漏洞证明。
- `dependencyUpdates --no-parallel` 在新 Gradle/新插件下正常运行。
- OCR delegation 复检覆盖工作区38个变更文件；其中29个内容未变的文件复用上一轮逐文件审查，9个新增或更新文件重新检查，最终无待修发现。Wrapper二进制以官方校验值和实际执行验证。

### 保留的边界

Gradle 9.8.2 仍超出 Kotlin 2.4.21 官方完全支持矩阵上界 9.7.0；以上是本项目实测通过的范围，不等同于官方扩大兼容保证。构建仍有现存的配置阶段解析、Gradle 10 弃用、SDK XML 和 Native 链接诊断警告，未影响本次验证，也未通过关闭检查隐藏它们。

未运行 Android、iOS、Linux 真机/目标运行时测试；未执行远程发布。序列化依赖与编译器升级后的下游项目建议对齐 Kotlin 2.4.21；旧编译器消费兼容性未作保证。

## 升级前评估（留档）

## 结论

优先修复 Gradle 构建工具的已知高危漏洞；其次处理 JS 测试链的安全债务。业务运行时依赖没有查到本次查询范围内的已知漏洞。不要把版本检查插件列出的所有候选版本直接一起升级。

## 证据与范围

- 运行 `./gradlew dependencyUpdates --no-parallel -Drevision=release`；报告位于 `build/dependencyUpdates/report.txt`。默认并行运行会被当前版本检查插件拒绝。

- 查询 Maven Central、Gradle Plugin Portal、npm 注册表、官方发布记录及 Gradle 官方安全公告。

- 对当前安装的78个 npm 包名/版本执行 npm advisory bulk 查询，6个包命中23条公告；这些包来自 Mocha 测试依赖。

- 单独查询发布运行时 npm 闭包 iconv-lite 0.6.3 / safer-buffer，返回无公告。发布包 package.json 运行时依赖只有 iconv-lite。

- OSV 查询 kotlin-stdlib 2.3.21、coroutines-core-jvm 1.11.0、serialization-json-jvm 1.11.0、io-core-jvm 0.9.0、TinyPinyin 2.0.3.RELEASE、AGP 9.0.1，均无结果。此检查不包含完整 Maven/Gradle 插件传递依赖图，也不代表不存在漏洞。

## 必须优先安排：Gradle 9.1.0

当前版本命中官方高危公告：

- [GHSA-xwqc-3h47-hg64](https://github.com/gradle/gradle/security/advisories/GHSA-xwqc-3h47-hg64)：client/daemon 通道在认证前反序列化 Java 对象，可能导致代码执行或拒绝服务。攻击者需要同主机访问回环端口；并非可从外网直接攻击，共享构建主机风险更高。

- [GHSA-mvvg-497x-hmj8](https://github.com/gradle/gradle/security/advisories/GHSA-mvvg-497x-hmj8)：worker/daemon 通道未认证反序列化。

- [GHSA-j5m7-59rp-24f5](https://github.com/gradle/gradle/security/advisories/GHSA-j5m7-59rp-24f5)：SSL连接失败后继续查找后续仓库，存在错误解析恶意制品风险。攻击还需要后续仓库受控等条件。

- 另命中1月的仓库回退公告 [GHSA-mqwm-5m85-gmcv](https://github.com/gradle/gradle/security/advisories/GHSA-mqwm-5m85-gmcv) 与 [GHSA-w78c-w6vf-rw82](https://github.com/gradle/gradle/security/advisories/GHSA-w78c-w6vf-rw82)。

10月公告开源修复版本为9.8.1、8.14.6；当前AGP 9.0需要Gradle至少9.1，不能直接降到8.14.6。建议升级目标为[当前稳定版9.8.2](https://services.gradle.org/versions/current)，同步更新Wrapper SHA-256。官方列出的部分9.x旧分支补丁属于安全订阅，不应误当作可直接使用的开源补丁。

兼容性限制：[Kotlin官方矩阵](https://kotlinlang.org/docs/gradle-configure-project.html)中，2.3.21完整支持Gradle到9.3.0、AGP到9.0.0；2.4.21完整支持Gradle到9.7.0、AGP到9.3.1。Gradle9.8.2超出两者完整支持范围，不等同于必定无法构建，但必须实际验证，不能宣称是已验证的兼容组合。建议将Kotlin插件同步升到2.4.21以缩小差距，AGP先保持9.0.1，减少同轮变量；验证全部平台编译、JVM/JS/macOS测试及本地发布包生成。

暂缓升级时，官方建议隔离共享构建用户与CI runner；仓库问题可通过严格内容过滤/依赖校验缓解。现有google内容过滤不等同于完整依赖制品校验，也不能修复daemon漏洞。

## 值得尽快处理：JS测试工具链

Mocha由Kotlin/JS工具链生成配置，当前为11.7.5。问题不在已发布App的业务运行时；修复意义主要是开发机/CI安全与依赖卫生。

| 包 | 当前版本 | 公告 |
|---|---|---|
| serialize-javascript | 6.0.2 | [Serialize JavaScript has CPU Exhaustion Denial of Service via crafted array-like objects](https://github.com/advisories/GHSA-qj8w-gfj5-8c6v)；[Serialize JavaScript is Vulnerable to RCE via RegExp.flags and Date.prototype.toISOString()](https://github.com/advisories/GHSA-5c6j-r48x-rmvq)；[Serialize JavaScript: Cross-site scripting (XSS) via unescaped </script at the end of a serialized function body](https://github.com/advisories/GHSA-m5mm-p8h7-m29v) |
| js-yaml | 4.1.0 | [js-yaml has prototype pollution in merge (<<)](https://github.com/advisories/GHSA-mh29-5h37-fv8m)；[JS-YAML: Quadratic-complexity DoS in merge key handling via repeated aliases](https://github.com/advisories/GHSA-h67p-54hq-rp68)；[js-yaml: YAML merge-key chains can force quadratic CPU consumption](https://github.com/advisories/GHSA-52cp-r559-cp3m)；[JS-YAML: Quadratic CPU consumption in !!omap resolution (3.x and 4.x) — CVE-2026-59870 fix not backported](https://github.com/advisories/GHSA-5p4m-2wfm-xmqj)；[js-yaml: maxTotalMergeKeys does not limit CPU use for empty merge sources](https://github.com/advisories/GHSA-2883-xcg3-v3hh) |
| diff | 7.0.0 | [jsdiff has a Denial of Service vulnerability in parsePatch and applyPatch](https://github.com/advisories/GHSA-73rr-hh4g-fpgx) |
| brace-expansion | 2.0.1 | [brace-expansion Regular Expression Denial of Service vulnerability](https://github.com/advisories/GHSA-v6h2-p8h4-qcjw)；[brace-expansion: DoS via unbounded expansion length causing an out-of-memory process crash](https://github.com/advisories/GHSA-mh99-v99m-4gvg)；[brace-expansion: DoS via unbounded intermediate arrays, bypassing the CVE-2026-14257 mitigation](https://github.com/advisories/GHSA-rgw5-rvv9-x895)；[brace-expansion: Zero-step sequence causes process hang and memory exhaustion](https://github.com/advisories/GHSA-f886-m6hf-6m8v)；[brace-expansion: DoS via exponential-time expansion of consecutive non-expanding {} groups](https://github.com/advisories/GHSA-3jxr-9vmj-r5cp)；[brace-expansion: DoS via uncontrolled recursion in parseCommaParts causing stack exhaustion](https://github.com/advisories/GHSA-6j4f-fj2g-mc7p)；[brace-expansion: Quadratic-time expansion of the `{a},b}` rewrite causes CPU denial of service](https://github.com/advisories/GHSA-q2hr-2g5m-vwhr)；[brace-expansion: DoS via uncontrolled recursion on nested brace groups causing stack exhaustion](https://github.com/advisories/GHSA-qhr7-859c-m2p7) |
| minimatch | 9.0.5 | [minimatch has a ReDoS via repeated wildcards with non-matching literal in pattern](https://github.com/advisories/GHSA-3ppc-4f35-3m26)；[minimatch has ReDoS: matchOne() combinatorial backtracking via multiple non-adjacent GLOBSTAR segments](https://github.com/advisories/GHSA-7r86-cg39-jmmj)；[minimatch ReDoS: nested *() extglobs generate catastrophically backtracking regular expressions](https://github.com/advisories/GHSA-23c5-xmqv-rm74)；[minimatch has potential denial of service via globparts amplification](https://github.com/advisories/GHSA-g3hv-75wg-m249) |
| glob | 10.4.5 | [glob CLI: Command injection via -c/--cmd executes matches with shell:true](https://github.com/advisories/GHSA-5j98-mcp5-4vw2)；[Glob: AbortSignal cancellation bypass allows post-timeout filesystem traversal](https://github.com/advisories/GHSA-3g3m-2fjr-6pw4) |

优先通过Kotlin/JS工具链和Mocha配置升级并重新生成锁文件，不手改生成的 build/js/package.json。当前[Mocha稳定版12.0.3](https://github.com/mochajs/mocha/releases/tag/v12.0.3)，要求Node ^20.19.0 或 >=22.12.0。

单独升级Mocha并不能保证公告清零：其minimatch ^10.2.2低于新公告修复版11.0.1，glob ^13.0.0低于14.0.2。见[minimatch公告](https://github.com/advisories/GHSA-g3hv-75wg-m249)和[glob公告](https://github.com/advisories/GHSA-3g3m-2fjr-6pw4)。跨主版本resolutions不能盲目添加，需核对上游调用兼容性并执行JS测试；剩余不可达风险可明确记录等待上游，而不是声称升级Mocha后全部修复。

## 直接依赖与插件建议

| 组件 | 当前 → 稳定候选 | 建议与理由 |
|---|---|---|
| Kotlin multiplatform + serialization插件 | 2.3.21 → 2.4.21 | 值得更新，两个插件同步；包含JS生产压缩和Native链接修复。编译器/元数据升级需验证下游App的Kotlin版本兼容性。 |
| kotlinx-serialization-json | 1.11.0 → 1.12.0 | 值得更新，建议跟随Kotlin升级；该版基于Kotlin2.4.10，改进JSON性能和嵌套深度限制。自定义反序列化异常统一包装为JsonException，需检查下游catch行为。 |
| kotlinx-coroutines-core | 1.11.0 → 1.11.0 | 已是稳定最新版，保持。 |
| iconv-lite | 0.6.3 → 0.7.3 | 可选，主要是流式UTF编解码和类型/编码别名修复；当前仅整缓冲区GBK/GB18030收益有限。0.7.3仍为CommonJS；1.0 alpha已迁移ESM，不建议升级预览版。升级需跑字节级编码兼容测试；不涉及改变Apple GBK超集实现。 |
| kotlinx-io-core（测试） | 0.9.0 → 0.9.2 | 当前src中未找到使用，优先考虑删除未使用依赖，而非为了追版本升级。需移除后完整测试验证。 |
| TinyPinyin（JVM测试） | 2.0.3.RELEASE | 保持。版本插件建议2.0.3，但Maven metadata的latest/release均是2.0.3.RELEASE，不是明确的新版本。仅测试中的字表生成代码使用。 |
| ben-manes.versions | 0.54.0 → 0.67.0 | 值得更新，开发工具变更，帮助新版Gradle兼容和版本报告准确性。仍需按任务要求使用--no-parallel。 |
| dev.petuska.npm.publish | 3.5.3 → 3.5.3 | Plugin Portal没有更高稳定版本，保持；升级Gradle后重点验证包生成任务。 |
| AGP KMP library插件 | 9.0.1 → 9.5.0 | 不建议单独追最新。当前compileSdk36不需要新AGP；9.5超出Kotlin2.4.21官方完整支持上界9.3.1。 |

## 建议执行顺序

1. Gradle安全升级验证：9.8.2，并评估Kotlin2.4.21及versions插件0.67.0；AGP保持9.0.1。若发现不兼容，明确处理而不是退回有漏洞版本并称完成。

2. 清理JS测试依赖公告，确认Node版本、Mocha适配与新锁文件，重新审计。

3. 再评估serialization1.12.0；iconv0.7.3作为单独可回滚变更，用既有GBK/GB18030字节测试验证。

4. 删除确认未使用的kotlinx-io测试依赖；保留coroutines、npm-publish和TinyPinyin。

## 官方版本与发布依据

- [Kotlin 2.4.21发布说明](https://github.com/JetBrains/kotlin/releases/tag/v2.4.21)

- [serialization 1.12.0发布说明](https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.12.0)

- [kotlinx-io 0.9.2发布说明](https://github.com/Kotlin/kotlinx-io/releases/tag/0.9.2)

- [iconv-lite 0.7.3变更日志](https://github.com/pillarjs/iconv-lite/blob/v0.7.3/Changelog.md)

- [versions plugin 0.67.0](https://github.com/ben-manes/gradle-versions-plugin/releases/tag/v0.67.0)

- [AGP9.0兼容要求](https://developer.android.com/build/releases/agp-9-0-0-release-notes)

- [AGP9.5兼容要求](https://developer.android.com/build/releases/agp-9-5-0-release-notes)

- [TinyPinyin Maven元数据](https://repo.maven.apache.org/maven2/io/github/biezhi/TinyPinyin/maven-metadata.xml)

- [coroutines Maven元数据](https://repo.maven.apache.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/maven-metadata.xml)

- [npm发布插件元数据](https://plugins.gradle.org/m2/dev/petuska/npm/publish/dev.petuska.npm.publish.gradle.plugin/maven-metadata.xml)

## 原评估范围限制

升级前调查未扫描全部 Maven/构建插件传递依赖，也未运行攻击性漏洞利用。升级后的实际验证结果见文首。