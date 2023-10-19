@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    id("maven-publish")
    id("signing")
}

group = "dev.snipme"
version = "0.6.0"

android {
    namespace = "dev.snipme.kodeview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}

kotlin {
    jvm()

    js(IR) {
        browser()
    }

    androidTarget {
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
            // TODO Try to export Highlights with Kotlin 1.9.20
            baseName = "kodeview"
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
                implementation(compose.material3)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // Share logic between submodules
                api("dev.snipme:highlights:0.7.1")
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
                implementation("androidx.core:core-ktx:1.12.0")
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

signing {
    useInMemoryPgpKeys(
        rootProject.ext["signing.keyId"] as String,
        rootProject.ext["signing.key"] as String,
        rootProject.ext["signing.password"] as String
    )
    sign(publishing.publications)
}

publishing {
    val emptyJar = tasks.register<Jar>("emptyJar") {
        archiveAppendix.set("empty")
    }

    publications.forEach {
        val publication = it as? MavenPublication ?: return@forEach

        publication.artifact(emptyJar) {
            classifier = "javadoc"
        }

        publication.pom.withXml {
            val root = asNode()
            root.appendNode("name", "kodeview")
            root.appendNode(
                "description",
                "Kotlin Multiplatform syntax highlighting views"
            )
            root.appendNode("url", "https://github.com/SnipMeDev/KodeView")

            root.appendNode("licenses").apply {
                appendNode("license").apply {
                    appendNode("name", "The Apache Software License, Version 2.0")
                    appendNode("url", "https://www.apache.org/licenses/LICENSE-2.0.txt")
                    appendNode("distribution", "repo")
                }
            }

            root.appendNode("developers").apply {
                appendNode("developer").apply {
                    appendNode("id", "tkadziolka")
                    appendNode("name", "Tomasz Kądziołka")
                    appendNode("email", "kontakt@tkadziolka.pl")
                }
            }

            root.appendNode("scm").apply {
                appendNode(
                    "connection",
                    "scm:git:ssh://git@github.com:SnipMeDev/KodeView.git"
                )
                appendNode(
                    "developerConnection",
                    "scm:git:ssh://git@github.org:SnipMeDev/KodeView.git",
                )
                appendNode("url", "https://github.com/SnipMeDev/KodeView")
            }
        }
    }
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(":kodeview:signIosSimulatorArm64Publication")
    dependsOn(":kodeview:signIosArm64Publication")
    dependsOn(":kodeview:signIosX64Publication")
    dependsOn(":kodeview:signJvmPublication")
    dependsOn(":kodeview:signJsPublication")
    dependsOn(":kodeview:signKotlinMultiplatformPublication")
}

tasks.withType<PublishToMavenRepository> {
    dependsOn(":kodeview:signIosSimulatorArm64Publication")
    dependsOn(":kodeview:signIosArm64Publication")
    dependsOn(":kodeview:signIosX64Publication")
    dependsOn(":kodeview:signJvmPublication")
    dependsOn(":kodeview:signJsPublication")
    dependsOn(":kodeview:signKotlinMultiplatformPublication")
}