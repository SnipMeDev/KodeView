package dev.snipme.kodeview.view

import androidx.compose.foundation.background
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.snipme.highlights.Highlights

@Composable
fun CodeTextView(
    modifier: Modifier = Modifier.background(Color.Transparent),
    highlights: Highlights,
    textStyle: TextStyle = LocalTextStyle.current,
    hasHorizontalScroll: Boolean = false,
    showLineNumbers: Boolean = false,
    lineNumberTextStyle: TextStyle = textStyle.copy(),
) {
    val (textState, _) = rememberTextStateWithHighlights(highlights)

    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        LineNumberWrapper(
            text = textState.text,
            hasHorizontalScroll = hasHorizontalScroll,
            showLineNumbers = showLineNumbers,
            lineNumberTextStyle = lineNumberTextStyle
        ) {
            Text(
                text = textState.annotatedString,
                style = textStyle
            )
        }
    }
}