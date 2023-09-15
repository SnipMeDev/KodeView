@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
}

//apply(from = "publish-root.gradle")

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":kodeview"))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

//signing {
//    useInMemoryPgpKeys(
//        rootProject.ext["signing.keyId"] as String,
//        rootProject.ext["signing.key"] as String,
//        rootProject.ext["signing.password"] as String
//    )
//    sign(publishing.publications)
//}