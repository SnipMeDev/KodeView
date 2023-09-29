//
//  CodeEditText.swift
//  iosExample
//
//  Created by Tomasz Kądziołka on 25/09/2023.
//

import SwiftUI
import kodeview

// TODO Handle callback and stylize edit text field
struct CodeEditText: UIViewControllerRepresentable {
    @Binding private var highlights: Highlights

    init(newHighlights: Binding<Highlights>) {
        self._highlights = newHighlights
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return UIViewController()
    }

    func updateUIViewController(_ wrapper: UIViewController, context: Context) {
        let kotlinController = KodeViewKt.codeEditText(highlights: highlights) { String in
            // TODO FINISH
        }
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
