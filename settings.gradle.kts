pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        // Compose Desktop
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // Compose Web
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }

    plugins {
        val kotlinVersion = "1.9.21"
        val agpVersion = "8.1.1"
        val composeVersion = "1.5.11"

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.base").version(agpVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        // Compose Desktop
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // Compose Web
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}

rootProject.name = "KodeView"
include(":kodeview")
include(":androidExample")
include(":desktopExample")
include(":webExample")