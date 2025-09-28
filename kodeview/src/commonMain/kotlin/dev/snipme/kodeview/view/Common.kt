package dev.snipme.kodeview.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import dev.snipme.highlights.DefaultHighlightsResultListener
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.CodeHighlight
import generateAnnotatedString

@Composable
internal fun rememberTextStateWithHighlights(
    highlights: Highlights,
    async: Boolean = true,
): TextFieldValue {
    val textState = remember {
        mutableStateOf(
            TextFieldValue(
                AnnotatedString(highlights.getCode())
            )
        )
    }

    LaunchedEffect(highlights) {
        if (async) {
            highlights.getHighlightsAsync(object : DefaultHighlightsResultListener() {
                override fun onSuccess(result: List<CodeHighlight>) {
                    textState.value = textState.value.copy(
                        annotatedString = result.generateAnnotatedString(textState.value.text),
                    )
                }
            })
            return@LaunchedEffect
        }

        val result = highlights.getHighlights()
        textState.value = textState.value.copy(
            annotatedString = result.generateAnnotatedString(textState.value.text),
        )
    }

    return textState.value
}

@Composable
internal fun LineNumberWrapper(
    modifier: Modifier = Modifier,
    text: String,
    showLineNumbers: Boolean,
    lineNumberTextStyle: androidx.compose.ui.text.TextStyle,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (showLineNumbers) {
            val lines = text.lines().size
            Column(horizontalAlignment = Alignment.End) {
                for (i in 1..lines) {
                    Text(
                        text = i.toString(),
                        style = lineNumberTextStyle,
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = modifier.horizontalScroll(rememberScrollState())) {
                content()
            }
        } else {
            content()
        }
    }
}