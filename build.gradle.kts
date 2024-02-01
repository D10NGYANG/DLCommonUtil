val bds100MavenUsername: String by project
val bds100MavenPassword: String by project
val npmJsToken: String by project

plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("com.android.library")
    id("maven-publish")
    id("dev.petuska.npm.publish") version "3.4.2"
    id("com.github.ben-manes.versions") version "0.51.0"
}

group = "com.github.D10NGYANG"
version = "0.0.9"

repositories {
    google()
    mavenCentral()
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        jvmToolchain(8)
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
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
                // serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.3.0")
                // 协程
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
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
    namespace = "$group.${rootProject.name}"

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType(MavenPublication::class) {
        artifact(tasks["javadocJar"])
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