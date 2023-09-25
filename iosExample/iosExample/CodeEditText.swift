//
//  CodeEditText.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 25/09/2023.
//

import SwiftUI

struct CodeEditText: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        return KodeViewKt.codeEditText(highlights: Highlights.Builder(code: String, language: HighlightsSyntaxLanguage.default(), theme: HighlightsSyntaxTheme, emphasisLocations: [HighlightsPhraseLocation]))
    }
}

struct CodeEditText_Previews: PreviewProvider {
    static var previews: some View {
        CodeEditText()
    }
}
