package dev.snipme.desktopexample

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxTheme
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.highlights.model.SyntaxThemes.useDark
import dev.snipme.kodeview.view.material3.CodeEditText
import dev.snipme.kodeview.view.CodeTextView

private val sampleCode =
    """
    class Main {
        public static void main(String[] args) {
            int abcd = 100;
        }
    }
    """.trimIndent()

fun main() = application {
    val isDarkModeState = remember { mutableStateOf(false) }
    val isDarkMode = isDarkModeState.value

    val highlightsState = remember {
        mutableStateOf(
            Highlights.Builder(code = sampleCode).build()
        )
    }
    val highlights = highlightsState.value

    fun updateSyntaxTheme(theme: SyntaxTheme) {
        highlightsState.value = highlights.getBuilder()
            .theme(theme)
            .build()
    }

    fun updateSyntaxLanguage(language: SyntaxLanguage) {
        highlightsState.value = highlights.getBuilder()
            .language(language)
            .build()
    }

    MaterialTheme(colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KodeView example",
            state = rememberWindowState()
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(Modifier.height(8.dp))

                    ThemeSwitcher(
                        isDarkMode,
                        modifier = Modifier.fillMaxWidth(),
                    ) { setToDarkMode ->
                        isDarkModeState.value = setToDarkMode
                        updateSyntaxTheme(highlights.getTheme().useDark(setToDarkMode)!!)
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "KodeView",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) {
                        CodeTextView(
                            modifier = Modifier.weight(1f),
                            highlights = highlights,
                        )
                        VerticalDivider(Modifier.padding(8.dp))
                        CodeEditText(
                            modifier = Modifier.weight(1f),
                            label = { Text("Edit code") },
                            highlights = highlights,
                            onValueChange = { textValue ->
                                highlightsState.value = highlights.getBuilder()
                                    .code(textValue)
                                    .build()
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Dropdown(
                        options = SyntaxThemes.getNames(),
                        selected = SyntaxThemes.themes().keys.indexOf(highlights.getTheme().key),
                    ) { selectedThemeName ->
                        updateSyntaxTheme(
                            SyntaxThemes.themes(isDarkMode)[selectedThemeName.lowercase()]!!
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Dropdown(
                        options = SyntaxLanguage.getNames(),
                        selected = SyntaxLanguage.getNames().indexOfFirst {
                            it.equals(highlights.getLanguage().name, ignoreCase = true)
                        }) { selectedLanguage ->
                        updateSyntaxLanguage(SyntaxLanguage.getByName(selectedLanguage)!!)
                    }
                }
            }
        }
    }
}