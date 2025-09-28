package dev.snipme.kodeview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    showLineNumbers: Boolean = false,
    lineNumberTextStyle: TextStyle = textStyle.copy(),
    async: Boolean = false,
) {
    val textState = rememberTextStateWithHighlights(highlights, async = async)

    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        LineNumberWrapper(
            text = textState.text,
            showLineNumbers = showLineNumbers,
            lineNumberTextStyle = lineNumberTextStyle
        ) {
            Text(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                text = textState.annotatedString,
                style = textStyle
            )
        }
    }
}