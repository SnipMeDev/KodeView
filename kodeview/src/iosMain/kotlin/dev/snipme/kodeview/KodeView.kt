package dev.snipme.kodeview

import androidx.compose.ui.window.ComposeUIViewController
import dev.snipme.highlights.Highlights
import dev.snipme.kodeview.view.CodeEditText
import dev.snipme.kodeview.view.CodeTextView

fun codeTextView(
    highlights: Highlights,
) = ComposeUIViewController {
    CodeTextView(
        highlights = highlights
    )
}

// TODO Add other fields to customize
fun codeEditText(
    highlights: Highlights,
    onValueChange: (String) -> Unit,
) = ComposeUIViewController {
    CodeEditText(
        onValueChange = onValueChange,
        highlights = highlights
    )
}