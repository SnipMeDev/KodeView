//
//  CodeTextView.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 21/05/2023.
//

import SwiftUI
import kodeview

// Omit KMM library prefixing
typealias Highlights = HighlightsHighlights

struct CodeTextView: UIViewControllerRepresentable {
    @Binding private var highlights: Highlights

    init(newHighlights: Binding<Highlights>) {
        self._highlights = newHighlights
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return UIViewController()
    }

    func updateUIViewController(_ wrapper: UIViewController, context: Context) {
        let kotlinController = HighlightsViewKt.CodeTextViewUiViewController(highlights: highlights)
        // Cleanup
        kotlinController.removeFromParent()
        kotlinController.view.removeFromSuperview()
        // Update view
        wrapper.addChild(kotlinController)
        wrapper.view.addSubview(kotlinController.view)
        // Match with frame
        kotlinController.view.frame = wrapper.view.frame
        kotlinController.didMove(toParent: wrapper)
    }
}
