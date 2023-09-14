![kodeview_banner_opaque](https://github.com/SnipMeDev/KodeView/assets/8405055/59c6a2af-1b32-4a02-998f-ecae2296363a)

# KodeView

Kotlin Compose Multiplatform components for syntax highlighting based on
[Highlights](https://github.com/SnipMeDev/Highlights/blob/main/CHANGELOG.md) library.

## Preview

<img width="1680" alt="iShot_2023-09-14_08 19 21" src="https://github.com/SnipMeDev/KodeView/assets/8405055/adb205e4-ea16-41f7-aacb-de1085d6d5f5">

## Installation
TBD

## Components

### CodeTextView
The basic component that takes instance of Highlights and applies coloring on text.

#### Android

```kotlin
@Composable
fun MyApp() {
    val highlights = remember {
        mutableStateOf(
            Highlights
                .default()
                .getBuilder()
                .code("public static void main(String[] args) {}")
                .build()
        )
    }

   MaterialTheme {
      Column {
        CodeTextView(highlights = highlights.value)
      }
   }
}
```

#### iOS

```swift
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    @State var highlights = Highlights.companion.default()
    
    init() {
        highlights.setCode(code: "public static void main(String[] args) {}")
    }
    
    var body: some View {
        VStack {
            CodeTextView(newHighlights: $highlights)
                .ignoresSafeArea(.keyboard)
                .padding()
        }
    }
}
```

#### Desktop

```kotlin
private val windowSize = 500.dp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            width = windowSize,
            height = windowSize,
        )
    ) {
        val highlights = remember {
            mutableStateOf(
                Highlights
                    .default()
                    .getBuilder()
                    .code("public static void main(String[] args) {}")
                    .build()
            )
        }

        MaterialTheme {
            Column {
                CodeTextView(highlights = highlights.value)
            }
        }
    }
}
```

#### Web

```kotlin
@ExperimentalComposeUiApi
fun main() {
    onWasmReady {
        CanvasBasedWindow(
            canvasElementId = "ComposeTarget",
            applyDefaultStyles = true,
        ) {
            val highlights = remember {
                mutableStateOf(
                    Highlights
                        .default()
                        .getBuilder()
                        .code("public static void main(String[] args) {}")
                        .build()
                )
            }
    
            MaterialTheme {
                Column {
                    CodeTextView(highlights = highlights.value)
                }
            }
        }
    }
}
```
