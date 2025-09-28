package dev.snipme.kodeview.view.material3

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import copySpanStyles
import dev.snipme.highlights.DefaultHighlightsResultListener
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.CodeHighlight
import dev.snipme.kodeview.view.LineNumberWrapper
import dev.snipme.kodeview.view.rememberTextStateWithHighlights
import generateAnnotatedString
import updateIndentations
import androidx.compose.material3.LocalTextStyle as LocalTextStyle3
import androidx.compose.material3.TextField as TextField3
import androidx.compose.material3.TextFieldColors as TextFieldColors3
import androidx.compose.material3.TextFieldDefaults as TextFieldDefaults3


@Composable
fun CodeEditText(
    highlights: Highlights,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    handleIndentations: Boolean = true,
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
    colors: TextFieldColors3 = TextFieldDefaults3.colors(),
    showLineNumbers: Boolean = false,
    hasHorizontalScroll: Boolean = false,
    lineNumberTextStyle: TextStyle = textStyle.copy()
) {
    val (currentText, updateCallback) = rememberTextStateWithHighlights(
        highlights,
        onValueChange = onValueChange,
        handleIndentations = handleIndentations,
    )

    val labelPadding = label?.run { 8.dp } ?: 0.dp
    val numbersPadding = PaddingValues(top = 16.dp + labelPadding)

    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        LineNumberWrapper(
            text = currentText.text,
            showLineNumbers = showLineNumbers,
            hasHorizontalScroll = hasHorizontalScroll,
            lineNumberTextStyle = lineNumberTextStyle,
            numbersPadding = numbersPadding,
        ) {
            TextField3(
                onValueChange = updateCallback,
                value = currentText,
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
    }
}

@Composable
fun CodeEditTextSwiftUi(
    highlights: Highlights,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    handleIndentations: Boolean = true,
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
    colors: TextFieldColors3 = TextFieldDefaults3.colors(),
    showLineNumbers: Boolean = false,
    lineNumberTextStyle: TextStyle = textStyle.copy()
) {
    val currentText = remember {
        mutableStateOf(
            TextFieldValue(
                AnnotatedString(highlights.getCode())
            )
        )
    }
    val commentColor = remember {
        mutableStateOf(
            Color(highlights.getTheme().comment)
        )
    }

    LaunchedEffect(highlights) {
        highlights.getHighlightsAsync(object : DefaultHighlightsResultListener() {
            override fun onSuccess(result: List<CodeHighlight>) {
                currentText.value = currentText.value.copy(
                    annotatedString = result.generateAnnotatedString(currentText.value.text),
                )
                commentColor.value = Color(highlights.getTheme().comment)
            }
        })
    }

    fun updateNewValue(change: TextFieldValue) {
        val updated = change.updateIndentations(handleIndentations)

        if (updated.text != currentText.value.text) {
            onValueChange(updated)
        }

        currentText.value = updated.copySpanStyles(
            currentText.value
        )
    }

    Row(modifier = modifier.fillMaxWidth()) {
        if (showLineNumbers) {
            val lines = currentText.value.text.lines().size.coerceAtLeast(minLines)
            Column(
                modifier
            ) {
                (1..lines).forEach { i ->
                    Text(
                        text = i.toString(),
                        style = lineNumberTextStyle,
                    )
                }
            }
        }

        // TODO Extract common logic to a wrapper

        val lineNumberAwareModifier = if (showLineNumbers)
            modifier.horizontalScroll(rememberScrollState())
        else
            modifier

        TextField3(
            modifier = lineNumberAwareModifier.weight(1f),
            value = currentText.value,
            onValueChange = ::updateNewValue,
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
}
