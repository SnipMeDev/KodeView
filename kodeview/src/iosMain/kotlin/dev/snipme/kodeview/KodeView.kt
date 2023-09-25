package dev.snipme.kodeview

import androidx.compose.ui.main.DefaultIOSAppDelegate
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

// TODO Finish
fun codeEditText(
    highlights: Highlights,
) = ComposeUIViewController {
    CodeEditText(
        onValueChange = {},
        highlights = highlights
    )
}