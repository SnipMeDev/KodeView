import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import dev.snipme.kodeview.view.CodeEditText
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

            MaterialTheme(colors = if (isDarkMode) darkColors() else lightColors()) {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween
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

                        CodeTextView(highlights = highlights)

                        Spacer(modifier = Modifier.size(16.dp))

                        Divider()

                        Spacer(modifier = Modifier.size(16.dp))

                        Text("Edit this...")
                        CodeEditText(
                            highlights = highlights,
                            onValueChange = { textValue ->
                                highlightsState.value = highlights.getBuilder()
                                    .code(textValue)
                                    .build()
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        Spacer(modifier = Modifier.weight(1f))

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
}