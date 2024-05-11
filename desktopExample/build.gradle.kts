@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.compose)
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.materialIconsExtended)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.components.resources)
    implementation(compose.desktop.currentOs)
    // local module kodeview
    implementation(project(":kodeview"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}