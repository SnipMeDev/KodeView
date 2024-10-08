plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    alias(libs.plugins.compose) apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    alias(libs.plugins.compose.compiler) apply false
}

apply(from = "publish-root.gradle")