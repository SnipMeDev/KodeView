import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight

internal const val TAB_LENGTH = 4
internal const val TAB_CHAR = "\t"

internal fun TextFieldValue.calculateFieldPhraseUpdate(translateTabToSpaces: Boolean) =
    if (translateTabToSpaces && text.contains(TAB_CHAR)) {
        val result = text.replace(TAB_CHAR, " ".repeat(TAB_LENGTH))
        this.copy(text = result, TextRange(selection.start + TAB_LENGTH - 1))
    } else {
        this
    }

internal fun AnnotatedString.Builder.generateAnnotatedString(highlights: Highlights) {
    append(highlights.getCode())

    highlights.getHighlights()
        .filterIsInstance<ColorHighlight>()
        .forEach {
            addStyle(
                SpanStyle(color = Color(it.rgb).copy(alpha = 1f)),
                start = it.location.start,
                end = it.location.end,
            )
        }

    highlights.getHighlights()
        .filterIsInstance<BoldHighlight>()
        .forEach {
            addStyle(
                SpanStyle(fontWeight = FontWeight.Bold),
                start = it.location.start,
                end = it.location.end,
            )
        }
}