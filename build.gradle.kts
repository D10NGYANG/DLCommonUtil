val bds100MavenUsername: String by project
val bds100MavenPassword: String by project
val npmJsToken: String by project

plugins {
    kotlin("multiplatform") version "1.9.10"
    id("maven-publish")
    id("dev.petuska.npm.publish") version "3.4.1"
    id("com.github.ben-manes.versions") version "0.49.0"
}

group = "com.github.D10NGYANG"
version = "0.0.3"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
    }
    js(IR) {
        moduleName = "dl-common-util"
        binaries.library()
        binaries.executable()
        nodejs()
        generateTypeScriptDefinitions()
    }
    ios {
        binaries {
            framework {}
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        val commonMain by getting {
            dependencies {
                // ByteBuffer
                api("com.ditchoom:buffer:1.3.6")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                // 拼音处理
                api("io.github.biezhi:TinyPinyin:2.0.3.RELEASE")
            }
        }
        val jsMain by getting {
            dependencies {
                // 拼音处理
                api(npm("pinyin-pro", "3.16.3"))
            }
        }
    }
}

publishing {
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