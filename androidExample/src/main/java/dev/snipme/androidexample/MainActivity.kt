package dev.snipme.androidexample

import Dropdown
import LineNumberSwitcher
import ThemeSwitcher
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxTheme
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.highlights.model.SyntaxThemes.useDark
import dev.snipme.kodeview.view.CodeTextView
import dev.snipme.kodeview.view.material3.CodeEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var isDarkMode by remember { mutableStateOf(false) }
    var lineNumbersEnabled by remember { mutableStateOf(true) }
    var currentLanguage by remember { mutableStateOf(SyntaxLanguage.DEFAULT) }

    var highlights by remember {
        mutableStateOf(Highlights.Builder(code = Samples.kotlin).build())
    }

    fun updateSyntaxTheme(theme: SyntaxTheme) {
        highlights = highlights.getBuilder().theme(theme).build()
    }

    fun updateSyntaxLanguage(language: SyntaxLanguage) {
        highlights = highlights.getBuilder()
            .language(language)
            .code(Samples.getSampleCode(language))
            .build()
        currentLanguage = language
    }

    MaterialTheme(colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                ThemeSwitcher(
                    isDarkMode,
                    modifier = Modifier.fillMaxWidth()
                ) { setToDark ->
                    isDarkMode = setToDark
                    updateSyntaxTheme(highlights.getTheme().useDark(setToDark)!!)
                }

                Spacer(Modifier.height(16.dp))

                LineNumberSwitcher(
                    lineNumbersEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) { enabled ->
                    lineNumbersEnabled = enabled
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "KodeView",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                key(currentLanguage) {
                    CodeTextView(
                        modifier = Modifier.weight(1f)
                            .verticalScroll(rememberScrollState()),
                        highlights = highlights.getBuilder()
                            .code(Samples.getSampleCode(highlights.getLanguage())).build(),
                        showLineNumbers = lineNumbersEnabled,
                        hasHorizontalScroll = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                    )
                }

                Spacer(Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(Modifier.height(16.dp))

                key(highlights.getLanguage()) {
                    CodeEditText(
                        modifier = Modifier.weight(1f)
                            .verticalScroll(rememberScrollState()),
                        highlights = highlights,
                        showLineNumbers = lineNumbersEnabled,
                        hasHorizontalScroll = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
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

                Spacer(Modifier.height(16.dp))

                Dropdown(
                    options = SyntaxThemes.getNames(),
                    selected = SyntaxThemes.themes().keys.indexOf(highlights.getTheme().key)
                ) { selectedThemeName ->
                    updateSyntaxTheme(
                        SyntaxThemes.themes(isDarkMode)[selectedThemeName.lowercase()]!!
                    )
                }

                Spacer(Modifier.height(16.dp))

                Dropdown(
                    options = SyntaxLanguage.getNames(),
                    selected = SyntaxLanguage.getNames().indexOfFirst {
                        it.equals(highlights.getLanguage().name, ignoreCase = true)
                    }
                ) { selectedLanguage ->
                    updateSyntaxLanguage(SyntaxLanguage.getByName(selectedLanguage)!!)
                }
            }
        }
    }
}
