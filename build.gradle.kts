val bds100MavenUsername: String by project
val bds100MavenPassword: String by project
val npmJsToken: String by project

plugins {
    kotlin("multiplatform") version "1.9.20"
    id("com.android.library")
    id("maven-publish")
    id("dev.petuska.npm.publish") version "3.4.1"
    id("com.github.ben-manes.versions") version "0.50.0"
}

group = "com.github.D10NGYANG"
version = "0.0.5"

repositories {
    google()
    mavenCentral()
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
    jvm {
        jvmToolchain(8)
    }
    js(IR) {
        moduleName = "dl-common-util"
        binaries.library()
        binaries.executable()
        nodejs()
        generateTypeScriptDefinitions()
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        commonMain {
            dependencies {
                // ByteBuffer
                api("com.ditchoom:buffer:1.3.7")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.3.0")
                // 协程
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")
            }
        }
        jvmMain {
            dependencies {
                // 拼音处理
                api("io.github.biezhi:TinyPinyin:2.0.3.RELEASE")
            }
        }
        androidMain {
            dependencies {
                // 拼音处理
                api("io.github.biezhi:TinyPinyin:2.0.3.RELEASE")
            }
        }
        jsMain {
            dependencies {
                // 拼音处理
                api(npm("pinyin-pro", "3.16.3"))
            }
        }
    }
}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "$group.${rootProject.name}"
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks["javadocJar"])
        }
    }
    repositories {
        maven {
            url = uri("/Users/d10ng/project/kotlin/maven-repo/repository")
        }
        maven {
            credentials {
                username = bds100MavenUsername
                password = bds100MavenPassword
            }
            setUrl("https://nexus.bds100.com/repository/maven-releases/")
        }
    }
}

npmPublish {
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
        isNonStable(candidate.version)
    }
}

// TODO 修复gradle 8.0以后出现任务依赖不声明导致的问题，待后续修复了再移除
tasks.named("jsNodeProductionLibraryPrepare") {
    dependsOn("jsProductionExecutableCompileSync")
}