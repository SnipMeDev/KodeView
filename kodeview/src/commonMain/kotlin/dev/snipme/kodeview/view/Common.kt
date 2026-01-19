package dev.snipme.kodeview.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import copySpanStyles
import dev.snipme.highlights.DefaultHighlightsResultListener
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.CodeHighlight
import generateAnnotatedString
import updateIndentations

@Composable
internal fun rememberTextStateWithHighlights(
    highlights: Highlights,
    onValueChange: (String) -> Unit = {},
    handleIndentations: Boolean = true,
    async: Boolean = true,
): Pair<TextFieldValue, (TextFieldValue) -> Unit> {
    val textState = remember {
        mutableStateOf(
            TextFieldValue(
                AnnotatedString(highlights.getCode())
            )
        )
    }

    val updateCallback: (TextFieldValue) -> Unit = { change ->
        val updated = change.updateIndentations(handleIndentations)
        if (updated.text != textState.value.text) {
            onValueChange(updated.text)
        }

        textState.value = updated.copySpanStyles(textState.value)
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



    return Pair(textState.value, updateCallback)
}

@Composable
internal fun LineNumberWrapper(
    modifier: Modifier = Modifier,
    text: String,
    hasHorizontalScroll: Boolean,
    showLineNumbers: Boolean,
    lineNumberTextStyle: androidx.compose.ui.text.TextStyle,
    numbersPadding: PaddingValues = PaddingValues(),
    content: @Composable () -> Unit
) {
    val modifierWithScroll = modifier.then(
        if (showLineNumbers || hasHorizontalScroll) {
            Modifier.horizontalScroll(rememberScrollState())
        } else {
            Modifier
        }
    )

    Row(modifier = modifier.fillMaxWidth()) {
        if (showLineNumbers) {
            val lines = text.lines().size
            Column(
                modifier = Modifier.padding(numbersPadding), horizontalAlignment = Alignment.End
            ) {
                for (i in 1..lines) {
                    Text(
                        text = i.toString(),
                        style = lineNumberTextStyle.copy(
                            fontFamily = FontFamily.Monospace
                        ),
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = modifierWithScroll) {
                content()
            }
        } else {
            Box(modifier = modifierWithScroll) {
                content()
            }
        }
    }
}