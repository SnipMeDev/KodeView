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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxTheme
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.highlights.model.SyntaxThemes.useDark
import dev.snipme.kodeview.view.CodeTextView
import dev.snipme.kodeview.view.material3.CodeEditText
import org.jetbrains.skiko.wasm.onWasmReady

@ExperimentalComposeUiApi
fun main() {
    onWasmReady {
        CanvasBasedWindow(
            title = "KodeView example",
            canvasElementId = "ComposeTarget",
            applyDefaultStyles = true,
        ) {
            var isDarkMode by remember { mutableStateOf(false) }
            var lineNumbersEnabled by remember { mutableStateOf(false) }
            var currentLanguage by remember { mutableStateOf(SyntaxLanguage.DEFAULT) }

            var highlights by remember {
                mutableStateOf(
                    Highlights.Builder(code = Samples.kotlin).build()
                )
            }

            fun updateSyntaxTheme(theme: SyntaxTheme) {
                highlights = highlights.getBuilder().theme(theme).build()
            }

            MaterialTheme(colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()) {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Spacer(Modifier.height(8.dp))

                        ThemeSwitcher(
                            isDarkMode,
                            modifier = Modifier.fillMaxWidth()
                        ) { dark ->
                            isDarkMode = dark
                            updateSyntaxTheme(highlights.getTheme().useDark(dark)!!)
                        }

                        Spacer(Modifier.height(16.dp))

                        LineNumberSwitcher(
                            lineNumbersEnabled,
                            modifier = Modifier.fillMaxWidth()
                        ) { enabled ->
                            lineNumbersEnabled = enabled
                        }

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "KodeView",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )

                        Spacer(Modifier.size(16.dp))

                        Row(
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        ) {
                            key(currentLanguage) {
                                CodeTextView(
                                    modifier = Modifier.weight(1f)
                                        .verticalScroll(rememberScrollState()),
                                    highlights = highlights.getBuilder()
                                        .code(Samples.getSampleCode(highlights.getLanguage()))
                                        .build(),
                                    showLineNumbers = lineNumbersEnabled,
                                    hasHorizontalScroll = true,
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                )
                            }

                            VerticalDivider(Modifier.padding(8.dp))
                            key(highlights.getLanguage()) {
                                CodeEditText(
                                    modifier = Modifier.weight(1f)
                                        .verticalScroll(rememberScrollState()),
                                    highlights = highlights,
                                    showLineNumbers = lineNumbersEnabled,
                                    hasHorizontalScroll = true,
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    label = { Text("Edit code") },
                                    onValueChange = { newText ->
                                        highlights = highlights.getBuilder().code(newText).build()
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
                        }

                        Spacer(Modifier.size(16.dp))

                        Dropdown(
                            options = SyntaxThemes.getNames(),
                            selected = SyntaxThemes.themes().keys.indexOf(highlights.getTheme().key)
                        ) { selectedThemeName ->
                            updateSyntaxTheme(
                                SyntaxThemes.themes(isDarkMode)[selectedThemeName.lowercase()]!!
                            )
                        }

                        Spacer(Modifier.size(16.dp))

                        Dropdown(
                            options = SyntaxLanguage.getNames(),
                            selected = SyntaxLanguage.getNames().indexOfFirst {
                                it.equals(highlights.getLanguage().name, ignoreCase = true)
                            }
                        ) { selectedLanguage ->
                            val language = SyntaxLanguage.getByName(selectedLanguage)!!
                            if (language == highlights.getLanguage()) return@Dropdown
                            currentLanguage = language
                            highlights = highlights.getBuilder()
                                .language(language)
                                .code(Samples.getSampleCode(language))
                                .build()
                        }
                    }
                }
            }
        }
    }
}