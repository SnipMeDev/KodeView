//
//  ContentView.swift
//  iosApp
//
//  Created by Tomasz Kądziołka on 01/05/2023.
//

import SwiftUI
import kodeview

struct ContentView: View {
    @State var highlights = Highlights.companion.default()
    private let themes = Highlights.companion.themes(darkMode: false)
    private let languages = SyntaxLanguage.companion.getNames()

    init() {
        highlights.setCode(code:
                """
                class Main {
                    public static void main(String[] args) {
                        int abcd = 100;
                    }
                }
                """
        )
    }
    
    var body: some View {
        VStack {
            Text("KodeView")
            
            Divider()
            
            CodeTextView(newHighlights: $highlights)
                .ignoresSafeArea(.keyboard)
                .padding()
            
            Divider()
            
            CodeEditText()
            
            DropdownMenu(
                values: getThemeNames(themes: themes),
                defaultSelection: getThemeNames(themes: themes)
                    .firstIndex(of: highlights.getTheme().description()) ?? 0
            ) { selection in
                $highlights.wrappedValue = highlights
                    .getBuilder()
                    .theme(theme: themes[selection]!)
                    .build()
            }

            DropdownMenu(
                values: languages,
                defaultSelection:
                    SyntaxLanguage.companion.getNames()
                    .firstIndex(of: highlights.getLanguage().description()) ?? 0
            ) { selection in
                let newLanguage = SyntaxLanguage.companion.getByName(name: selection)
                $highlights.wrappedValue = highlights
                    .getBuilder()
                    .language(language: newLanguage!)
                    .build()
            }
        }
    }

//    func getThemeNames(themes: Dictionary<String, SyntaxTheme>) -> Array<String> {
//        return themes.keys.map { $0.description }.sorted()
//    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

