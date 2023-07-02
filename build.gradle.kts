@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
