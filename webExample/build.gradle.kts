@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation("dev.snipme:kodeview:0.4.0")
            }
        }
    }
}

compose.experimental {
    web.application {}
}