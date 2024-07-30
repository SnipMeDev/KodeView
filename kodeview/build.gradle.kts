@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.bcv)
    id("maven-publish")
    id("signing")
}

group = "dev.snipme"
version = "0.8.0"

android {
    namespace = "dev.snipme.kodeview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}
dependencies {
    implementation(libs.compose.material)
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
            baseName = "kodeview"
            isStatic = true
            export(libs.highlights)
        }
    }

    applyDefaultHierarchyTemplate()

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
                api(libs.highlights)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

apiValidation {
    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib {
        enabled = true
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