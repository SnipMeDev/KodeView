package dev.snipme.kodeview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import dev.snipme.highlights.Highlights
import dev.snipme.kodeview.view.CodeEditTextLegacyMaterial

object CodeEditTextIos {
    fun uiViewController(
        highlights: Highlights,
        onValueChange: ((String) -> Unit)? = null,
    ) = ComposeUIViewController {
        CodeEditTextLegacyMaterial(
            highlights = highlights,
            onValueChange = onValueChange ?: {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}