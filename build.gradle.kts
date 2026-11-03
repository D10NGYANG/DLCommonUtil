import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    kotlin("multiplatform") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
    id("com.android.kotlin.multiplatform.library") version "9.0.1"
    id("maven-publish")
    id("dev.petuska.npm.publish") version "3.5.3"
    id("io.github.ben-manes.versions") version "0.67.0"
}

group = "com.github.D10NGYANG"
version = "1.1.0"

repositories {
    google {
        mavenContent {
            includeGroupAndSubgroups("androidx")
            includeGroupAndSubgroups("com.android")
            includeGroupAndSubgroups("com.google")
        }
    }
    mavenCentral()
}

kotlin {
    jvmToolchain(8)
    android {
        namespace = "com.d10ng.common"
        compileSdk = 36
        minSdk = 24
    }
    jvm()
    js {
        outputModuleName = "dl-common-util"
        binaries.library()
        nodejs()
        generateTypeScriptDefinitions()
        compilerOptions {
            freeCompilerArgs.add("-Xes-long-as-bigint")
        }
    }
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    macosArm64()
    linuxX64()
    linuxArm64()

    sourceSets {
        all {
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
        }
        commonMain {
            dependencies {
                // serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.12.0")
                // 协程
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0")
            }
        }
        jsMain {
            dependencies {
                implementation(npm("iconv-lite", "0.7.3"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jvmTest {
            dependencies {
                // 拼音处理
                implementation("io.github.biezhi:TinyPinyin:2.0.3.RELEASE")
            }
        }
    }
}

plugins.withType<YarnPlugin> {
    extensions.configure<YarnRootExtension> {
        // Kotlin's reporter requires Mocha 11; remove these overrides when its ranges include the security fixes.
        resolution("diff", "9.0.0")
        resolution("glob", "14.0.3")
        resolution("minimatch", "11.0.4")
        resolution("serialize-javascript", "7.1.4")
    }
}

val bds100MavenUsername = providers.gradleProperty("bds100MavenUsername")
    .orElse(providers.environmentVariable("BDS100_MAVEN_USERNAME"))
val bds100MavenPassword = providers.gradleProperty("bds100MavenPassword")
    .orElse(providers.environmentVariable("BDS100_MAVEN_PASSWORD"))
val npmJsToken = providers.gradleProperty("npmJsToken")
    .orElse(providers.environmentVariable("NPM_JS_TOKEN"))

publishing {
    repositories {
        maven {
            name = "local"
            url = uri("/Users/d10ng/project/kotlin/maven-repo/repository")
        }
        maven {
            name = "bds100"
            credentials {
                username = bds100MavenUsername.orNull
                password = bds100MavenPassword.orNull
            }
            setUrl("https://nexus.bds100.com/repository/maven-releases/")
        }
    }
}

npmPublish {
    // npm-publish 3.5.3 uses a removed KGP getter; bind the current Node setup output instead.
    nodeHome.set(layout.dir(tasks.named<NodeJsSetupTask>("kotlinNodeJsSetup")
        .flatMap { it.destinationProvider }.map { it.asFile }))
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(npmJsToken)
        }
    }
    packages {
        named("js") {
            packageName.set("dl-common-util")
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
