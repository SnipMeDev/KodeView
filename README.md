![kodeview_banner_opaque](https://github.com/SnipMeDev/KodeView/assets/8405055/59c6a2af-1b32-4a02-998f-ecae2296363a)

[![Maven Central](https://img.shields.io/maven-central/v/dev.snipme/kodeview)](https://mvnrepository.com/artifact/dev.snipme)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.10-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# KodeView

Kotlin Compose Multiplatform components for syntax highlighting based on
[Highlights](https://github.com/SnipMeDev/Highlights) library.

## Preview

<img width="1680" alt="iShot_2023-09-14_08 19 21" src="https://github.com/SnipMeDev/KodeView/assets/8405055/adb205e4-ea16-41f7-aacb-de1085d6d5f5">

## Installation ⬇️
```shell
repositories {
    mavenCentral()
}
```

```shell
implementation("dev.snipme:kodeview:0.6.0")
```

## Features ✨
- CodeTextView
- CodeEditText
- Code component analysis (Keyword, comment, etc.)
- Multiple syntax languages (Java, Swift, Kotlin, C, ...)
- Themes
- Text bolding (emphasis)
- Written in pure Kotlin, so available for many platforms 📱 💻 🖥️

## Support ☕
Kotlin Multiplatform is a fresh environment and developing for it is neither fast nor easy 🥲

If you feel that any of our project has saved you a time or effort, then consider supporting us via:  
[🧋 Buy Me A Coffee](https://bmc.link/SnipMeDev)

## Components 🧩

### CodeTextView
Basic component that takes instance of Highlights and applies coloring on a text.

```kotlin
@Composable
fun MyApp() {
    val highlights = remember {
        mutableStateOf(
            Highlights
                .Builder(code = "public static void main(String[] args) {}")
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

### CodeEditText
With this component, you can update your code via `onValueChange` callback.  
The Highlights library is ready for incremental updates, so change values anytime.  
The view bases on `TextField()`, and all it's fields are available for customization.  

```kotlin
@Composable
fun MyApp() {
    val highlights = remember {
        mutableStateOf(
            Highlights
                .Builder(code = "public static void main(String[] args) {}")
                .build()
        )
    }

   MaterialTheme {
      Column {
          CodeEditText(
              highlights = highlights.value,
              onValueChange = { textValue ->
                  highlights.value = highlights.value.getBuilder()
                      .code(textValue)
                      .build()
              },
              // Customize view's style
              colors = TextFieldDefaults.textFieldColors(),
          )
      }
   }
}
```

## Run examples 🏎️

Not all examples can be executed from command line, so recommended way is to use pre-created configurations:

<img width="270" alt="iShot_2023-09-18_08 19 44" src="https://github.com/SnipMeDev/KodeView/assets/8405055/be660f49-5a77-445e-a717-6aaec9b5c28a">

## TODO 🚧
- [X] CodeEditText

## Contribution 💻
Any form of support is very welcomed. 
Bugs, problems and new feature requests should be placed in the `Issues` tab with proper labeling.
New feature can be also submitted via `Pull Requests`. 
Then make sure:
- CHANGELOG and README have been updated
- New code matches library's vision and code style

License 🖋️
=======

    Copyright 2023 Tomasz Kądziołka.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
