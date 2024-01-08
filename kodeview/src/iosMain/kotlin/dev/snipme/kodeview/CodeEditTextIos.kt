package dev.snipme.kodeview

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.ComposeUIViewController
import dev.snipme.highlights.Highlights
import dev.snipme.kodeview.view.CodeEditTextSwiftUi

object CodeEditTextIos {
    fun uiViewController(
        highlights: Highlights,
        onValueChange: ((TextFieldValue) -> Unit)? = null,
    ) = ComposeUIViewController {
        CodeEditTextSwiftUi(
            highlights = highlights,
            onValueChange = onValueChange ?: {},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
            ),
        )
    }
}