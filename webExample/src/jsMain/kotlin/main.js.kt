package dev.snipme.webexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.kodeview.view.CodeTextView
import org.jetbrains.skiko.wasm.onWasmReady

private val sampleCode =
    """
    class Main {
        public static void main(String[] args) {
            int abcd = 100;
        }
    }
    """.trimIndent()

@ExperimentalComposeUiApi
fun main() {
    onWasmReady {
        CanvasBasedWindow(
            title = "KodeView example",
            canvasElementId = "ComposeTarget",
            applyDefaultStyles = true,
        ) {
            val highlights = remember {
                mutableStateOf(
                    Highlights
                        .default()
                        .getBuilder()
                        .code(sampleCode)
                        .build()
                )
            }

            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "KodeView",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    CodeTextView(highlights = highlights.value)

                    Spacer(modifier = Modifier.weight(1f))

                    Dropdown(
                        options = SyntaxThemes.light.keys.toList(),
                        selected = SyntaxThemes.light.entries.indexOfFirst {
                            it.value == highlights.value.getTheme()
                        }) { selectedTheme ->
                        highlights.value = highlights.value.getBuilder()
                            .theme(SyntaxThemes.light[selectedTheme]!!)
                            .build()
                    }

                    Dropdown(
                        options = SyntaxLanguage.getNames(),
                        selected = SyntaxLanguage.getNames().indexOfFirst {
                            it.equals(
                                highlights.value.getLanguage().name,
                                ignoreCase = true
                            )
                        }) { selectedLanguage ->
                        highlights.value = highlights.value.getBuilder()
                            .language(SyntaxLanguage.getByName(selectedLanguage)!!)
                            .build()
                    }
                }
            }
        }
    }
}