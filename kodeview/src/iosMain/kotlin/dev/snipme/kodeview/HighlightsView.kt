package dev.snipme.kodeview

import androidx.compose.ui.window.ComposeUIViewController
import dev.snipme.highlights.Highlights
import dev.snipme.kodeview.view.CodeTextView

fun CodeTextViewUiViewController(
    highlights: Highlights,
) = ComposeUIViewController {
    CodeTextView(
        highlights = highlights
    )
}