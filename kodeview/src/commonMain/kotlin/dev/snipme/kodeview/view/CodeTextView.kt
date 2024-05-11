package dev.snipme.kodeview.view

import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextGeometricTransform
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight
import dev.snipme.highlights.model.PhraseLocation
import generateWithLineNumbers

@Composable
fun CodeTextView(
    modifier: Modifier = Modifier.background(Color.Transparent),
    highlights: Highlights
) {
    Surface {
        Text(
            modifier = modifier,
            text = buildAnnotatedString {
                append(highlights.getCode())

                highlights.getHighlights()
                    .filterIsInstance<ColorHighlight>()
                    .map {
                        it.copy(
                            location = PhraseLocation(
                                start = it.location.start + 4,
                                end = it.location.end + 4,
                            )
                        )
                    }
                    .forEach {
                        addStyle(
                            SpanStyle(color = Color(it.rgb).copy(alpha = 1f)),
                            start = it.location.start,
                            end = it.location.end,
                        )
                    }

                highlights.getHighlights()
                    .filterIsInstance<BoldHighlight>()
                    .map {
                        it.copy(
                            location = PhraseLocation(
                                start = it.location.start + 4,
                                end = it.location.end + 4,
                            )
                        )
                    }
                    .forEach {
                        addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start = it.location.start,
                            end = it.location.end,
                        )
                    }

                addStyle(
                    SpanStyle(textGeometricTransform = TextGeometricTransform(
                        skewX = 0.1f,
                    )),
                    start = 0,
                    end = 4,
                )
            })
    }
}