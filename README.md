![kodeview_banner](https://github.com/SnipMeDev/KodeView/assets/8405055/6311ac3c-5e56-45bd-bc27-1790093f09fd)

# KodeView

Kotlin Compose Multiplatform components for syntax highlighting based on
[Highlights](https://github.com/SnipMeDev/Highlights/blob/main/CHANGELOG.md) library.

## Preview

<table>
  <tr>
    <th>Android</th>
    <th>iOS</th>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/SnipMeDev/KodeView/assets/8405055/a051e91f-b726-4050-b3bd-0c0fb9afb4b5" width="500">
    </td>
    <td>
      <img src="https://github.com/SnipMeDev/KodeView/assets/8405055/853afa56-c02a-47de-b0e4-85e6d45fe2e8" width="400">
    </td>
  </tr>
</table>

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
