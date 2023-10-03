package dev.snipme.kodeview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.ComposeUITextField
import androidx.compose.ui.window.ComposeUIViewController


fun someText() = ComposeUIViewController(configure = {}) {
    ComposeUITextField("", {}, Modifier)
}

//fun SomeText() = ComposeUiView {
//    Text("ABCD")
//}