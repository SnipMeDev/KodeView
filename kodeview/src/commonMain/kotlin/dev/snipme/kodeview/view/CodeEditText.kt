package dev.snipme.kodeview.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.LocalTextStyle as LocalTextStyle3
import androidx.compose.material3.TextField as TextField3
import androidx.compose.material3.TextFieldColors as TextFieldColors3
import androidx.compose.material3.TextFieldDefaults as TextFieldDefaults3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight

private const val TAB_LENGTH = 4
private const val TAB_CHAR = "\t"

@Composable
fun CodeEditTextLegacyMaterial(
    highlights: Highlights,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    translateTabToSpaces: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.TextFieldShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    val currentText = remember {
        mutableStateOf(
            TextFieldValue()
        )
    }

    TextField(
        modifier = modifier.fillMaxWidth(),
        onValueChange = {
            val fieldUpdate = it.calculateFieldPhraseUpdate(translateTabToSpaces)
            currentText.value = fieldUpdate
            onValueChange(fieldUpdate.text)
        },
        value = TextFieldValue(
            selection = currentText.value.selection,
            composition = currentText.value.composition,
            annotatedString = buildAnnotatedString {
                calculateAnnotatedString(highlights)
            },
        ),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

@Composable
fun CodeEditText(
    highlights: Highlights,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    translateTabToSpaces: Boolean = true,
    textStyle: TextStyle = LocalTextStyle3.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults3.shape,
    colors: TextFieldColors3 = TextFieldDefaults3.colors()
) {
    val currentText = remember {
        mutableStateOf(
            TextFieldValue()
        )
    }

    TextField3(
        modifier = modifier.fillMaxWidth(),
        onValueChange = {
            val fieldUpdate = it.calculateFieldPhraseUpdate(translateTabToSpaces)
            currentText.value = fieldUpdate
            onValueChange(fieldUpdate.text)
        },
        value = TextFieldValue(
            selection = currentText.value.selection,
            composition = currentText.value.composition,
            annotatedString = buildAnnotatedString {
                calculateAnnotatedString(highlights)
            },
        ),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}


private fun TextFieldValue.calculateFieldPhraseUpdate(translateTabToSpaces: Boolean) =
    if (translateTabToSpaces && text.contains(TAB_CHAR)) {
        val result = text.replace(TAB_CHAR, " ".repeat(TAB_LENGTH))
        this.copy(text = result, TextRange(selection.start + TAB_LENGTH - 1))
    } else {
        this
    }

private fun AnnotatedString.Builder.calculateAnnotatedString(highlights: Highlights) {
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
