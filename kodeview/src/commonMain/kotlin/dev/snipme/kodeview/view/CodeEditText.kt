package dev.snipme.kodeview.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import copySpanStyles
import dev.snipme.highlights.DefaultHighlightsResultListener
import updateIndentations
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.CodeHighlight
import generateAnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun CodeEditText(
    highlights: Highlights,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    handleIndentations: Boolean = true,
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
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
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

    LaunchedEffect(highlights) {
        highlights.getHighlightsAsync(object : DefaultHighlightsResultListener() {
            override fun onSuccess(result: List<CodeHighlight>) {
                currentText.value = currentText.value.copy(
                    annotatedString = result.generateAnnotatedString(currentText.value.text),
                )
            }
        })
    }

    fun updateNewValue(change: TextFieldValue) {
        val updated = change.updateIndentations(handleIndentations)
        if (updated.text != currentText.value.text) {
            onValueChange(updated.text)
        }

        currentText.value = updated.copySpanStyles(
            currentText.value
        )
    }

    Row(modifier = modifier) {
        if (showLineNumbers) {
            val lines = currentText.value.text.lines().size
            Column(horizontalAlignment = Alignment.End,) {
                for (i in 1..lines) {
                    androidx.compose.material.Text(
                        text = i.toString(),
                        style = lineNumberTextStyle
                    )
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = ::updateNewValue,
            value = currentText.value,
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
        )
    }
}