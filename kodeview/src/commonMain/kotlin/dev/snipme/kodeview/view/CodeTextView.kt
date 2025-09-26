package dev.snipme.kodeview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.snipme.highlights.Highlights
import generateAnnotatedString

@Composable
fun CodeTextView(
    modifier: Modifier = Modifier.background(Color.Transparent),
    highlights: Highlights,
    textStyle: TextStyle = LocalTextStyle.current,
    showLineNumbers: Boolean = false,
    lineNumberTextStyle: TextStyle = textStyle.copy()
) {
    var textState by remember {
        mutableStateOf(AnnotatedString(highlights.getCode()))
    }

    LaunchedEffect(highlights) {
        textState = highlights
            .getHighlights()
            .generateAnnotatedString(highlights.getCode())
    }

    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Row(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
        ) {
            if (showLineNumbers) {
                val lines = textState.text.lines().size
                Column(horizontalAlignment = Alignment.End) {
                    for (i in 1..lines) {
                        Text(
                            text = i.toString(),
                            style = lineNumberTextStyle,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = textState,
                style = textStyle
            )
        }
    }
}