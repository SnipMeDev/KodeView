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
                api("dev.snipme:highlights:0.2.0-SNAPSHOT")
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
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
            }
        }
    }
}

//kotlin {
//    android {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = libs.versions.jvmTarget.get()
//            }
//        }
//    }
//
//    ios {
//        binaries {
//            framework {
//                baseName = "shared"
//                isStatic = true
//            }
//        }
//    }
//
////    listOf(
////        iosX64(),
////        iosArm64(),
////        iosSimulatorArm64()
////    ).forEach {
////        it.binaries.framework {
////            baseName = "shared"
////            isStatic = true
////        }
////    }
//
//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                implementation(compose.runtime)
//                implementation(compose.foundation)
//                implementation(compose.material)
//                implementation(compose.ui)
//                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
//                implementation(compose.components.resources)
//            }
//        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test"))
//            }
//        }
//        val androidMain by getting {
//            dependsOn(commonMain)
//            dependencies {
//                implementation("dev.snipme:highlights-jvm:0.2.0-SNAPSHOT")
//                api("androidx.activity:activity-compose:1.7.0")
//                api("androidx.appcompat:appcompat:1.6.1")
//                api("androidx.core:core-ktx:1.10.0")
//            }
//        }
////        val androidUnitTest by getting
////        val iosX64Main by getting
////        val iosArm64Main by getting
////        val iosSimulatorArm64Main by getting
////        val iosMain by creating {
////            dependsOn(commonMain)
////            iosX64Main.dependsOn(this)
////            iosArm64Main.dependsOn(this)
////            iosSimulatorArm64Main.dependsOn(this)
////        }
////        val iosX64Test by getting
////        val iosArm64Test by getting
////        val iosSimulatorArm64Test by getting
////        val iosTest by creating {
////            dependsOn(commonTest)
////            iosX64Test.dependsOn(this)
////            iosArm64Test.dependsOn(this)
////            iosSimulatorArm64Test.dependsOn(this)
////        }
//    }
//}
//
//android {
//    namespace = "pl.tkadziolka.highlights"
//    compileSdk = 33
//
//    defaultConfig {
//        minSdk = 24
//    }
//}