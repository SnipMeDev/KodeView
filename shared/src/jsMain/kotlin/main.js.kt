import androidx.compose.runtime.Composable
import dev.snipme.highlights.Highlights
import dev.snipme.kodeview.view.CodeTextView

@Composable
fun MainView() = CodeTextView(highlights = Highlights.Builder().code("class").build())