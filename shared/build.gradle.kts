@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
}

group = "dev.snipme"
version = "0.1.1"

android {
    namespace = "dev.snipme.kodeview"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
    }
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        // Common
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                api("dev.snipme:highlights:0.3.0-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        // Android
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.7.2")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.core:core-ktx:1.10.1")
            }
        }
        // iOS
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}